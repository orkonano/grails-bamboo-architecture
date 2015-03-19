package ar.com.bamboo.framework

import ar.com.bamboo.framework.domains.BaseEntity
import grails.converters.JSON
import org.springframework.web.multipart.commons.CommonsMultipartFile

import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED
import static org.springframework.http.HttpStatus.NOT_FOUND

abstract class BaseController {

    protected void notFound() {
        withFormat {
            json {
                response.status = NOT_FOUND.value()
            }

            '*'{
                response.status = NOT_FOUND.value()
            }
        }
    }

    protected void notAllow() {
        withFormat {
            json {
                def result = [success: false, status: METHOD_NOT_ALLOWED]
                render result as JSON

            }
            '*' {
                response.status = METHOD_NOT_ALLOWED.value()
            }
        }
    }

    protected void makeViewOnErrorJson(BaseEntity baseEntity){
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
}
