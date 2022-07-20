package net.oc_soft.paging

import kotlin.js.Json
import kotlin.text.toBoolean
import kotlin.text.toInt

import kotlinx.js.Object

/**
 * page settings helper
 */
class Settings {

    /**
     * class instance
     */
    companion object {
        /**
         * pager setting
         */
        fun createControlSettingFromAttributes(attr: dynamic): Json {
            val result = kotlin.js.json() 
            var settingValue = attr["paging-control-loop"] as Any?

            result["loop"] = when (settingValue) {
                is String -> settingValue.toInt() != 0
                is Number -> settingValue.toInt() != 0
                else -> false 
            } 
            settingValue = attr["paging-control-auto"] 

            result["auto"] = when (settingValue) {
                is String -> settingValue.toInt() != 0
                is Number -> settingValue.toInt() != 0
                else -> false 
            }

            result["color-1"] = attr["paging-control-color-1"]
            result["color-2"] = attr["paging-control-color-2"]
            
            settingValue = attr["paging-control-auto-direction"]
            result["auto-direction"] = when (settingValue) {
                is String -> settingValue.toInt()
                is Number -> settingValue
                else -> 0
            }
            settingValue = attr["paging-control-stop-duration"]
            result["auto-paging-stop-duration"] = when (settingValue) {
                is String -> settingValue.toInt()
                is Number -> settingValue
                else -> 0
            }


            return result
        }


        /**
         * create paging setting from attribute
         */
        fun createPagingSettingsFromAttributes(attr: dynamic): Json {

            var settingValue = attr["effect-type"] 
            
            val pagingSetting = when (settingValue) {
                "push" -> createPushSettingFromAttributes(attr)
                "slide" -> createSlideSettingFromAttributes(attr)
                "rect" -> createFragmentsRectSettingFromAttributes(attr)
                "square" -> createFragmentsSquareSettingFromAttributes(attr)
                "turning" -> createTurningSettingFromAttributes(attr)
                "fade" -> createFadeSettingFromAttributes(attr)
                else -> createFadeSettingFromAttributes(attr)
            }

            val result = kotlin.js.json()
            result["settings"] = arrayOf(pagingSetting)
            result["control"] =
                createControlSettingFromAttributes(attr)

            return result
        }

        /**
         * create fade setting from attribute
         */
        fun createFadeSettingFromAttributes(attr: dynamic): Json {
            
            var settingValue = attr["effect-fade-duration"] as Any?
            val forBackCommon = kotlin.js.json()
            settingValue?.let {
                forBackCommon["duration"] = it
            }

            arrayOf(arrayOf("effect-fade-in", "fade-in"),
                arrayOf("effect-fade-out", "fade-out")).forEach {
                settingValue = attr[it[0]] as Any?
                val destKey = it[1]
                settingValue?.let {
                    forBackCommon[destKey] = when (it) {
                        is String -> it.toBoolean()
                        is Boolean -> it 
                        is Number -> it.toInt() != 0
                        else -> true
                    }
                }
            }

            val result = kotlin.js.json()
            val animation = kotlin.js.json()
            animation["next-page"] = Object.assign(
                object {}, forBackCommon)
            animation["previous-page"] = Object.assign(
                object {}, forBackCommon)
            result["animation"] = animation
            result["type"] = "fade-page"
            return result
        }

        /**
         * create slide setting from attribute
         */
        fun createSlideSettingFromAttributes(attr: dynamic): Json {
            
            var settingValue = attr["effect-slide-duration"] as Any?
            val forBackCommon = kotlin.js.json()
            settingValue?.let {
                forBackCommon["duration"] = it
            }

            
            val nextSetting = Object.assign(
                object {}, forBackCommon) as Json
            val prevSetting = Object.assign(
                object {}, forBackCommon) as Json


            settingValue = attr["effect-slide-forward-move-in"]
            nextSetting["move-in"] = when (settingValue) {
                is String -> settingValue.toBoolean()
                is Number -> settingValue.toInt() != 0
                else -> true
            }
            settingValue = attr["effect-slide-backward-move-in"]
            prevSetting["move-in"] = when (settingValue) {
                is String -> settingValue.toBoolean()
                is Number -> settingValue.toInt() != 0
                else -> false
            }


            var keys = arrayOf(
                "effect-slide-forward-start-left", 
                "effect-slide-forward-start-top")
            
            nextSetting["start-position"] = Array<Int>(keys.size) {
                val settingValue0 = attr[keys[it]] as Any?
                when (settingValue0) {
                    is String -> settingValue0.toInt()
                    is Number -> settingValue0.toInt()
                    else -> 0
                } 
            }
            keys = arrayOf(
                "effect-slide-forward-stop-left", 
                "effect-slide-forward-stop-top")
            
            nextSetting["stop-position"] = Array<Int>(keys.size) {
                val settingValue0 = attr[keys[it]] as Any?
                when (settingValue0) {
                    is String -> settingValue0.toInt()
                    is Number -> settingValue0.toInt()
                    else -> 0
                } 
            }
            keys = arrayOf(
                "effect-slide-backward-start-left", 
                "effect-slide-backward-start-top")
            
            prevSetting["start-position"] = Array<Int>(keys.size) {
                val settingValue0 = attr[keys[it]] as Any?
                when (settingValue0) {
                    is String -> settingValue0.toInt()
                    is Number -> settingValue0.toInt()
                    else -> 0
                } 
            }            
            keys = arrayOf(
                "effect-slide-backward-stop-left", 
                "effect-slide-backward-stop-top")
            
            prevSetting["stop-position"] = Array<Int>(keys.size) {
                val settingValue0 = attr[keys[it]] as Any?
                when (settingValue0) {
                    is String -> settingValue0.toInt()
                    is Number -> settingValue0.toInt()
                    else -> 0
                } 
            }

            val result = kotlin.js.json()
            val animation = kotlin.js.json()
            animation["next-page"] = nextSetting
            animation["previous-page"] = prevSetting
            result["animation"] = animation
            result["type"] = "slide-page"
            return result
        }


        /**
         * create push setting from attribute
         */
        fun createPushSettingFromAttributes(attr: dynamic): Json {
            
            var settingValue = attr["effect-push-duration"] as Any?
            val forBackCommon = kotlin.js.json()
            settingValue?.let {
                forBackCommon["duration"] = it
            }

            
            val nextSetting = Object.assign(
                object {}, forBackCommon) as Json
            val prevSetting = Object.assign(
                object {}, forBackCommon) as Json

            var keys = arrayOf(
                "effect-push-in-forward-left", 
                "effect-push-in-forward-top")
            
            nextSetting["push-in-position"] = Array<Int>(keys.size) {
                val settingValue0 = attr[keys[it]] as Any?
                when (settingValue0) {
                    is String -> settingValue0.toInt()
                    is Number -> settingValue0.toInt()
                    else -> 0
                } 
            }
            keys = arrayOf(
                "effect-push-out-forward-left", 
                "effect-push-out-forward-top")
            
            nextSetting["push-out-position"] = Array<Int>(keys.size) {
                val settingValue0 = attr[keys[it]] as Any?
                when (settingValue0) {
                    is String -> settingValue0.toInt()
                    is Number -> settingValue0.toInt()
                    else -> 0
                } 
            }
            keys = arrayOf(
                "effect-push-in-backward-left", 
                "effect-push-in-backward-top")
            
            prevSetting["push-in-position"] = Array<Int>(keys.size) {
                val settingValue0 = attr[keys[it]] as Any?
                when (settingValue0) {
                    is String -> settingValue0.toInt()
                    is Number -> settingValue0.toInt()
                    else -> 0
                } 
            }            
            keys = arrayOf(
                "effect-push-out-backward-left", 
                "effect-push-out-backward-top")
            
            prevSetting["push-out-position"] = Array<Int>(keys.size) {
                val settingValue0 = attr[keys[it]] as Any?
                when (settingValue0) {
                    is String -> settingValue0.toInt()
                    is Number -> settingValue0.toInt()
                    else -> 0
                } 
            }

            val result = kotlin.js.json()
            val animation = kotlin.js.json()
            animation["next-page"] = nextSetting
            animation["previous-page"] = prevSetting
            result["animation"] = animation
            result["type"] = "push-page"
            return result
        }

        /**
         * create fragments rect setting from attribute
         */
        fun createFragmentsRectSettingFromAttributes(attr: dynamic): Json {
            
            val forBackCommon = kotlin.js.json("type" to "rect")

            var settingValue = attr["effect-rect-row-count"] as Any?
            settingValue?.let {
                forBackCommon["row-count"] = it
            }
            settingValue = attr["effect-rect-column-count"]
            settingValue?.let {
                forBackCommon["column-count"] = it
            }
            settingValue =  attr["effect-rect-steps"]
            settingValue?.let {
                forBackCommon["steps"] = it
            }

            val forBackAnimCommon = kotlin.js.json()
            settingValue = attr["effect-rect-order"] as Any?
            settingValue?.let {
                forBackAnimCommon["order"] = it
            }
 
            settingValue = attr["effect-rect-duration-1"] 
            settingValue?.let {
                forBackAnimCommon["duration"] = (it as Any).toInt()
            }
            settingValue = attr["effect-rect-duration-2"]
            settingValue?.let {
                forBackAnimCommon["delay"] = (it as Any).toInt()
            }
            
            val nextSetting = Object.assign(
                object {}, forBackCommon) as Json
            val prevSetting = Object.assign(
                object {}, forBackCommon) as Json

            val nextAnimSetting = kotlin.js.json()
            Object.assign(nextAnimSetting, forBackAnimCommon)
            arrayOf(
                arrayOf("effect-rect-forward-major-offset",
                    "major-offset"), 
                arrayOf("effect-rect-forward-minor-offset",
                    "minor-offset"),
                arrayOf("effect-rect-forward-major-direction",
                    "major-direction"),
                arrayOf("effect-rect-forward-minor-direction",
                    "minor-direction"),
                arrayOf("effect-rect-forward-stride-offset",
                    "stride-offset")).forEach {
                val (srcKey, destKey) = it
                nextAnimSetting[destKey] = attr[srcKey]
            }
            val prevAnimSetting = kotlin.js.json()
            Object.assign(prevAnimSetting, forBackAnimCommon)
            arrayOf(
                arrayOf("effect-rect-backward-major-offset",
                    "major-offset"), 
                arrayOf("effect-rect-backward-minor-offset",
                    "minor-offset"),
                arrayOf("effect-rect-backward-major-direction",
                    "major-direction"),
                arrayOf("effect-rect-backward-minor-direction",
                    "minor-direction"),
                arrayOf("effect-rect-backward-stride-offset",
                    "stride-offset")).forEach {
                val (srcKey, destKey) = it
                prevAnimSetting[destKey] = attr[srcKey]
            }

            var keys = arrayOf("effect-rect-forward-anchor-1",
                "effect-rect-forward-anchor-2")
            
            nextSetting["anchor"] = Array<Int>(keys.size) {
                val settingValue0 = attr[keys[it]] as Any?
                settingValue0?.let {
                    it.toInt() 
                }?: it
            }

            keys = arrayOf(
                "effect-rect-backward-anchor-1", 
                "effect-rect-backward-anchor-2")
            
            prevSetting["anchor"] = Array<Int>(keys.size) {
                val settingValue0 = attr[keys[it]] as Any?
                settingValue0?.let {
                    it.toInt()
                }?: it
            }
            nextSetting["animation"] = nextAnimSetting
            prevSetting["animation"] = prevAnimSetting
            val result = kotlin.js.json()
            val animation = kotlin.js.json()
            animation["next-page"] = nextSetting
            animation["previous-page"] = prevSetting
            result["animation"] = animation
            result["type"] = "fragments-page"
            return result
        }
        /**
         * create fragments square setting from attribute
         */
        fun createFragmentsSquareSettingFromAttributes(attr: dynamic): Json {
            
            
            val forBackCommon = kotlin.js.json("type" to "square")
            
            var settingValue = attr["effect-square-size"] as Any?
            settingValue?.let {
                forBackCommon["size"] = it
            }

            settingValue =  attr["effect-square-steps"]
            settingValue?.let {
                forBackCommon["steps"] = it
            }


            val forBackAnimCommon = kotlin.js.json()
            settingValue = attr["effect-square-order"] as Any?
            settingValue?.let {
                forBackAnimCommon["order"] = it
            }
            settingValue = attr["effect-square-duration-1"] 
            settingValue?.let {
                forBackAnimCommon["duration"] = (it as Any).toInt()
            }
            settingValue = attr["effect-square-duration-2"]
            settingValue?.let {
                forBackAnimCommon["delay"] = (it as Any).toInt()
            }
            
            val nextSetting = Object.assign(
                object {}, forBackCommon) as Json
            val prevSetting = Object.assign(
                object {}, forBackCommon) as Json

            val nextAnimSetting = kotlin.js.json()
            Object.assign(nextAnimSetting, forBackAnimCommon)
            arrayOf(
                arrayOf("effect-square-forward-major-offset",
                    "major-offset"), 
                arrayOf("effect-square-forward-minor-offset",
                    "minor-offset"),
                arrayOf("effect-square-forward-major-direction",
                    "major-direction"),
                arrayOf("effect-square-forward-minor-direction",
                    "minor-direction"),
                arrayOf("effect-square-forward-stride-offset",
                    "stride-offset")).forEach {
                val (srcKey, destKey) = it
                nextAnimSetting[destKey] = attr[srcKey]
            }
            val prevAnimSetting = kotlin.js.json()
            Object.assign(prevAnimSetting, forBackAnimCommon)
            arrayOf(
                arrayOf("effect-square-backward-major-offset",
                    "major-offset"), 
                arrayOf("effect-square-backward-minor-offset",
                    "minor-offset"),
                arrayOf("effect-square-backward-major-direction",
                    "major-direction"),
                arrayOf("effect-square-backward-minor-direction",
                    "minor-direction"),
                arrayOf("effect-square-backward-stride-offset",
                    "stride-offset")).forEach {
                val (srcKey, destKey) = it
                prevAnimSetting[destKey] = attr[srcKey]
            }

            var keys = arrayOf("effect-square-forward-anchor-1",
                "effect-square-forward-anchor-2")
            
            nextSetting["anchor"] = Array<Int>(keys.size) {
                val settingValue0 = attr[keys[it]] as Any?
                settingValue0?.let {
                    it.toInt() 
                }?: it
            }

            keys = arrayOf(
                "effect-square-backward-anchor-1", 
                "effect-square-backward-anchor-2")
            
            prevSetting["anchor"] = Array<Int>(keys.size) {
                val settingValue0 = attr[keys[it]] as Any?
                settingValue0?.let {
                    it.toInt()
                }?: it
            }
            nextSetting["animation"] = nextAnimSetting
            prevSetting["animation"] = prevAnimSetting
            val result = kotlin.js.json()
            val animation = kotlin.js.json()
            animation["next-page"] = nextSetting
            animation["previous-page"] = prevSetting
            result["animation"] = animation
            result["type"] = "fragments-page"
            return result
        }

        /**
         * create turning setting from attribute
         */
        fun createTurningSettingFromAttributes(attr: dynamic): Json {
            
            var settingValue = attr["effect-turn-duration"] as Any?
            



            val forBackCommon = kotlin.js.json()
            settingValue?.let {
                forBackCommon["duration"] = it
            }

            val result = kotlin.js.json()

            settingValue = attr["effect-turn-steps"]
            settingValue?.let {
                result["steps"] = it
            }


            val animation = Object.assign(object {}, forBackCommon)
            result["animation"] = animation
            result["type"] = "turn-page"
            return result
        }

    }
}



// vi: se ts=4 sw=4 et:
