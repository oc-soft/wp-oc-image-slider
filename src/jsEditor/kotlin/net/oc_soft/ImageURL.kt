package net.oc_soft

import org.w3c.dom.url.URL
import org.w3c.dom.url.URLSearchParams


/**
 * url element 
 */
class ImageURL( 
    /**
     * url
     */
    val url: URL,
    /**
     * width
     */
    val width: Int,
    /**
     * height
     */
    val height: Int) {

    val urlFields: Array<String>  get() = arrayOf(
        url.href, url.origin, url.protocol, url.username, url.password,
        url.host, url.port, url.pathname, url.hash)

    /**
     * hash code
     */
    override fun hashCode(): Int {
        var result = 0
        urlFields.forEach {
            result = result xor it.hashCode()
        }
        result = result xor width
        result = result xor height
        return result
    }

    /**
     * equals
     */
    override operator fun equals(other: Any?): Boolean {

        var result = other === other

        if (!result) {
            result = other is ImageURL
            if (result) {
                val otherURL = other as ImageURL

                val fields0 = this.urlFields
                val fields1 = otherURL.urlFields

                for (idx in fields0.indices) {
                    result = fields0[idx] == fields1[idx]
                    if (!result) {
                        break
                    }
                } 
                if (result) {
                    result = width == other.width
                }
                if (result) {
                    result = height == other.height
                }
            }
        }
        return result
    }
}


// vi: se ts=4 sw=4 et:
