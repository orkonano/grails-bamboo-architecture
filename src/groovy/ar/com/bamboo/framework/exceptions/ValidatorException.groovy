package ar.com.bamboo.framework.exceptions

import groovy.transform.CompileStatic

/**
 * Created by orko on 15/08/14.
 */
@CompileStatic
class ValidatorException extends RuntimeException{

    private Map errors

}
