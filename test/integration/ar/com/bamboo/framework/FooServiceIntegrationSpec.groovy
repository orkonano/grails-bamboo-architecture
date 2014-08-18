package ar.com.bamboo.framework


import spock.lang.*

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
        Person personDB = fooService.getById(p)
        then: "El objeto persona es el que guardé antes"
        personDB
        personDB.id == p.id

        when: "Cuando busco una persona que no existe"
        p.id = 2
        personDB = fooService.getById(p)
        then: "El objeto persona es el que guardé antes"
        !personDB
    }
}
