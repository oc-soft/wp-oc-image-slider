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
