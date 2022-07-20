package net.oc_soft.image

import kotlinx.browser.document

import org.w3c.dom.HTMLElement

import org.w3c.dom.ResizeObserver
import org.w3c.dom.ResizeObserverEntry


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
            attributes: dynamic): Array<
                Pair<HTMLElement, (HTMLElement)->HTMLElement>> {
            return Array<
                Pair<HTMLElement, (HTMLElement)->HTMLElement>>(
                imageUrls.size) {
                createContent(imageUrls[it], pagingContainer, attributes)
            } 
        }
        
        /**
         * create image content
         */
        fun createContent(
            imageUrl: ImageURL, 
            pagingContainer: HTMLElement,
            attributes: dynamic): 
            Pair<HTMLElement, (HTMLElement)->HTMLElement>  {
            val content = document.createElement("div") as HTMLElement
            

            val items = ArrayList<HTMLElement>()

            val resizeObserver = ResizeObserver {
                entries, observer ->
                items.forEach {
                    updateContentsStyle(
                        pagingContainer, attributes, imageUrl, it)
                }
            }
            resizeObserver.observe(pagingContainer)

            updateContentsStyle(pagingContainer, attributes, imageUrl, content)
            items.add(content)

            val cloneElem: (HTMLElement)->HTMLElement = {
                val elem = it.cloneNode(true) as HTMLElement
                items.add(elem)
                elem
            }

            return Pair(content, cloneElem)
        }


        /**
         * update contents style
         */
        fun updateContentsStyle(
            pagingContainer: HTMLElement,
            attributes: dynamic,
            imageUrl: ImageURL,
            contentElement: HTMLElement) {
            val bounds = pagingContainer.getBoundingClientRect()

            val imageSize = Size(bounds.width, bounds.height) 

            contentElement.style.width = "${imageSize.widthPx}"
            contentElement.style.height = "${imageSize.heightPx}"

            contentElement.style.background = createBackground(
                imageUrl, imageSize, attributes) 
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
