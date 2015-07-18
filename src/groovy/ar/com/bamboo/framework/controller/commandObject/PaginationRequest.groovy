package ar.com.bamboo.framework.controller.commandObject

/**
 * Created by orko on 18/07/15.
 */
class PaginationRequest {
    private static final Integer MAX_COUNT_ELEMENT = 100
    private static final Integer DEFAULT_COUNT_ELEMENT = 10

    Integer max = DEFAULT_COUNT_ELEMENT
    Integer offset = 0

    void setMax(Integer max) {
        this.max = Math.min(max ?: this.max, MAX_COUNT_ELEMENT)
    }
}
