package net.oc_soft

import kotlin.js.Json
import kotlinx.js.Object
import kotlinx.browser.document

import org.w3c.dom.HTMLElement


import react.create
import react.dom.html.ReactHTML.div
import net.oc_soft.slide.settings.Size
import net.oc_soft.slide.Panel
import net.oc_soft.image.ContentsMgr as ImageContentsMgr

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
     * paging listener
     */
    var pagingListener: ((String, Paging)->Unit)? = null

    /**
     *  page index
     */
    var pageIndex: Int? = null
    
    /**
     * create react element
     */
    operator fun invoke(
        props: dynamic,
        blockProps: dynamic,
        size: Size?,
        previewWidth: Int?,
        settings: Json,
        attributes: dynamic): react.ReactElement<*> {

        val attrs: dynamic = props.attributes 
        val pagingController = react.useRef<HTMLElement>()
        react.useEffect {
            val paging = pagingController.current?.let {
                bindPaging(it, size, previewWidth, settings, attributes)
            }
            cleanup {
                pagingController.current?.let {
                    val controller = it
                    paging?.let {
                        unbindPaging(it , controller)
                    }
                }
            }
        }
        val styleContainingImageAndController = object {
            @JsName("position")
            val position = "relative"
        }.unsafeCast<react.CSSProperties>()
        
        size?.let {
            it.createStyle(previewWidth?.let{ it.toDouble() }).forEach {
                val styleDyn: dynamic = styleContainingImageAndController
                styleDyn[it.first] = it.second

            }
            styleContainingImageAndController.marginLeft = 
                "auto".unsafeCast<csstype.MarginLeft>()

            styleContainingImageAndController.marginRight = 
                "auto".unsafeCast<csstype.MarginRight>()

        }

          
        val rootStyle: csstype.Properties = 
            (object {}).unsafeCast<csstype.Properties>()
        
        
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
        previewWidth: Int?,
        settings: Json,
        attributes: dynamic): Paging {

        val pagingListener: (String, Paging)->Unit = { 
            eventType, paging ->
            handlePagingEvent(eventType, paging)
        }
        val contentsLoader: (HTMLElement)->Array<HTMLElement> = {
            ImageContentsMgr.createContents(imageUrls, it, attributes)
        }

        slidePanel.bind(controllerRootElement)


        val result = slidePanel.bindPaging(
            pageIndex, settings, contentsLoader)


        result.addEventListener(null, pagingListener)

        this.pagingListener = pagingListener
        return result
    }

    /**
     * detach this object from pager
     */
    fun unbindPaging(
        paging: Paging,
        controllerRootElement: HTMLElement) {

        pageIndex = paging.pageIndex
        pagingListener?.let {
            paging.removeEventListener(null, it)
            pagingListener = null
        }
        slidePanel.unbindPaging()
        slidePanel.unbind()    
    }

    

    /**
     * get paging size
     */
    fun getPagingSize(size: net.oc_soft.geom.Size?): net.oc_soft.geom.Size? {
        return if (size != null) {
            size
        } else {
            imageUrls.findBetterMatchSize()
        }
    }


    /**
     * handle paging event
     */
    fun handlePagingEvent(
        evenType: String,
        paging: Paging) {
        
    }
}



// vi: se ts=4 sw=4 et:
