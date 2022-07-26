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

package net.oc_soft.wordpress


import net.oc_soft.slide.settings.Size as SlideSize
import net.oc_soft.slide.settings.SizeOption
import net.oc_soft.slide.settings.Width
import net.oc_soft.slide.settings.Height

/**
 * size helper for wordpress setting
 */
class Size {


    /**
     * class instance
     */
    companion object {

        /**
         * pager size
         */
        fun createPagingSizeFromSettings(
            attr: dynamic): SlideSize? {
            val optionKeys =  arrayOf("page-width-option", "page-height-option")
            val optionSettings = Array<Number?>(optionKeys.size) {
                val value = attr[optionKeys[it]] as Any?
                when (value) {
                    is Number -> value
                    is String -> value.toIntOrNull() as Number?
                    else -> null
                }
            }

            val result = if (optionSettings[0] != null 
                && optionSettings[1] != null) {
                var sizeKeys = if (optionSettings[0] == 0) {
                    arrayOf("page-width-relative")
                } else {
                    arrayOf(
                        "page-width-desktop",
                        "page-width-tablet",
                        "page-width-mobile")
                    
                }
                var sizeSettings = Array<Number?>(sizeKeys.size) {
                    val value = attr[sizeKeys[it]] as Any?
                    when (value) {
                        is Number -> value
                        is String -> value.toIntOrNull() as Number?
                        else -> null
                    }
                }
                val width = if (sizeSettings[0] != null) {
                    var lastValue: Double? = null
                    Width(SizeOption.values()[optionSettings[0]!!.toInt()],
                        DoubleArray(sizeSettings.size) {
                            val current = sizeSettings[it] 
                            current?.let {
                                lastValue = it.toDouble()
                            }
                            lastValue!!
                        })
                } else null

                sizeKeys = if (optionSettings[1] == 0) {
                    arrayOf("page-height-aspect-1",
                        "page-height-aspect-2")
                } else {
                    arrayOf(
                        "page-height-desktop",
                        "page-height-tablet",
                        "page-height-mobile")
                    
                }
                sizeSettings = Array<Number?>(sizeKeys.size) {
                    val value = attr[sizeKeys[it]] as Any?
                    when (value) {
                        is Number -> value
                        is String -> value.toIntOrNull()
                        else -> null
                    }
                }
                val height = if (sizeSettings[0] != null) {
                    var lastValue: Double? = null
                    Height(SizeOption.values()[optionSettings[1]!!.toInt()],
                        DoubleArray(sizeSettings.size) {
                            val current = sizeSettings[it] 
                            current?.let {
                                lastValue = it.toDouble()
                            }
                            lastValue!!
                        })
                } else null

                if (width != null && height != null) {
                    SlideSize(width, height)
                } else null
            } else null

            return result
        }
    }
}
// vi: se ts=4 sw=4 et:

