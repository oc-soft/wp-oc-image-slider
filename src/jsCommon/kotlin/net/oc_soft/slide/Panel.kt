package net.oc_soft.slide

import kotlin.js.Json

import kotlinx.browser.document

import org.w3c.dom.HTMLElement
import org.w3c.dom.svg.SVGElement
import org.w3c.dom.svg.SVGPathElement

import org.w3c.dom.events.Event

import net.oc_soft.geom.Size
import net.oc_soft.Paging


/**
 * slide panel
 */
class Panel {

    
    /**
     * pagination command
     */
    enum class PaginatingCommand(
        /**
         * icon name
         */
        val iconName: String,
        /**
         * class name
         */
        val className: String) {
        /**
         * forward
         */
        FORWARD("oc-soft/arrow-h-1-24",
            "forward"),
        /**
         * backword
         */
        BACKWARD("oc-soft/arrow-h-0-24",
            "backward")
        ;
        
    }

    /**
     * root container
     */
    var rootContainer: HTMLElement? = null

    /**
     * paging container
     */
    val pagingContainer: HTMLElement?
        get() {
            return rootContainer?.let {
                it.querySelector(".paging-container") as HTMLElement?
            }
        }
    /**
     * paging controller
     */
    val pagingController: HTMLElement?
        get() {
            return rootContainer?.let {
                it.querySelector(".paging-controller") as HTMLElement?
            }
        }

    /**
     * ui element to proceed forward page
     */
    val pagingForwardUI: HTMLElement?
        get() {
            return pagingController?.let {
                it.querySelector(
                    ".${PaginatingCommand.FORWARD.className}.command") 
                    as HTMLElement?
            }
        }

    /**
     * ui element to proceed forward page
     */
    val pagingBackwardUI: HTMLElement?
        get() {
            return pagingController?.let {
                it.querySelector(
                    ".${PaginatingCommand.BACKWARD.className}.command") 
                    as HTMLElement?
            }
        }

    /**
     * paging command click handler
     */
    var pagingCommandClickHdlr: ((Event)->Unit)? = null


    /**
     * attach this object into html elements
     */
    fun bind(
        rootContainer: HTMLElement) {

        this.rootContainer = rootContainer
        createImageAndControllerContainerElement()
        setupController()       
    }


    /**
     * detach this object from html elements
     */
    fun unbind() {
        teardownController()
        pagingContainer?.let {
            it.remove()
        }
        pagingController?.let {
            it.remove()
        }
    }

    /**
     * create image and controller 
     */
    fun createImageAndControllerContainerElement() {
        
        this.rootContainer?.let {
            val rootContainer = it

            arrayOf("paging-container",
                "paging-controller").forEach {

                val elem = document.createElement("div") as HTMLElement
                elem.style.width = "100%"
                elem.style.height = "100%"
                elem.style.position = "absolute"
                elem.classList.add(it)
                rootContainer.append(elem)
            }

        }
    }

    /**
     * attach this object into pager
     */
    fun bindPaging(
        pageIndex: Int,
        settings: Json,
        contentsLoader: ()->Array<HTMLElement>): Paging {
        return bindPaging(
            pagingContainer!!, pageIndex, settings,
            contentsLoader)
    }


    /**
     * attach this object into pager
     */
    fun bindPaging(
        pagingContainer: HTMLElement,
        pageIndex: Int,
        settings: Json,
        contentsLoader: ()->Array<HTMLElement>): Paging {
        val paging = Paging("")
        paging.contentsLoader = contentsLoader
        paging.bind(pagingContainer)
        paging.updateSetting(settings)
        paging.setupPagingContainer()
        paging.preparePlay()
        paging.pageIndex = pageIndex
        return paging
    }

    /**
     * detach this object from pager
     */
    fun unbindPaging(
        paging: Paging) {
        paging.unbind()
    }


    /**
     * handle paging event
     */
    fun handlePagingEventCommand(event: Event) {
        if (event.currentTarget == pagingForwardUI) {
            println("foward clicked")
        } else if (event.currentTarget == pagingBackwardUI) {
            println("backword clicked")
        }
    }

    /**
     * initialize controller
     */
    fun setupController() {
        pagingController?.let {
            val controller = it
            controller.append(
                createPaginatingButton(PaginatingCommand.BACKWARD))
            controller.append(
                document.createElement("div"))
            controller.append(
                createPaginatingButton(PaginatingCommand.FORWARD))
        }

        val clickHdlr: (Event)->Unit = { handlePagingEventCommand(it) }
        pagingForwardUI?.let {
            it.addEventListener("click", clickHdlr)
        }
        pagingBackwardUI?.let {
            it.addEventListener("click", clickHdlr)
        }
        pagingCommandClickHdlr = clickHdlr
    }

    /**
     * tear controll down
     */
    fun teardownController() {

        pagingCommandClickHdlr?.let {
            val hdlr = it
            pagingForwardUI?.let {
                it.removeEventListener("click", hdlr)
            }
            pagingBackwardUI?.let {
                it.removeEventListener("click", hdlr)
            }
            pagingCommandClickHdlr = null
        }

        pagingController?.let {
            while (it.childElementCount > 0) {
                it.lastElementChild?.let {
                    it.remove()
                }
            }
        } 
    }
    

    /**
     * create paginating button
     */
    fun createPaginatingButton(
        pagination: PaginatingCommand): HTMLElement {
        
        val result = document.createElement("div") as HTMLElement
        result.classList.add("command")
        result.classList.add(pagination.className) 

        createIconElement(pagination.iconName,
            pagination.className)?.let {
            result.append(it)
        }
        return result
    }

    /**
     * svg icon element
     */
    fun createIconElement(
        pathName: String,
        className: String): SVGElement? {

        val d = net.oc_soft.svg.path.d
        val pathDyn = d[pathName]
        val result = if (pathDyn is String) {
            val pathData = pathDyn
            val res0 = net.oc_soft.svg.Svg.createElement("svg")  
            val path = net.oc_soft.svg.Svg.createElement(
                "path") as SVGPathElement
            path.setAttribute("d", pathData)

            res0.append(path)
            res0.setAttribute("viewBox", "0 0 24 24")
            res0.classList.add(className)
            res0
        } else null
        return result
    }
    
}

// vi: se ts=4 sw=4 et:
