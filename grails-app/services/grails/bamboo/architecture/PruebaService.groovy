package grails.bamboo.architecture

import ar.com.bamboo.framework.services.BaseService
import grails.transaction.Transactional

class PruebaService extends BaseService{

    @Transactional
    boolean save(domainToSave, boolean isSave) {
       return super.save(domainToSave) || isSave
    }

}
