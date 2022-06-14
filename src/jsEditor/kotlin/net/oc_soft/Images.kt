package net.oc_soft

import kotlin.js.Json
import kotlinx.js.Object
import kotlinx.browser.document

import org.w3c.dom.HTMLElement

import wordpress.components.Icon

import react.create
import react.dom.html.ReactHTML.div
import net.oc_soft.geom.Size
import net.oc_soft.slide.Panel

/**
 * manage images
 */
class Images {


    /**
     * class instance
     */
    companion object {
    }


    /**
     * image urls 
     */
    var imageUrls = ImageURLs()


    /**
     * slide panel which has contoller and paiging
     */
    val slidePanel = Panel()


    /**
     * create react element
     */
    operator fun invoke(
        props: dynamic,
        blockProps: dynamic,
        size: Size?,
        settings: Json): react.ReactElement<*> {

        val setAttrs: (dynamic)->Unit = props.setAttributes
        val attrs: dynamic = props.attributes 
        val pagingSize = getPagingSize(size)
        val pagingController = react.useRef<HTMLElement>()
        react.useEffect {
            val paging = pagingController.current?.let {
                bindPaging(it, pagingSize, settings, attrs)
            }
            cleanup {
                pagingController.current?.let {
                    val controller = it
                    paging?.let {
                        unbindPaging(it , controller, setAttrs)
                    }
                }
            }
        }
        val styleContainingImageAndController = object {
            @JsName("position")
            val position = "relative"
        }.unsafeCast<react.CSSProperties>()
        
        pagingSize?.let {
            val size0 = it
            Object.assign(styleContainingImageAndController, object {
                @JsName("width")
                val width = size0.widthPx
                @JsName("height")
                val height = size0.heightPx
            })
        }


        return react.Fragment.create {
            div {
                Object.assign(this, blockProps) 
                
                div {
                    style = styleContainingImageAndController
                    ref = pagingController
                }
            }
        }
    }

    /**
     * attach this object into pager
     */
    fun bindPaging(
        controllerRootElement: HTMLElement,
        pagingSize: Size?,
        settings: Json,
        attributes: dynamic): Paging {

        
        val contentsLoader: ()->Array<HTMLElement> = {
            createContents(pagingSize, attributes)
        }

        val pageIndex = (attributes["editor-page"] as Any?)?.let {
            it as Int 
        }?: 0

        slidePanel.bind(controllerRootElement)
        return slidePanel.bindPaging(pageIndex, settings, contentsLoader)
    }

    /**
     * detach this object from pager
     */
    fun unbindPaging(
        paging: Paging,
        controllerRootElement: HTMLElement,
        setAttributes: (dynamic)->Unit) {

        paging.pageIndex?.let {
            val pageIndex = it
            setAttributes( object {
                @JsName("editorName")
                val editorPage = pageIndex
            })
        }
        slidePanel.unbindPaging()
        slidePanel.unbind()    
    }

    /**
     * get paging size
     */
    fun getPagingSize(size: Size?): Size? {
        return if (size != null) {
            size
        } else {
            imageUrls.findBetterMatchSize()
        }
    }


    /**
     * create contents
     */
    fun createContents(
        size: Size?,
        attributes: dynamic): Array<HTMLElement> {
        return Array<HTMLElement>(imageUrls.size) {
            createContent(imageUrls[it], size, attributes)
        } 
    }
    
    /**
     * create image content
     */
    fun createContent(
        imageUrl: ImageURL, 
        size: Size?,
        attributes: dynamic): HTMLElement {
        val result = document.createElement("div") as HTMLElement

        val imageSize = 
            size?: Size(imageUrl.width.toDouble(), imageUrl.height.toDouble()) 

        result.style.width = "${imageSize.widthPx}"
        result.style.height = "${imageSize.heightPx}"

        result.style.background = createBackground(imageUrl, size, attributes) 
        return result
    }


    /**
     * create backgrounds
     */
    fun createBackgrounds(
        size: Size?, 
        attributes: dynamic): Array<String> {
        return Array<String>(imageUrls.size) {
            createBackground(imageUrls[it], size, attributes)
        }
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



// vi: se ts=4 sw=4 et:
