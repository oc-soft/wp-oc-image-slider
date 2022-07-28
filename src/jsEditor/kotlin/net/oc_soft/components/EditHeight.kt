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

import kotlin.text.toInt

import react.create

import net.oc_soft.px.toPx
import net.oc_soft.px.pxToInt


/**
 * height editor
 */
class EditHeight {


    /**
     * creat react element
     */
    operator fun invoke(
        attributes: dynamic,
        setAttributes: (dynamic)->Unit): react.ReactNode  {
        val aspectRatioUi = AspectRatio()
        val controlUnits = arrayOf(
            object {
                @JsName("value")
                val value = "px"

                @JsName("label")
                val label = "px"
            }
        )
        return react.Fragment.create {
            wordpress.components.PanelBody {

                title = wordpress.i18n.gettext(
                    "Height", 
                    "oc-image-slider")

                wordpress.components.ToggleControl {

                    label = wordpress.i18n.gettext(
                        "Use fixed values", "oc-image-slider")
                    checked = (attributes["page-height-option"] as Int) != 0 
                    onChange = {
                        val obj: dynamic = object {}
                        obj["page-height-option"] = if (it) 1 else 0
                        setAttributes(obj)
                    }
                }

                + aspectRatioUi(
                    attributes,
                    setAttributes,
                    "page-height-aspect-1",
                    "page-height-aspect-2")

                
                wordpress.components.Divider {
                }

                wordpress.components.UnitControl {
                    label = wordpress.i18n.gettext("Desktop")
                    isPressEnterToChange = true
                    units = controlUnits
                    onChange = {
                        value, _ ->
                        val obj: dynamic = object {}
                        obj["page-height-desktop"] = value.pxToInt()
                        setAttributes(obj)
                    }
                    value = (attributes["page-height-desktop"] as Int).toPx()
                }
                wordpress.components.UnitControl {
                    label = wordpress.i18n.gettext("Tablet")
                    isPressEnterToChange = true
                    units = controlUnits
                    onChange = {
                        value, _ ->
                        val obj: dynamic = object {}
                        obj["page-height-tablet"] = value.pxToInt()
                        setAttributes(obj)
                    } 
                    value = (attributes["page-height-tablet"] as Int).toPx()
                } 
                wordpress.components.UnitControl {
                    label = wordpress.i18n.gettext("Mobile")
                    isPressEnterToChange = true
                    units = controlUnits
                    onChange = {
                        value, _ ->
                        val obj: dynamic = object {}
                        obj["page-height-mobile"] = value.pxToInt()
                    } 
                    value = (attributes["page-height-mobile"] as Int).toPx()
                }
            }
        }
    }
}


// vi: se ts=4 sw=4 et:
