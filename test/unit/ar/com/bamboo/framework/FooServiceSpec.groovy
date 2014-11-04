package ar.com.bamboo.framework

import grails.gorm.DetachedCriteria
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(FooService)
@Mock([Person])
class FooServiceSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "save method"() {
        given:
        mockForConstraintsTests Person
        Person personWithErrors = new Person()
        Person personWithoutErrors = new Person(name: "Mariano")

        when: "Cuando se intenta guardar un objeto que tiene que tirar error"
        boolean success = service.save(personWithErrors)

        then: "El metodo devuelve los errores"
        !success

        when: "Cuando se guarda un objeto sin errores"
        success = service.save(personWithoutErrors)


        then: "El metodo no devuelve los errores vacios y el id del objeto cargado"
        success
        personWithoutErrors.id
    }

    void "disable method"() {
        given:
        mockForConstraintsTests Person
        Person personToDisable = new Person(name: "Mariano")
        personToDisable.save(flush: true, failOnError: true)

        when: "Cuando se llama a un objeto para deshabilitado"
        service.disabled(personToDisable)
        then: "El objeto queda deshabilitado"
        !personToDisable.enabled
    }

    void "test listWithLimit method"() {
        given:
        mockForConstraintsTests Person
        //Guardo varias personas para poder hacer el test luego
        for ( i in 1..20 ){
            Person person = new Person(name: "Bamboo ${i}")
                    .save(flush: true, failOnError: true)
            if ( (i % 2) == 0){
                person.enabled = false
                person.save(flush: true, failOnError: true)
            }
        }
        def where = {
            enabled == true
        } as DetachedCriteria<Person>
        Map params = [max: 5]
        when: "Cuando busco objetos de alguna clase con limite 5 y sólo habilitado"
        def (List<Person> listResult, Integer count) = service.listWithLimit(Person.class, where, params)
        then: "El el resultado es el esperado"
        listResult
        listResult.size() == 5
        count == 10

        when: "Cuando se busca sin parametros"
        (listResult, count) = service.listWithLimit(Person.class, where, null)
        then: "El resultado es el total sin limite"
        listResult
        listResult.size() == 10
        count == 10
    }

    void "test listAll method"() {
        given:
        mockForConstraintsTests Person
        //Guardo varias personas para poder hacer el test luego
        for ( i in 1..1300 ){
            Person person = new Person(name: "Bamboo ${i}")
                    .save(flush: true, failOnError: true)
            if ( (i % 2) == 0){
                person.enabled = false
                person.save(flush: true, failOnError: true)
            }
        }
        def where1 = {
            enabled == true
        } as DetachedCriteria<Person>
        when: "Cuando busco objetos de de alguna clase con enable y supera la cantidad maxima de chunk"
        List<Person> listResult = service.listAll(Person.class, where1)
        then: "El el resultado es el esperado"
        listResult
        listResult.size() == 650

        when: "Cuando se busca con una condicion que no trae ningun resultado"
        def where2 = {
            name == "Pepe"
        } as DetachedCriteria<Person>
        listResult = service.listAll(Person.class, where2)
        then: "El resultado es el total sin limite"
        !listResult
    }
}
