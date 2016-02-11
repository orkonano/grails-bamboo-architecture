package ar.com.bamboo.framework.demo

import ar.com.bamboo.framework.domains.BaseEntity


class Person implements BaseEntity {

    String name

    static constraints = {
        name blank: false
    }

}
