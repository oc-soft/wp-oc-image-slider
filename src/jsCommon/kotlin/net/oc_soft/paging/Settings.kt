package net.oc_soft.paging

import kotlin.js.Json

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
        fun createControlSettingFromAttribute(attr: dynamic): Json {
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
            return result
        }
    
    }
}



// vi: se ts=4 sw=4 et:
