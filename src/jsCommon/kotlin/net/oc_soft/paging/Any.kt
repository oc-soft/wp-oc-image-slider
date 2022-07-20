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
