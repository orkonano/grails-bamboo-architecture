package ar.com.bamboo.framework.exceptions

/**
 * Created by orko on 05/07/15.
 */
class NotFoundException extends RuntimeException{
    def id
    Class<?> aClass

    NotFoundException(id, Class aClass1){
        this.id = id
        this.aClass = aClass1
    }

    @Override
    String getMessage() {
        return "No se encontró el $id de la clase $aClass.canonicalName"
    }
}
