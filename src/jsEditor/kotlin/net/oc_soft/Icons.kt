package net.oc_soft

import react.Fragment
import react.create
import react.dom.svg.ReactSVG.path
import react.dom.svg.ReactSVG.svg
import react.dom.svg.FillRule

/**
 * manage icon object
 */
class Icons {

    /**
     * icons instance
     */
    companion object {
        /**
         * block editor icon
         */
        val blockEditor : react.ReactElement<*>
            get() {
                return Fragment.create {
                    svg { 
                        viewBox = "0 0 24 24" 
                        path {
                            fillRule = FillRule.evenodd
                            d = net.oc_soft.icon.block.editor.d
                        }
                    }
                }
            } 

    }
}

// vi: se ts=4 sw=4 et:
