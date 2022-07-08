package net.oc_soft.components

import react.create
import react.dom.html.ReactHTML.span

/**
 * aspect ratio
 */
class AspectRatio {


    /**
     * create aspect ratio input
     */
    operator fun invoke(
        attributes: dynamic,
        setAttributes: (dynamic)->Unit,
        nominatorName: String,
        denominatorName: String): react.ReactNode {

        return react.Fragment.create {
            wordpress.components.Flex {
                direction = "row"

                wordpress.components.Flex {
                    wordpress.components.FlexItem {
                        wordpress.components.InputControl {
                            type = "number"
                            isPressEnterToChange = true
                            value = (attributes[
                                nominatorName] as Number).toString()
                            onChange = {
                                val setting: dynamic = object {}
                                setting[nominatorName] = it.toInt()
                                setAttributes(setting)
                            }
                        }
                    }
                    wordpress.components.FlexItem {
                        span {
                            + "/"
                        }
                    }
                    wordpress.components.FlexItem {
                        wordpress.components.InputControl {
                            type = "number"
                            isPressEnterToChange = true
                            value = (
                                attributes[
                                    denominatorName] as Number).toString() 
                            onChange = {
                                val setting: dynamic = object {}
                                setting[denominatorName] = it.toInt()
                                setAttributes(setting)
                            }
                        }
                    }
                }
            }
        }
    }
}

// vi: se ts=4 sw=4 et:
