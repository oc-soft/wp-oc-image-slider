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
                    "oc-slide")
                
                wordpress.components.TextControl {
                    Object.assign(this, object {
                        @JsName("min")
                        val min = 0
                    })
                    label = wordpress.i18n.gettext(
                        "Stop Duration (msec)",
                        "oc-slide")
                    value = duration
                    onChange = handleChangeDuration
                    type = "number"
                }


                wordpress.components.ToggleControl {
                    label = wordpress.i18n.gettext(
                        "Auto paging", "oc-slide")
                    checked = (attributes["paging-control-auto"] as Int) != 0
                    onChange = {
                        val obj: dynamic = object {}
                        obj["paging-control-auto"] = if (it) 1 else 0
                        setAttributes(obj)
                    }
                }
                wordpress.components.ToggleControl {
                    label = wordpress.i18n.gettext(
                        "Endless", "oc-slide")
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
                        "Auto backward", "oc-slide")
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
