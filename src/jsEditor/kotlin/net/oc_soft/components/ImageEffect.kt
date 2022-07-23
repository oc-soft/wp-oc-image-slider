package net.oc_soft.components

import kotlinx.js.Object

import react.create

/**
 * image effect
 */
class ImageEffect {


    /**
     * class instance
     */
    companion object {

        /**
         * convert dynamic object into integer
         */
        fun dynamicToInt(obj: dynamic): Int? {
            return when (obj) {
                is String -> (obj as String).toIntOrNull()
                is Number -> obj.toInt()
                else -> throw IllegalArgumentException()
            }
        }
    }

    /**
     * create image effect control
     */
    operator fun invoke(
        attributes: dynamic,
        setAttributes: (dynamic)->Unit): react.ReactNode {
        return react.Fragment.create {
            wordpress.components.PanelBody {
                title = wordpress.i18n.gettext(
                    "Effect type",
                    "oc-image-slider")
                + createEffectSelector(attributes, setAttributes)
            }
            + createFadeControl(attributes, setAttributes)
            + createSlideControl(attributes, setAttributes)
            + createPushControl(attributes, setAttributes)
            + createRectControl(attributes, setAttributes)
            + createSquareControl(attributes, setAttributes)
            + createTurnControl(attributes, setAttributes)
        } 
    } 

    /**
     * create effect selector user interface
     */
    fun createEffectSelector(
        attributes: dynamic,
        setAttributes: (dynamic)->Unit): react.ReactNode {

        val handleChange: (dynamic)->Unit = {
            val selected = it as String
            val setting: dynamic = object {}
            setting["effect-type"] = selected
            setAttributes(setting)

        }
        
        val selected = attributes["effect-type"] as String

        

        return react.Fragment.create {
            wordpress.components.RadioControl {
                onChange = handleChange
                this.selected = selected
                options = arrayOf(
                    object {
                        @JsName("label")
                        var label =  
                            wordpress.i18n.gettext(
                                "Fade", "oc-image-slider")
                        @JsName("value")
                        var value = "fade"
                    },
                    object {
                        @JsName("label")
                        var label =  
                            wordpress.i18n.gettext(
                                "Slide", "oc-image-slider")
                        @JsName("value")
                        var value = "slide"
                    },
                    object {
                        @JsName("label")
                        var label =  
                            wordpress.i18n.gettext(
                                "Push", "oc-image-slider")
                        @JsName("value")
                        var value = "push"
                    },
                    object {
                        @JsName("label")
                        var label =  
                            wordpress.i18n.gettext(
                                "Rectangles", "oc-image-slider")
                        @JsName("value")
                        var value = "rect"
                    },
                    object {
                        @JsName("label")
                        var label =  
                            wordpress.i18n.gettext(
                                "Squares", "oc-image-slider")
                        @JsName("value")
                        var value = "square"
                    },
                    object {
                        @JsName("label")
                        var label =  
                            wordpress.i18n.gettext(
                                "Turning", "oc-image-slider")
                        @JsName("value")
                        var value = "turning"
                    }
                ) 
            } 
        } 
    }


    /**
     * save additional setting
     */
    fun saveAdditonalSetting(
        pagingType: String,
        attributes: dynamic,
        setAttributes: (dynamic)->Unit) {
        when (pagingType) {
            "fade" -> saveFadeAdditionalSetting(attributes, setAttributes)
            "slide" -> saveSlideAdditionalSetting(attributes, setAttributes)
            "push" -> savePushAdditionalSetting(attributes, setAttributes)
            "rect" -> saveRectAdditionalSetting(attributes, setAttributes)
            "square" -> saveSquareAdditionalSetting(attributes, setAttributes)
            "turn" -> saveTurnAdditionalSetting(attributes, setAttributes)
        }
    }


    /**
     * save fade additional setting
     */
    fun saveFadeAdditionalSetting(attributes: dynamic,
        setAttributes: (dynamic)->Unit) {

        arrayOf(
            "effect-face-duration",
            "effect-fade-in", 
            "effect-fade-out").forEach {

            val setting: dynamic = object {}
            setting[it] = attributes[it]
            setAttributes(setting)
        }
    }
    /**
     * save slide additional setting
     */
    fun saveSlideAdditionalSetting(attributes: dynamic,
        setAttributes: (dynamic)->Unit) {

        arrayOf(
            "effect-slide-duration",
            "effect-slide-forward-move-in", 
            "effect-slide-backward-mive-in",
            "effect-slide-forward-start-left",
            "effect-slide-forward-start-top",
            "effect-slide-forward-stop-left",
            "effect-slide-forward-stop-top",
            "effect-slide-backward-start-left", 
            "effect-slide-backward-start-top",
            "effect-slide-backward-stop-left", 
            "effect-slide-backward-stop-top").forEach {

            val setting: dynamic = object {}
            setting[it] = attributes[it]
            setAttributes(setting)
        }
    }

    /**
     * save push additional setting
     */
    fun savePushAdditionalSetting(attributes: dynamic,
        setAttributes: (dynamic)->Unit) {

        arrayOf("effect-push-duration",
            "effect-push-in-forward-left", 
            "effect-push-in-forward-top",
            "effect-push-out-forward-left", 
            "effect-push-out-forward-top",
            "effect-push-in-backward-left", 
            "effect-push-in-backward-top",
            "effect-push-out-backward-left", 
            "effect-push-out-backward-top").forEach {

            val setting: dynamic = object {}
            setting[it] = attributes[it]
            setAttributes(setting)
        }
    }

    /**
     * save rect additional setting
     */
    fun saveRectAdditionalSetting(attributes: dynamic,
        setAttributes: (dynamic)->Unit) {

        arrayOf("effect-rect-row-count",
            "effect-rect-column-count",
            "effect-rect-steps",
            "effect-rect-order",
            "effect-rect-duration-1",
            "effect-rect-duration-2",
            "effect-rect-forward-major-offset",
            "effect-rect-forward-minor-offset",
            "effect-rect-forward-major-direction",
            "effect-rect-forward-minor-direction",
            "effect-rect-forward-stride-offset",
            "effect-rect-backward-major-offset",
            "effect-rect-backward-minor-offset",
            "effect-rect-backward-major-direction",
            "effect-rect-backward-minor-direction",
            "effect-rect-backward-stride-offset",
            "effect-rect-forward-anchor-1",
            "effect-rect-forward-anchor-2",
            "effect-rect-backward-anchor-1", 
            "effect-rect-backward-anchor-2").forEach {
            val setting: dynamic = object {}
            setting[it] = attributes[it]
            setAttributes(setting)
        }
    }

    /**
     * save square additional setting
     */
    fun saveSquareAdditionalSetting(attributes: dynamic,
        setAttributes: (dynamic)->Unit) {

        arrayOf("effect-square-size",
            "effect-square-steps",
            "effect-square-order",
            "effect-square-duration-1",
            "effect-square-duration-2",
            "effect-square-forward-major-offset",
            "effect-square-forward-minor-offset",
            "effect-square-forward-major-direction",
            "effect-square-forward-minor-direction",
            "effect-square-forward-stride-offset",
            "effect-square-backward-major-offset",
            "effect-square-backward-minor-offset",
            "effect-square-backward-major-direction",
            "effect-square-backward-minor-direction",
            "effect-square-backward-stride-offset",
            "effect-square-forward-anchor-1",
            "effect-square-forward-anchor-2",
            "effect-square-backward-anchor-1", 
            "effect-square-backward-anchor-2").forEach {
            val setting: dynamic = object {}
            setting[it] = attributes[it]
            setAttributes(setting)
        }
    }

    /**
     * save turn additional setting
     */
    fun saveTurnAdditionalSetting(attributes: dynamic,
        setAttributes: (dynamic)->Unit) {
        arrayOf(
            "effect-turn-duration",
            "effect-turn-steps").forEach {
            val setting: dynamic = object {}
            setting[it] = attributes[it]
            setAttributes(setting)
        }
    }



    /**
     * create fade control
     */
    fun createFadeControl(
        attributes: dynamic,
        setAttributes: (dynamic)->Unit): react.ReactNode {

        val handleChange: (dynamic)->Unit = {

            val newValue = dynamicToInt(it)
            newValue?.let {
                val setting: dynamic = object {}
                setting["effect-fade-duration"] = it
                setAttributes(setting) 
            }
        }

        val duration = attributes["effect-fade-duration"] as Number
        

        val additionalAttr = object {
            @JsName("min")
            val min = 0
        }

        return react.Fragment.create {
            wordpress.components.PanelBody {
                title = wordpress.i18n.gettext(
                    "Fade",
                    "oc-image-slider")
                wordpress.components.TextControl {
                    label = wordpress.i18n.gettext(
                        "Duration (msec)", "oc-image-slider")

                    Object.assign(this, additionalAttr) 

                    type = "number"
                    value = duration  
                    onChange = handleChange
                } 
            }
        }
    }
    /**
     * create slide control
     */
    fun createSlideControl(
        attributes: dynamic,
        setAttributes: (dynamic)->Unit): react.ReactNode {

        val handleChange: (dynamic)->Unit = {

            val newValue = dynamicToInt(it)
            newValue?.let {
                val setting: dynamic = object {}
                setting["effect-slide-duration"] = it
                setAttributes(setting) 
            }
        }

        val duration = attributes["effect-slide-duration"] as Number
        

        val additionalAttr = object {
            @JsName("min")
            val min = 0
        }

        return react.Fragment.create {
            wordpress.components.PanelBody {
                title = wordpress.i18n.gettext(
                    "Slide",
                    "oc-image-slider")
                wordpress.components.TextControl {
                    label = wordpress.i18n.gettext(
                        "Duration (msec)", "oc-image-slider")

                    Object.assign(this, additionalAttr) 

                    type = "number"
                    value = duration  
                    onChange = handleChange
                } 
            }
        }
    }

    /**
     * create push control
     */
    fun createPushControl(
        attributes: dynamic,
        setAttributes: (dynamic)->Unit): react.ReactNode {

        val handleChange: (dynamic)->Unit = {

            val newValue = dynamicToInt(it)
            newValue?.let {
                val setting: dynamic = object {}
                setting["effect-push-duration"] = it
                setAttributes(setting) 
            }
        }

        val duration = attributes["effect-push-duration"] as Number
        

        val additionalAttr = object {
            @JsName("min")
            val min = 0
        }

        return react.Fragment.create {
            wordpress.components.PanelBody {
                title = wordpress.i18n.gettext(
                    "Push",
                    "oc-image-slider")
                wordpress.components.TextControl {
                    label = wordpress.i18n.gettext(
                        "Duration (msec)", "oc-image-slider")

                    Object.assign(this, additionalAttr) 

                    type = "number"
                    value = duration  
                    onChange = handleChange
                } 
            }
        }
    }

    /**
     * create rect control
     */
    fun createRectControl(
        attributes: dynamic,
        setAttributes: (dynamic)->Unit): react.ReactNode {

        val handleChangeRows: (dynamic)->Unit = {
            val newValue = dynamicToInt(it) 
            newValue?.let {
                val setting: dynamic = object {}
                setting["effect-rect-row-count"] = it
                setAttributes(setting) 
            }
        }
        val rowCount = attributes["effect-rect-row-count"] as Number
        
        val handleChangeColumns: (dynamic)->Unit = {
            val newValue = dynamicToInt(it) 
            newValue?.let {
                val setting: dynamic = object {}
                setting["effect-rect-column-count"] = it
                setAttributes(setting) 
            }
        }
        val columnCount = attributes["effect-rect-column-count"] as Number


        val handleColumnMajor: (Boolean) -> Unit = {
            val order = if (it) { "col" } else { "row" }
            val setting: dynamic = object {}
            setting["effect-rect-order"] = order
            setAttributes(setting)  
        }
        val columnAsMajor = attributes["effect-rect-order"] == "col"

        val handleReverseMajor: (Boolean) -> Unit = {

            val (forwardOffset, forwardDirection,
                backwardOffset, backwardDirection) = if (it) {
                intArrayOf(-1, -1, 0, 1)
            } else {
                intArrayOf(0, 1, -1, -1)    
            }
            val setting: dynamic = object {}
            setting["effect-rect-forward-major-offset"] = forwardOffset
            setting["effect-rect-forward-major-direction"] = forwardDirection
            setting["effect-rect-backward-major-offset"] = backwardOffset
            setting["effect-rect-backward-major-direction"] = backwardDirection
            setAttributes(setting)
        }
        val reverseMajor = 
            attributes["effect-rect-forward-major-direction"] == -1

        val handleReverseMinor: (Boolean) -> Unit = {

            val (forwardOffset, forwardDirection,
                backwardOffset, backwardDirection) = if (it) {
                intArrayOf(-1, -1, 0, 1)
            } else {
                intArrayOf(0, 1, -1, -1)    
            }
            val setting: dynamic = object {}
            setting["effect-rect-forward-minor-offset"] = forwardOffset
            setting["effect-rect-forward-minor-direction"] = forwardDirection
            setting["effect-rect-backward-minor-offset"] = backwardOffset
            setting["effect-rect-backward-minor-direction"] = backwardDirection
            setAttributes(setting)
        }
        val reverseMinor = 
            attributes["effect-rect-forward-minor-direction"] == -1

        val handleFragmentRToL: (Boolean)->Unit = {
            val (forwardAnchor, backwardAnchor) = if (it) {
                intArrayOf(2, 0)
            } else {
                intArrayOf(0, 2)
            }
            val setting: dynamic = object {}
            setting["effect-rect-forward-anchor-1"] = forwardAnchor
            setting["effect-rect-backward-anchor-1"] = backwardAnchor
            setAttributes(setting)
        }
        val fragmentRToL = attributes["effect-rect-forward-anchor-1"] != 0

        val handleFragmentBToT: (Boolean)->Unit = {
            val (forwardAnchor, backwardAnchor) = if (it) {
                intArrayOf(3, 1)
            } else {
                intArrayOf(1, 3)
            }
            val setting: dynamic = object {}
            setting["effect-rect-forward-anchor-2"] = forwardAnchor
            setting["effect-rect-backward-anchor-2"] = backwardAnchor
            setAttributes(setting)
        }
        val fragmentBToT = attributes["effect-rect-forward-anchor-2"] != 1 


        val handleChange1: (dynamic)->Unit = {

            val newValue = dynamicToInt(it) 
            newValue?.let {
                val setting: dynamic = object {}
                setting["effect-rect-duration-1"] = it
                setAttributes(setting) 
            }
        }

        val duration1 = attributes["effect-rect-duration-1"] as Number

        val handleChange2: (dynamic)->Unit = {

            val newValue = dynamicToInt(it) 
            newValue?.let {
                val setting: dynamic = object {}
                setting["effect-rect-duration-2"] = it
                setAttributes(setting) 
            }
        }

        val duration2 = attributes["effect-rect-duration-2"] as Number

        val additionalAttr1 = object {
            @JsName("min")
            val min = 1
            @JsName("max")
            val max = 100
        }


        val additionalAttr2 = object {
            @JsName("min")
            val min = 0
        }
        return react.Fragment.create {

            wordpress.components.PanelBody {
                title = wordpress.i18n.gettext(
                    "Retangles",
                    "oc-image-slider")
                wordpress.components.TextControl {
                    label = wordpress.i18n.gettext(
                        "Rows", "oc-image-slider")

                    Object.assign(this, additionalAttr1) 

                    type = "number"
                    value = rowCount
                    onChange = handleChangeRows
                } 
                wordpress.components.TextControl {
                    label = wordpress.i18n.gettext(
                        "Columns", "oc-image-slider")

                    Object.assign(this, additionalAttr1) 

                    type = "number"
                    value = columnCount
                    onChange = handleChangeColumns
                }


                wordpress.components.ToggleControl {
                    label = wordpress.i18n.gettext(
                        "Column as major", "oc-image-slider")
                    onChange = handleColumnMajor
                    checked = columnAsMajor
                }

                wordpress.components.ToggleControl {
                    label = wordpress.i18n.gettext(
                        "Reverse major", "oc-image-slider")
                    onChange = handleReverseMajor
                    checked = reverseMajor
                } 
                wordpress.components.ToggleControl {
                    label = wordpress.i18n.gettext(
                        "Reverse minor", "oc-image-slider")
                    onChange = handleReverseMinor
                    checked = reverseMinor
                }

                wordpress.components.ToggleControl {
                    label = wordpress.i18n.gettext(
                        "Fragment right to left", "oc-image-slider")
                    onChange = handleFragmentRToL
                    checked = fragmentRToL 
                }

                wordpress.components.ToggleControl {
                    label = wordpress.i18n.gettext(
                        "Fragment bottom to top", "oc-image-slider")
                    onChange = handleFragmentBToT
                    checked = fragmentBToT 
                }

                wordpress.components.TextControl {
                    label = wordpress.i18n.gettext(
                        "Duration 1 (msec)", "oc-image-slider")

                    Object.assign(this, additionalAttr2) 

                    type = "number"
                    value = duration1
                    onChange = handleChange1
                } 
                wordpress.components.TextControl {
                    label = wordpress.i18n.gettext(
                        "Duration 2 (msec)", "oc-image-slider")

                    Object.assign(this, additionalAttr2) 

                    type = "number"
                    value = duration2  
                    onChange = handleChange2
                } 
            }
        }
    }

    /**
     * create square control
     */
    fun createSquareControl(
        attributes: dynamic,
        setAttributes: (dynamic)->Unit): react.ReactNode {

        val handleChangeSize: (dynamic)->Unit = {

            val newValue = dynamicToInt(it) 
            newValue?.let {
                val setting: dynamic = object {}
                setting["effect-square-size"] = it
                setAttributes(setting) 
            }
        }
        val size = attributes["effect-square-size"] as Number

        val handleColumnMajor: (Boolean) -> Unit = {
            val order = if (it) { "col" } else { "row" }
            val setting: dynamic = object {}
            setting["effect-square-order"] = order
            setAttributes(setting)  
        }
        val columnAsMajor = attributes["effect-square-order"] == "col"

        val handleReverseMajor: (Boolean) -> Unit = {

            val (forwardOffset, forwardDirection,
                backwardOffset, backwardDirection) = if (it) {
                intArrayOf(-1, -1, 0, 1)
            } else {
                intArrayOf(0, 1, -1, -1)    
            }
            val setting: dynamic = object {}
            setting["effect-square-forward-major-offset"] = forwardOffset
            setting["effect-square-forward-major-direction"] = forwardDirection
            setting["effect-square-backward-major-offset"] = backwardOffset
            setting["effect-square-backward-major-direction"] = 
                backwardDirection
            setAttributes(setting)
        }
        val reverseMajor = 
            attributes["effect-square-forward-major-direction"] == -1

        val handleReverseMinor: (Boolean) -> Unit = {

            val (forwardOffset, forwardDirection,
                backwardOffset, backwardDirection) = if (it) {
                intArrayOf(-1, -1, 0, 1)
            } else {
                intArrayOf(0, 1, -1, -1)    
            }
            val setting: dynamic = object {}
            setting["effect-square-forward-minor-offset"] = forwardOffset
            setting["effect-square-forward-minor-direction"] = forwardDirection
            setting["effect-square-backward-minor-offset"] = backwardOffset
            setting["effect-square-backward-minor-direction"] = 
                backwardDirection
            setAttributes(setting)
        }
        val reverseMinor = 
            attributes["effect-square-forward-minor-direction"] == -1

        val handleFragmentRToL: (Boolean)->Unit = {
            val (forwardAnchor, backwardAnchor) = if (it) {
                intArrayOf(2, 0)
            } else {
                intArrayOf(0, 2)
            }
            val setting: dynamic = object {}
            setting["effect-square-forward-anchor-1"] = forwardAnchor
            setting["effect-square-backward-anchor-1"] = backwardAnchor
            setAttributes(setting)
        }
        val fragmentRToL = attributes["effect-square-forward-anchor-1"] != 0

        val handleFragmentBToT: (Boolean)->Unit = {
            val (forwardAnchor, backwardAnchor) = if (it) {
                intArrayOf(3, 1)
            } else {
                intArrayOf(1, 3)
            }
            val setting: dynamic = object {}
            setting["effect-square-forward-anchor-2"] = forwardAnchor
            setting["effect-square-backward-anchor-2"] = backwardAnchor
            setAttributes(setting)
        }
        val fragmentBToT = attributes["effect-square-forward-anchor-2"] != 1 

        val handleChange1: (dynamic)->Unit = {

            val newValue = dynamicToInt(it) 
            newValue?.let {
                val setting: dynamic = object {}
                setting["effect-square-duration-1"] = it
                setAttributes(setting) 
            }
        }

        val duration1 = attributes["effect-square-duration-1"] as Number

        val handleChange2: (dynamic)->Unit = {

            val newValue = dynamicToInt(it) 
            newValue?.let {
                val setting: dynamic = object {}
                setting["effect-square-duration-2"] = it
                setAttributes(setting) 
            }
        }

        val duration2 = attributes["effect-square-duration-2"] as Number

        val additionalAttr1 = object {
            @JsName("min")
            val min = 1 
            @JsName("max")
            val max = 100 

        }


        val additionalAttr2 = object {
            @JsName("min")
            val min = 0
        }

        return react.Fragment.create {

            wordpress.components.PanelBody {
                title = wordpress.i18n.gettext(
                    "Squares",
                    "oc-image-slider")
                wordpress.components.TextControl {
                    label = wordpress.i18n.gettext(
                        "Size", "oc-image-slider")

                    Object.assign(this, additionalAttr1) 

                    type = "number"
                    value = size 
                    onChange = handleChangeSize
                }
                wordpress.components.ToggleControl {
                    label = wordpress.i18n.gettext(
                        "Column as major", "oc-image-slider")
                    onChange = handleColumnMajor
                    checked = columnAsMajor
                }
                wordpress.components.ToggleControl {
                    label = wordpress.i18n.gettext(
                        "Reverse major", "oc-image-slider")
                    onChange = handleReverseMajor
                    checked = reverseMajor
                } 
                wordpress.components.ToggleControl {
                    label = wordpress.i18n.gettext(
                        "Reverse minor", "oc-image-slider")
                    onChange = handleReverseMinor
                    checked = reverseMinor
                }

                wordpress.components.ToggleControl {
                    label = wordpress.i18n.gettext(
                        "Fragment right to left", "oc-image-slider")
                    onChange = handleFragmentRToL
                    checked = fragmentRToL 
                }

                wordpress.components.ToggleControl {
                    label = wordpress.i18n.gettext(
                        "Fragment bottom to top", "oc-image-slider")
                    onChange = handleFragmentBToT
                    checked = fragmentBToT 
                }

                wordpress.components.TextControl {
                    label = wordpress.i18n.gettext(
                        "Duration 1 (msec)", "oc-image-slider")

                    Object.assign(this, additionalAttr2) 

                    type = "number"
                    value = duration1
                    onChange = handleChange1
                } 
                wordpress.components.TextControl {
                    label = wordpress.i18n.gettext(
                        "Duration 2 (msec)", "oc-image-slider")

                    Object.assign(this, additionalAttr2) 

                    type = "number"
                    value = duration2  
                    onChange = handleChange2
                } 
            }
        }
    }

    /**
     * create turning control
     */
    fun createTurnControl(
        attributes: dynamic,
        setAttributes: (dynamic)->Unit): react.ReactNode {

        val handleChange: (dynamic)->Unit = {

            val newValue = dynamicToInt(it)
            newValue?.let {
                val setting: dynamic = object {}
                setting["effect-turn-duration"] = it
                setAttributes(setting) 
            }
        }

        val duration = attributes["effect-turn-duration"] as Number
        

        val additionalAttr = object {
            @JsName("min")
            val min = 0
        }

        return react.Fragment.create {
            wordpress.components.PanelBody {
                title = wordpress.i18n.gettext(
                    "Turning",
                    "oc-image-slider")
                wordpress.components.TextControl {
                    label = wordpress.i18n.gettext(
                        "Duration (msec)", "oc-image-slider")

                    Object.assign(this, additionalAttr) 

                    type = "number"
                    value = duration  
                    onChange = handleChange
                } 
            }
        }
    }
}

// vi: se ts=4 sw=4 et:
