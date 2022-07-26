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

package net.oc_soft.slide.settings

import kotlin.collections.ArrayList

import kotlinx.css.px
import kotlinx.css.pct

/**
 * size setting
 */
class Size(
    /**
     * width
     */
    val width: Width,
    /**
     * height
     */
    val height: Height) {


    /**
     * class instance
     */
    companion object {

        /**
         * select width by media query
         */
        fun selectDevice(
            deviceWidth: Double?): String {

            return if (deviceWidth == null) {
                net.oc_soft.wordpress.DeviceMap.matchDevice 
            } else {
                net.oc_soft.wordpress.DeviceMap.matchDevice(deviceWidth)
            } 
        }

        /**
         * select vaue 
         */
        fun selectValue(
            deviceType: String,
            values: DoubleArray): Double {
            return when (deviceType) {
                "Mobile" -> values[2]
                "Tablet" -> values[1]
                else -> values[0]
            }
        }
    }

    /**
     * create style object
     */
    fun createStyle(
        deviceWidth: Double?): Array<Pair<String, String>> {
        val keyValues = ArrayList<Pair<String, String>>()
        
        val widthStr = when(width.option) {
            SizeOption.RELATIVE -> width.values[0].pct.toString()
            SizeOption.FIXED -> selectValue(
                selectDevice(deviceWidth), width.values).px.toString()
            else -> null
        }
        val height = when(height.option) {
            SizeOption.RELATIVE -> arrayOf(
                Pair("aspectRatio", 
                    "${height.values[0]} / ${height.values[1]}"))
            SizeOption.FIXED -> arrayOf(
                Pair("height", height.values[0].px.toString()))
            else -> emptyArray<Pair<String, String>>()
        }
        widthStr?.let {
            keyValues.add(Pair("width", widthStr))
        }
        keyValues.addAll(height)

        return keyValues.toTypedArray()
    }
}

// vi: se ts=4 sw=4 et:
