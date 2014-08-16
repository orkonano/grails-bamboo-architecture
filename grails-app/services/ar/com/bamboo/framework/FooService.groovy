package ar.com.bamboo.framework

import ar.com.bamboo.framework.services.BaseService
import grails.transaction.Transactional

class FooService extends BaseService{

    @Transactional
    boolean save(domainToSave, boolean isSave) {
        return super.save(domainToSave) || isSave
    }
}
