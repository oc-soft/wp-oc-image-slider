package net.oc_soft

import kotlin.text.Regex
import kotlin.text.lowercase
import kotlin.text.uppercase

/**
 * convert dash style to camel case
 */
fun String.dashStyleToCamel(): String {

    val words0 = split("-")

    val words1 = Array<String>(words0.size) {
        val word = words0[it]
        if (!word.isEmpty() && it != 0) {
            word[0].uppercase() + word.substring(1)
        } else {
            word
        }
    }
    return words1.joinToString("")
}


// vi: se ts=4 sw=4 et:
