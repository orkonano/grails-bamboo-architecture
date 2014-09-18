package ar.com.bamboo.framework

import grails.gorm.DetachedCriteria
import grails.transaction.Transactional

class FooService extends BaseService{

    @Transactional
    boolean save(domainToSave, boolean isSave) {
        return super.save(domainToSave) || isSave
    }

    @Transactional(readOnly = true)
    List<Long> listProjections() {
        def where = { name == 'Mariano' } as DetachedCriteria<Person>
        Map options = [projections: 'id']
        return this.listAll(Person.class, where, options)
    }

    @Transactional(readOnly = true)
    List<Person> listAllHql(String hql, Map params = [:]) {
        return this.listAllHql(Person.class, hql, params)
    }

    @Transactional(readOnly = true)
    Person getById(Number id){
        return super.getById(Person.class, id)
    }

}
