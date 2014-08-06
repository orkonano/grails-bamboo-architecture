package ar.com.bamboo.framework

import ar.com.bamboo.framework.domains.BaseEntity

class Person extends BaseEntity{

    String name

    static constraints = {
        name blank: false
    }
}
