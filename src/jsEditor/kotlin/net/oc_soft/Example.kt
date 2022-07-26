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

import kotlinx.browser.window

import org.w3c.dom.get

/**
 * example for block editor
 */
class Example {


    /**
     * image example data
     */
    fun getImageExamples(): Array<Any> {
        val examples = (window["oc"] as Any?)?.let {
            val dynOc: dynamic 
            dynOc = it
            (dynOc["slider"] as Any?)?.let {
                val dynSlider: dynamic 
                dynSlider = it
                (dynSlider["example"] as Any?)?.let {
                    it as Array<*>
                }?: emptyArray<Any>()
            }?: emptyArray<Any>()
        }?: emptyArray<Any>()

        val result = Array<Any>(examples.size) {
            object {
                @JsName("name")
                val name = "core/image"
                @JsName("attributes")
                val attributes = examples[it]
            }
        }
        return result
    }


    /**
     * get example object
     */
    operator fun invoke(): Any {
        val result = object {
            @JsName("attributes")
            val attributes = kotlin.js.json(
                "paging-control-auto" to 1,
                "paging-control-loop" to 1,
                "paging-control-stop-duration" to 1000
            ) 

            @JsName("innerBlocks")
            val innerBlocks = getImageExamples()
        }
        return result
    }
}
// vi: se ts=4 sw=4 et:
