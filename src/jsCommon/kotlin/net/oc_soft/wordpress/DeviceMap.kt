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

import kotlinx.browser.window
import kotlin.collections.Map

/**
 * device map
 */
class DeviceMap {

    /**
     * class instance
     */
    companion object {

        /**
         * width map
         */
        val deviceWidth: Array<Pair<String, Int>> = arrayOf(
            Pair("Tablet", 780),
            Pair("Mobile", 360))

        
        /**
         * match device key
         */
        val matchDevice: String 
            get() {
                var result = "Desktop"
                for (idx in deviceWidth.indices) {
                    val elem = deviceWidth[idx]
                    if (window.matchMedia(
                        "(max-width: ${elem.second}px})").matches) {
                        result = elem.first
                        break
                    }
                }
                return result
            }
        /**
         * match device
         */
        fun matchDevice(width: Double): String {
            var result = "Desktop"
            for (idx in deviceWidth.indices) {
                val elem = deviceWidth[idx]
                if (width <= elem.second.toDouble()) {
                    result = elem.first
                }

            }
            return result
        }
    }
}

// vi: se ts=4 sw=4 et:
