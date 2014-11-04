package ar.com.bamboo.framework.exceptions

import grails.validation.ValidationErrors
import groovy.transform.CompileStatic

/**
 * Created by orko on 15/08/14.
 */
@CompileStatic
class ValidatorException<T> extends RuntimeException{

    private ValidationErrors errors
    private T model

}
