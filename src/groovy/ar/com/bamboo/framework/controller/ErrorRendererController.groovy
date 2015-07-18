package ar.com.bamboo.framework.controller

import grails.converters.JSON

/**
 * Created by orko on 18/07/15.
 */
trait ErrorRendererController {

    public void makeViewOnErrorJson(baseEntity){
        List<String> errorsMessage = baseEntity.errors.allErrors.collect {
            message(error:it, encodeAs:'HTML')
        }

        List<String> fieldsError = baseEntity.errors.fieldErrors.collect {
            it.field
        }

        def result = [success: false, model: [errorsMessage: errorsMessage, fieldsError: fieldsError]]
        render result as JSON
        return
    }

    public void makeViewOnErrorJson(String message){
        def result = [success: false, model: [errorsMessage: [message]]]
        render result as JSON
        return
    }

}