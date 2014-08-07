package ar.com.bamboo.framework.domains

import groovy.transform.CompileStatic

/**
 * Created by orko on 05/08/14.
 */
@CompileStatic
abstract class BaseEntity {

    boolean enabled


    void beforeInsert() {
        this.enabled = true
        this.executeMoreBeforeInsert()
    }

    abstract  protected void  executeMoreBeforeInsert();
}
