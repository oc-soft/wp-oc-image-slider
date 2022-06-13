package net.oc_soft.geom

import kotlin.math.sign
import kotlin.math.roundToInt


class Size(
    val width: Double = 0.0,
    val height: Double = 0.0): Comparable<Size> {


    /**
     * width as pixel
     */
    val widthPx: String get() = "${width.roundToInt()}px"

    /**
     * height as pixel
     */
    val heightPx: String get() = "${height.roundToInt()}px"

    
    /**
     * first component
     */
    operator fun component1(): Double {
        return width
    }

    /**
     * second component
     */
    operator fun component2(): Double {
        return height
    }

    /**
     * You get true if this contains other
     */
    fun contains(other: Size): Boolean {
        return width >= other.width && height >= other.height
    }

    /**
     * compare 
     */
    override operator fun compareTo(other: Size): Int {
        var result = (width - other.width).sign.roundToInt()
        if (result == 0) {
            result = (height - other.height).sign.roundToInt()
        }
        return result
    }
    

    /**
     * calculate hash code
     */
    override fun hashCode(): Int {
        return width.hashCode() xor height.hashCode()
    }


    /**
     * compare deeply 
     */
    override fun equals(other: Any?): Boolean {
        var result = this === other
        if (!result) {
            result = other is Size
            if (result) {
                result = this.compareTo(other as Size) == 0
            }
        }
        return result 
    } 
}


// vi: se ts=4 sw=4 et:
