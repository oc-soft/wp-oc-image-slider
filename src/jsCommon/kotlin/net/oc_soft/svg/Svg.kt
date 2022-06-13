package net.oc_soft.svg

import kotlinx.browser.document

import org.w3c.dom.svg.SVGElement
import org.w3c.dom.svg.SVGPathElement

/**
 * svg 
 */
class Svg {

    /**
     * class instance
     */
    companion object {

        /**
         * create svg element
         */
        fun createElement(tag: String): SVGElement {
            return document.createElementNS(
                "http://www.w3.org/2000/svg", tag) as SVGElement
        }
    }
}

// vi: se ts=4 sw=4 et:
