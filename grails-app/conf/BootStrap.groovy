import ar.com.bamboo.framework.Person

class BootStrap {


    def init = { servletContext ->
        Person personDB = Person.findByName("Mariano")
        if (!personDB){
            new Person(name: "Mariano").save(flush: true, failOnError: true)
        }
    }

    def destroy = {
    }


}
