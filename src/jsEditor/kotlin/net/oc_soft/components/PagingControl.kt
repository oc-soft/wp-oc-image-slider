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

package net.oc_soft.components

import kotlinx.js.Object
import react.create


class PagingControl {

    /**
     * create paging control react element
     */
    operator fun invoke(
        attributes: dynamic,
        setAttributes: (dynamic)->Unit): react.ReactNode {

        val handleChangeDuration: (dynamic)->Unit = {
            val duration = when (it) {
                is String -> (it as String).toIntOrNull()
                is Number -> it.toInt()
                else -> throw IllegalArgumentException()
            }
            duration?.let {
                val setting: dynamic = object {}
                setting["paging-control-stop-duration"] = duration
                setAttributes(setting)
            }
        }
        val duration = attributes["paging-control-stop-duration"]

        return react.Fragment.create {

            wordpress.components.PanelBody {
                title = wordpress.i18n.gettext(
                    "Paging", 
                    "oc-image-slider")
                
                wordpress.components.TextControl {
                    Object.assign(this, object {
                        @JsName("min")
                        val min = 0
                    })
                    label = wordpress.i18n.gettext(
                        "Stop Duration (msec)",
                        "oc-image-slider")
                    value = duration
                    onChange = handleChangeDuration
                    type = "number"
                }


                wordpress.components.ToggleControl {
                    label = wordpress.i18n.gettext(
                        "Auto paging", "oc-image-slider")
                    checked = (attributes["paging-control-auto"] as Int) != 0
                    onChange = {
                        val obj: dynamic = object {}
                        obj["paging-control-auto"] = if (it) 1 else 0
                        setAttributes(obj)
                    }
                }
                wordpress.components.ToggleControl {
                    label = wordpress.i18n.gettext(
                        "Endless", "oc-image-slider")
                    checked = (attributes["paging-control-loop"] as Int) != 0
                    onChange = {
                        val obj: dynamic = object {}
                        obj["paging-control-loop"] = if (it) 1 else 0
                        setAttributes(obj)
                    }
                }
                wordpress.components.Divider {
                }

                wordpress.components.ToggleControl {
                    label = wordpress.i18n.gettext(
                        "Auto backward", "oc-image-slider")
                    val autoDir = attributes[
                        "paging-control-auto-direction"] as Number 
                    checked = (autoDir.toInt()) != 0
                    onChange = {
                        val obj: dynamic = object {}
                        obj["paging-control-auto-direction"] = if (it) {
                            -1 
                        } else {
                            0
                        }
                        setAttributes(obj)
                    }
                }
            }
        }
    }
}

// vi: se ts=4 sw=4 et: 
