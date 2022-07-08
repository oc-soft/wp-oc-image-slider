package net.oc_soft.px

/**
 * int object to px string
 */
fun Int.toPx(): String {
    return "${this}px"
}

/**
 * px string to int
 */
fun String.pxToInt(): Int {
    return take(indexOf("px")).toInt()
}

// vi: se ts=4 sw=4 et:
