package ar.com.bamboo.framework

import grails.transaction.Transactional

class BaseService {

    private static final Integer MAX_BUNK = 500;

    @Transactional
    boolean save(domainToSave) {
        if (domainToSave.validate()){
            domainToSave.save()
            return true
        }else{
            BaseService.log.debug("""
       El dominio de la clase ${domainToSave.getClass()}  no pasó la validación.
       Se encontraron los siguientes errores: ${domainToSave.errors}
                    """)
            return false
        }
    }

    @Transactional
    public void disabled(domainToDisable){
        domainToDisable.enabled = false
        domainToDisable.save()
    }

    public <T> T getById(Class clazz, Number id){
        return clazz.get(id)
    }

    protected List<Object> listWithLimit(Class clazz,  where,  Map paramsQuery){
        def query = clazz.where(where)
        Integer count = query.count()
        List<Object> objects = query.list(paramsQuery ?: [:])
        return [objects, count]
    }

    /**
     * Devuelve el primer elemento encontrado de la query
     * @param clazz
     * @param hql
     * @param parameters
     * @return
     */
    protected Object getUnique(Class clazz, String hql, Map parameters){
        if (parameters == null){
            parameters = []
        }
        parameters.max = 1
        List<Object> queryResult = clazz.executeQuery(hql, parameters)
        return queryResult ? queryResult[0] : null
    }

    /**
     * Pasado una lista de ids, realiza un load de la session de hibernate de todos los ids
     * @param clazz
     * @param hql
     * @param parameters
     * @return
     */
    protected <T extends Serializable> List<Object> loadById(Class clazz, List<T> ids){
        if (ids){
            List<Object> result = new ArrayList<>(ids.size())
            for (id in ids){
                result.add(clazz.get(id))
            }
            return result
        }else{
            return new ArrayList<Object>()
        }
    }

    /**
     * Método para utilizar cuando se quiere buscar todos los elementos que corresponden con el
     * where (Debe ser Criteria o DetachedCriteria) pasado por parámetro
     * Este método se debe usar cuando se quiere obtener los resultados mediante Criteria
     * Por ejemplo Persona.where{ query } o DetachedCriteria dc = new DetachedCriteria.
     *
     * Es método hace uso de queries más cortas para evitar traer de la base de datos mucha información
     * y así agilizar las queries. Trae de a 500 resultados.
     * @param clazz Clase a la cual se va a ejecutar la query
     * @param where Where query o DetachedCriteria con las restricciones del query
     * @param options Opciones para la búsqueda como se projecciones. Es un mapa, que si se quiere
     * buscar proyecciones, se debe pasar por parametro como map[projections: properties]
     * @return
     */
    protected List<Object> listAll(Class clazz, where, Map options = [:]){
        def query = clazz.where(where)
        if (options){
            if (options.projections){
                query = query.property(options.projections)
            }
        }
        List<Object> objects = new ArrayList<Objects>()
        boolean continueSearch = true
        Map paramsQuery = [:]
        paramsQuery.max = MAX_BUNK
        paramsQuery.offset = 0
        while(continueSearch){
            List<Object> queryResult = query.list(paramsQuery)
            objects.addAll(queryResult)
            continueSearch = queryResult.size() == MAX_BUNK
            if (continueSearch){
                paramsQuery.offset += MAX_BUNK
            }
        }
        return objects
    }

    /**
     * Método para utilizar cuando se quiere buscar todos los elementos que corresponden a la query
     * escrita en HQL
     *
     * Es método hace uso de queries más cortas para evitar traer de la base de datos mucha información
     * y así agilizar las queries. Trae de a 500 resultados.

     * @param clazz Clase a la cual se va a ejecutar la query
     * @param sql La query Hql
     * @param parameters Se debe cargar los parámetros de la query. También admite los parametros offset y max
     * @return
     */
    protected List<Object> listAllHql(Class clazz, String hql, Map parameters){
        if (parameters == null){
            log.debug("Los parámetros para ejecutar la query vienen vacíos, inicializo el mapa")
            parameters = [:]
        }
        if (!parameters.offset){
            log.debug("El parámetro offset no está seteado, lo seteo por default en 0")
            parameters.offset = 0
        }
        if (!parameters.max){
            log.debug("El parámetro limit es seteado por default en $MAX_BUNK")
            parameters.max = MAX_BUNK
        }else{
            if (parameters.max > MAX_BUNK){
                parameters.max = MAX_BUNK
                log.debug("Se sobreescribe el parámetro limit por $MAX_BUNK")
            }
        }


        List<Object> objects = new ArrayList<Objects>()
        boolean continueSearch = true
        while(continueSearch){
            List<Object> queryResult = clazz.findAll(hql, parameters)
            objects.addAll(queryResult)
            continueSearch = queryResult.size() == MAX_BUNK
            if (continueSearch){
                parameters.offset += MAX_BUNK
            }
        }
        return objects
    }

    /**
     * El método arma la query para devolver la cantidad total de resultados que machean con la query
     * y además los resultados con limit y offset.
     *
     * El mapa paramaters contiene:
     *  - Los parámetros de la query (la clave es el nombre del parámetro)
     *  - El limit (Clave del mapa es max)
     *  - El offset (Clave del mapa offset)
     *  - Los orderBy (Clave orderBy). El valor del order by es un string sin la palabra ORDER BY.
     *     Ej: name DESC, year ASC
     *  - projections: Son las proyecciones que se quiere que la query traiga. Si no se pone nada, realiza la ejecución
     *  de la query sólo con FROM, sin select
     *
     * El order by, al ponerlo en los parámetros mejora la performance de la query de count.
     * @param clazz
     * @param hql
     * @param parameters
     * @return
     */
    protected List<Object> listAllHqlWithLimit(Class clazz, String hql, Map parameters){
        if (parameters == null){
            log.debug("Los parámetros para ejecutar la query vienen vacíos, inicializo el mapa")
            parameters = [:]
        }
        Integer offset = 0
        if (parameters.offset){
            offset = parameters.offset
            parameters.offset = 0
        }

        String orderBy = ""
        if (parameters.orderBy){
            orderBy = " ORDER BY " + parameters.orderBy
            parameters.remove("orderBy")
        }
        String select = ""
        if (parameters.projections){
            select = "SELECT " + parameters.projections
            parameters.remove("projections")
        }

        List result = clazz.executeQuery("SELECT count(*) " + hql, parameters)
        Integer count = result ? result[0] : 0

        parameters.offset = offset
        if (!parameters.max || Integer.valueOf(parameters.max) > MAX_BUNK){
            log.debug("El parámetro limit es seteado por default en $MAX_BUNK")
            parameters.max = MAX_BUNK
        }

        if (orderBy){
            hql += orderBy
        }

        if (select){
            log.debug("Se agrega el select -las projecciones- a la query")
            hql = select + " " + hql
        }

        List<Object> objects = clazz.executeQuery(hql, parameters)

        return [objects, count]
    }
}
