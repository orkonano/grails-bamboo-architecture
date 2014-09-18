package ar.com.bamboo.framework

import spock.lang.Specification

/**
 *
 */
class FooServiceIntegrationSpec extends Specification {

    def fooService

    def setup() {
        
    }

    def cleanup() {
    }

    void "test get method"() {
        given:
        Person p = new Person()
        p.id = 1

        when: "Cuando busco la persona por el id que acabo de registrar"
        Person personDB = fooService.getById(p.id)
        then: "El objeto persona es el que guardé antes"
        personDB
        personDB.id == p.id

        when: "Cuando busco una persona que no existe"
        p.id = 2
        personDB = fooService.getById(p.id)
        then: "El objeto persona es el que guardé antes"
        !personDB
    }

    void "test listAll with projections method"() {

        when: "Cuando busco la persona por el id que acabo de registrar"
        List<Long> idsPerson = fooService.listProjections()
        then: "El objeto persona es el que guardé antes"
        idsPerson
        idsPerson[0] instanceof Long

    }

    void "test listAll Con HQL method"() {
        given:
        //Guardo varias personas para poder hacer el test luego
        for ( i in 1..1300 ){
            Person person = new Person(name: "Bamboo ${i}").save(flush: true, failOnError: true)
            if ( (i % 2) == 0){
                person.enabled = false
                person.save(flush: true, failOnError: true)
            }
        }

        String hql = " from Person "

        when: "Busco las personas sin ningún parámetro extra y sin ninguna restriccion"
        List<Person> listResult = fooService.listAllHql( hql)
        then: "Devuelve 1300 resultados"
        listResult
        listResult.size() == 1301

        when: "Cuando se busca sólo a los habilitados, pero sin pasar parámetros"
        hql = " from Person where enabled = true"
        listResult = fooService.listAllHql(hql)
        then: "Devuelve 650 resultados"
        listResult
        listResult.size() == 651

        when: "Cuando se busca sólo a los habilitados, pero pasando por parametros los valores"
        hql = " from Person where enabled = :enabled"
        listResult = fooService.listAllHql(hql, [enabled: true])
        then: "Devuelve 650 resultados"
        listResult
        listResult.size() == 651

        when: "Cuando se busca sólo a los habilitados, pero pasando por parametros los valores, con offset y limit"
        hql = " from Person where enabled = :enabled"
        listResult = fooService.listAllHql(hql, [enabled: true, offset: 0, max: 10])
        then: "Devuelve 650 resultados, se buscó desde el comienzo y se ignoró al limit"
        listResult
        listResult.size() == 651

        when: "Cuando se busca sólo a los habilitados, pero pasando por parametros los valores, con offset y limit"
        hql = " from Person where enabled = :enabled"
        listResult = fooService.listAllHql(hql, [enabled: true, offset: 100])
        then: "Devuelve 550 resultados, se buscó desde la posición 100 y se ignoró al limit"
        listResult
        listResult.size() == 551
    }
}
