package ar.com.bamboo.framework

import ar.com.bamboo.framework.persistence.PaginatedResult
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
    PaginatedResult listAllHqlWithLimit(String hql, Map params = [:]) {
        return this.listAllHqlWithLimit(Person.class, hql, params)
    }

    @Transactional(readOnly = true)
    Person getById(Number id){
        return super.getById(Person.class, id)
    }

    @Transactional(readOnly = true)
    Person getByName(String nameArgument){
        def where = { name == nameArgument } as DetachedCriteria<Person>
        return super.getUnique(Person.class, where, null)
    }

}
