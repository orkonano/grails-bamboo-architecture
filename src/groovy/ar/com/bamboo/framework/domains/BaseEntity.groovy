package ar.com.bamboo.framework.domains

import groovy.transform.CompileStatic

/**
 * Created by orko on 05/08/14.
 */
@CompileStatic
class BaseEntity {

    boolean enable


    void beforeInsert() {
        this.enable = true
    }
}
