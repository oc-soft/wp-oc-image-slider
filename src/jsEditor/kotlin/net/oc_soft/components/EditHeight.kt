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

                wordpress.components.PanelRow {
                    wordpress.components.Button {
                        text = wordpress.i18n.gettext(
                            "Auto Fill", "oc-image-slider")
                        className = "oc-command-button"

                        variant = "secondary" 
                        isSmall = true
                    }
                }
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
