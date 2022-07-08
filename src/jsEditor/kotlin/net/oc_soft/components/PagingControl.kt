package net.oc_soft.components

import react.create

class PagingControl {

    /**
     * create paging control react element
     */
    operator fun invoke(
        attributes: dynamic,
        setAttributes: (dynamic)->Unit): react.ReactNode {

        return react.Fragment.create {

            wordpress.components.PanelBody {
                title = wordpress.i18n.gettext(
                    "Paging", 
                    "oc-slide")

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
