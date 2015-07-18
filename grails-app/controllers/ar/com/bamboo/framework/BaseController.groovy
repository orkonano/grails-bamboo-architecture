package ar.com.bamboo.framework

import ar.com.bamboo.framework.exceptions.ForbiddenException
import ar.com.bamboo.framework.exceptions.NotFoundException
import grails.converters.JSON
import org.springframework.http.HttpStatus

import static org.springframework.http.HttpStatus.FORBIDDEN
import static org.springframework.http.HttpStatus.NOT_FOUND

abstract class BaseController{

    protected void makeViewOnErrorJson(baseEntity){
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

    protected void makeViewOnErrorJson(String message){
        def result = [success: false, model: [errorsMessage: [message]]]
        render result as JSON
        return
    }

    protected Integer setMaxParameter(){
        params.max = Math.min(params.max ? Integer.valueOf(params.max) : 10, 100)
    }

    protected Integer setOffsetParameter(){
        params.offset = params.offset ? Integer.valueOf(params.offset) : 0
    }

    protected Map getListParameters(){
        this.setOffsetParameter()
        this.setMaxParameter()
        Map<String, Object> listParameters = new HashMap<>()
        listParameters.max = params.max
        listParameters.offset = params.offset
        return listParameters
    }

    def handleForbiddenException(ForbiddenException e) {
        this.forbidden()
        return
    }

    def handleNotFoundException(NotFoundException e) {
        this.notFound()
        return
    }

    protected void notFound() {
        response.status = NOT_FOUND.value()
    }

    protected void forbidden() {
        response.status = FORBIDDEN.value()
    }
}
