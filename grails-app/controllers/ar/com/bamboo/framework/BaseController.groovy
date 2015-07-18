package ar.com.bamboo.framework

import ar.com.bamboo.framework.controller.ErrorRendererController
import ar.com.bamboo.framework.exceptions.ForbiddenException
import ar.com.bamboo.framework.exceptions.NotFoundException
import org.springframework.http.HttpStatus

import static org.springframework.http.HttpStatus.FORBIDDEN
import static org.springframework.http.HttpStatus.NOT_FOUND

abstract class BaseController implements ErrorRendererController {

    protected Integer setMaxParameter(){
        params.max = Math.min(params.max ? Integer.valueOf(params.max) : 10, 100)
    }

    protected Integer setOffsetParameter(){
        params.offset = params.offset ? Integer.valueOf(params.offset) : 0
    }

    @Deprecated
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

    public void notFound() {
        this.generateResponse(NOT_FOUND)
    }

    public void forbidden() {
        this.generateResponse(FORBIDDEN)
    }

    private void generateResponse(HttpStatus httpStatus){
        request.withFormat {
            json {
                response.status = httpStatus.value()
            }
            '*'{
                response.sendError(httpStatus.value())
            }
        }
    }


}
