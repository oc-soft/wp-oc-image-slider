package net.oc_soft.image

import kotlinx.browser.document

import org.w3c.dom.HTMLElement


import net.oc_soft.ImageURLs
import net.oc_soft.ImageURL
import net.oc_soft.geom.Size

/**
 * contents manager
 */
class ContentsMgr {

    /**
     * class instance
     */
    companion object {

        /**
         * create contents
         */
        fun createContents(
            imageUrls: ImageURLs,
            pagingContainer: HTMLElement,
            attributes: dynamic): Array<HTMLElement> {
            return Array<HTMLElement>(imageUrls.size) {
                createContent(imageUrls[it], pagingContainer, attributes)
            } 
        }
        
        /**
         * create image content
         */
        fun createContent(
            imageUrl: ImageURL, 
            pagingContainer: HTMLElement,
            attributes: dynamic): HTMLElement {
            val result = document.createElement("div") as HTMLElement

            val bounds = pagingContainer.getBoundingClientRect()

            val imageSize = Size(bounds.width, bounds.height) 

            result.style.width = "${imageSize.widthPx}"
            result.style.height = "${imageSize.heightPx}"

            result.style.background = createBackground(
                imageUrl, imageSize, attributes) 
            return result
        }
        
        /**
         * create background string
         */
        fun createBackground(
            imageUrl: ImageURL, 
            size: Size?,
            attributes: dynamic): String {

            val bgPosSize = size?.let {
                val imageSize = Size(
                    imageUrl.width.toDouble(), 
                    imageUrl.height.toDouble())
                
                val bgSize = if (it.contains(imageSize)) {
                    "contain" 
                } else {
                    val ratioW = it.width / imageSize.width
                    val ratioH = it.height / imageSize.height
                    if (ratioW < ratioH) {
                        "${it.widthPx} auto"
                    } else {
                        "auto ${it.heightPx}"
                    }
                }
                "center/${bgSize}"
            }?: "center"

            val colorSetting = attributes["page-color"] as Any?
            val colorStr = colorSetting?.let {
                if (it is String) it else ""
            }?: ""

            
            val imageBg = arrayOf(
                colorStr,
                "url(\"${imageUrl.url}\")",
                bgPosSize,
                "no-repeat").joinToString(" ")

            return imageBg
            
        }
    }

}

// vi: se ts=4 sw=4 et:
