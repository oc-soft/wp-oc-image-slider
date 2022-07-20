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
