package net.oc_soft.components

import react.create

import net.oc_soft.slide.settings.Width as WidthSettings
import net.oc_soft.slide.settings.SizeOption

import net.oc_soft.px.pxToInt
import net.oc_soft.px.toPx

/**
 * width editor
 */
class EditWidth  {


    /**
     * relative value
     */
    var relativeValue: Int = 100
    /**
     * desktop size
     */
    var desktopSize: Int = 300
    /**
     * table size
     */
    var tabletSize: Int = 200
    /**
     * mobile size
     */
    var mobileSize: Int = 100 

    /**
     * command for reducer
     */
    data class Command(
        /**
         * command kind
         */
        val kind: String,
        /**
         * value
         */
        val value: Any? = null) 


    /**
     * create react element
     */
    operator fun invoke(
        attributes: dynamic,
        setAttributes: (dynamic)->Unit): react.ReactNode {

        return react.Fragment.create {
            val controlUnits = arrayOf(
                object {
                    @JsName("value")
                    val value = "px"

                    @JsName("label")
                    val label = "px"
                }
            )

            wordpress.components.PanelBody {
                title = wordpress.i18n.gettext(
                    "Width", 
                    "oc-image-slider")

                wordpress.components.ToggleControl {
                    label = wordpress.i18n.gettext(
                        "Use fixed values", "oc-image-slider")
                    checked = (attributes["page-width-option"] as Int) != 0
                    onChange = {
                        val obj: dynamic = object {}
                        obj["page-width-option"] = if (it) 1 else 0
                        setAttributes(obj)
                    }
                }

                wordpress.components.RangeControl {
                    label = wordpress.i18n.gettext(
                        "Relative", "oc-image-slider") 
                    value = (attributes["page-width-relative"] as Number)
                    min = 0
                    max = 100
                    initialPosition = 100
                    onChange = {
                        val obj: dynamic = object {}
                        obj["page-width-relative"] = it
                        setAttributes(obj) 
                    }
                }

                wordpress.components.Divider {
                }

                wordpress.components.UnitControl {
                    label = wordpress.i18n.gettext("Desktop")
                    units = controlUnits
                    isPressEnterToChange = true
                    onChange = {
                        value, _ ->
                        val obj: dynamic = object {}
                        obj["page-width-desktop"] = value.pxToInt()
                        setAttributes(obj)
                    }
                    value = (attributes["page-width-desktop"] as Int).toPx()
                }
                wordpress.components.UnitControl {
                    label = wordpress.i18n.gettext("Tablet")
                    isPressEnterToChange = true
                    units = controlUnits
                    onChange = {
                        value, _ ->
                        val obj: dynamic = object {}
                        obj["page-width-tablet"] = value.pxToInt()
                        setAttributes(obj)
                    }
                    value = (attributes["page-width-tablet"] as Int).toPx()
                } 
                wordpress.components.UnitControl {
                    label = wordpress.i18n.gettext("Mobile")
                    isPressEnterToChange = true
                    units = controlUnits
                    onChange = {
                        value, _ ->
                        val obj: dynamic = object {}
                        obj["page-width-mobile"] = value.pxToInt()
                        setAttributes(obj)
                    }
                    value = (attributes["page-width-mobile"] as Int).toPx()
                }
            }
        }
    }
}  

// vi: se ts=4 sw=4 et:
