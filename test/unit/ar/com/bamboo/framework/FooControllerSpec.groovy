package ar.com.bamboo.framework

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(FooController)
class FooControllerSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test notFoundAction"() {

        when: "Cuando llamo a un método que arroja la exception NotFound y el request es httml"
        controller.notFoundAction()

        then: "Se arroja el send error con 404"
        response.status == 404

        when: "Cuando llamo a un mñetodo con formato JSON"
        response.reset()
        request.contentType = JSON_CONTENT_TYPE
        controller.notFoundAction()

        then: "Se arroja el send error con 404"
        response.status == 404
    }
}
