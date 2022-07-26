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

package net.oc_soft.paging
import kotlin.text.toInt

/**
 * convert to boolean
 */
fun Any.toBoolean(): Boolean {
    return when (this) {
        is String -> (this as String).toBoolean()
        is Number -> toInt() != 0
        is Boolean -> this
        else -> throw IllegalArgumentException()
    }
}


/**
 * convert to int
 */
fun Any.toInt():Int {
    return when (this) {
        is String -> (this as String).toInt()
        is Number -> toInt() 
        else -> throw IllegalArgumentException()
    }
}


// vi: se ts=4 sw=4 et:
