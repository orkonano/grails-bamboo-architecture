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
    protected List<Object> listAllHql(Class clazz, String hql, Map parameters){
        if (parameters == null){
            log.debug("Los parámetros para ejecutar la query vienen vacíos, inicializo el mapa")
            parameters = [:]
        }
        if (!parameters.offset){
            log.debug("El parámetro offset no está seteado, lo seteo por default en 0")
            parameters.offset = 0
        }
        if (log.debugEnabled){
            if (!parameters.max){
                log.debug("El parámetro limit es seteado por default en $MAX_BUNK")
            }else{
                log.debug("Se sobreescribe el parámetro limit por $MAX_BUNK")
            }
        }

        parameters.max = MAX_BUNK

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
}
