/*
 * Copyright 2022 oc-soft
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
