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

package net.oc_soft

import kotlin.text.Regex
import kotlin.text.lowercase
import kotlin.text.uppercase

/**
 * convert dash style to camel case
 */
fun String.dashStyleToCamel(): String {

    val result = replace(Regex("-([a-z])")) {
        it.groups[1]!!.value.uppercase()
    }

    return result
}

/**
 * convert camel style to dash style
 */
fun String.camelToDashStyle(): String {

    val result = replace(Regex("([a-z0-9])([A-Z])")) {
        it.groups[1]!!.value + "-" + it.groups[2]!!.value.lowercase()
    }
    return result
}


// vi: se ts=4 sw=4 et:
