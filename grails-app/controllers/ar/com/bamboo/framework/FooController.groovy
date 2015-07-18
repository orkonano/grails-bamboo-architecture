package ar.com.bamboo.framework


import ar.com.bamboo.framework.exceptions.NotFoundException
import ar.com.bamboo.framework.persistence.PaginatedResult

class FooController extends BaseController{

    def notFoundAction() {
        throw new NotFoundException(1, PaginatedResult.class)
    }
}
