package net.oc_soft

import org.w3c.dom.url.URL

/**
 * an image
 */
data class Image(
    /**
     * url
     */
    val url: URL,
    /**
     * id
     */
    val id: String,
    /**
     * image width
     */
    val width: String,
    /**
     * image height
     */
    val height: String)

// vi: se ts=4 sw=4 et:
