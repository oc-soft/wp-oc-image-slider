package net.oc_soft.slide

import kotlin.js.Json
import kotlin.js.Promise

import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.Element
import org.w3c.dom.HTMLElement
import org.w3c.dom.svg.SVGElement
import org.w3c.dom.svg.SVGPathElement

import org.w3c.dom.events.Event

import net.oc_soft.slide.settings.Size
import net.oc_soft.Paging
import net.oc_soft.PagingDirection


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
                it.querySelector(".paging-container")?.let {
                    // chrome throws class cast exception sometimes
                    // it as HTMLElement?
                    it.unsafeCast<HTMLElement>()
                }
            }
        }
    /**
     * paging controller
     */
    val pagingController: HTMLElement?
        get() {
            return rootContainer?.let {
                it.querySelector(".paging-controller")?.let {
                    // chrome throws class cast exception sometimes
                    // it as HTMLElement?
                    it.unsafeCast<HTMLElement>()
                }
            }
        }

    /**
     * ui element to proceed forward page
     */
    val pagingForwardUI: HTMLElement?
        get() {
            return pagingController?.let {
                it.querySelector(
                    ".${PaginatingCommand.FORWARD.className}.command")?.let {
                    // chrome throws class cast exception sometimes
                    // it as HTMLElement?
                    it.unsafeCast<HTMLElement?>()
                }
            }
        }

    
    /**
     * forward icon svg element
     */
    val pagingForwardIcon: SVGElement?
        get() {
            return pagingForwardUI?.let {
                it.querySelector("svg")?.let {
                    it as SVGElement
                }
            }
        }

    
    /**
     * ui element to proceed forward page
     */
    val pagingBackwardUI: HTMLElement?
        get() {
            return pagingController?.let {
                it.querySelector(
                    ".${PaginatingCommand.BACKWARD.className}.command")?.let {
                    // chrome throws class cast exception sometimes
                    // it as HTMLElement?
                    it.unsafeCast<HTMLElement?>()
                }
            }
        }

    /**
     * backward icon svg element
     */
    val pagingBackwardIcon: SVGElement?
        get() {
            return pagingBackwardUI?.let {
                it.querySelector("svg")?.let {
                    it as SVGElement
                }
            }
        }

    /**
     * auto play
     */
    var autoPlay: Boolean = false
        set(value) {
            if (field != value) {
                field = value
            }
        }

    /**
     * forward controller visibility
     */
    var visilbleForwardController: Boolean = false
        set(value) {
            pagingForwardUI?.let { 
                setVisibleController(it, if (value) { 270 } else null) 
                field = value
            }
        }

    /**
     * backward controller visibility
     */
    var visilbleBackwardController: Boolean = false
        set(value) {
            pagingBackwardUI?.let { 
                setVisibleController(it, if (value) { 90 } else null) 
                field = value
            }
        }


    /**
     * colors setting
     */
    val colors = Array<String?>(2) { null }

    /**
     * paging command click handler
     */
    var pagingCommandClickHdlr: ((Event)->Unit)? = null

    /**
     * paging command enter leave handler
     */
    var pagingCommandEnterLeaveHdlr: ((Event)->Unit)? = null

    /**
     * promise object. It is exists only while paging object is moving 
     * between pages.
     */
    var pagingPromise: Promise<Unit>? = null

    /**
     * pagination object
     */
    var paging: Paging? = null

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
        pageIndex: Int?,
        settings: Json,
        contentsLoader: (HTMLElement)->Array<
            Pair<HTMLElement, (HTMLElement)->HTMLElement>>): Paging {
        return bindPaging(
            pagingContainer!!, pageIndex, settings,
            contentsLoader)
    }


    /**
     * attach this object into pager
     */
    fun bindPaging(
        pagingContainer: HTMLElement,
        pageIndex: Int?,
        settings: Json,
        contentsLoader: 
            (HTMLElement)->Array<
                Pair<HTMLElement, (HTMLElement)->HTMLElement>>): Paging {
        val paging = Paging("")

        setupController(settings)
        setupFields(settings)
        paging.contentsLoader = contentsLoader
        paging.bind(pagingContainer)
        paging.updateSetting(settings)
        paging.setupPagingContainer()
        paging.preparePlay()

        if (pageIndex != null) {
            paging.pageIndex = pageIndex
        } else {
            val idx = if (paging.autoPagingDirection == 
                PagingDirection.FORWARD) {
                0
            } else {
                paging.pagingStatus?.let {
                    it.pages.size - 1
                }?: 0
            }
            paging.pageIndex = idx
        }

        this.paging = paging
        startAutoPlayIfEnabled(paging)
        return paging
    }

    /**
     * detach this object from pager
     */
    fun unbindPaging() {
        paging?.let {

            if (autoPlay) {
                it.pagingStatus?.let {
                    it.stopPaging = true
                }
            }
            it.unbind()
            paging = null
        }
    }

    /**
     * start auto play if auto play setting is enabled
     */
    fun startAutoPlayIfEnabled(paging: Paging) {
        if (autoPlay) {
            paging.autoPlay() 
        }
    }


    /**
     * handle paging event
     */
    fun handlePagingEventCommand(event: Event) {
        proceedPage(event.currentTarget == pagingForwardUI)
    }

    /**
     * handle mouseeneter and mouseleave event
     */
    fun handleEnterLeave(event: Event) {

        var angle: Int? = when(event.currentTarget) {
            pagingForwardUI -> 270 
            else -> 90
        }
        if(event.type == "mouseleave" || autoPlay) { 
            angle = null
        }
        
        setVisibleController(event.currentTarget as HTMLElement, angle) 
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

        val mql = window.matchMedia("(hover:none)")

        if (!mql.matches) {
            val enterLeaveHdlr: (Event)->Unit = { handleEnterLeave(it) }        
            arrayOf(pagingForwardUI, pagingBackwardUI).forEach {
                it?.let {
                    val elm = it
                    arrayOf("mouseenter", "mouseleave").forEach {
                        elm.addEventListener(it, enterLeaveHdlr)
                    }
                }
            }
            pagingCommandEnterLeaveHdlr = enterLeaveHdlr 
            visilbleForwardController = false 
            visilbleBackwardController = false 
        } else {
            visilbleForwardController = true
            visilbleBackwardController = true 
            
        }
    }


    /**
     * setup controller 
     */
    fun setupController(
        settings: Json) {
        
        val controlObj = settings["control"]
        if (controlObj != null) {
            val control: dynamic = controlObj


            arrayOf("color-1", "color-2").forEachIndexed {
                idx, key ->
                var colorObj = control[key]
                colors[idx] = when (colorObj) {
                    is String -> colorObj
                    else -> null
                }
            }
            val autoObj: Any? = control.auto
            autoPlay = when(autoObj) {
                is String -> autoObj.toBoolean()
                is Number -> autoObj.toInt() != 0
                else -> false
            } 

        } else {
            for (idx in colors.indices) {
                colors[idx] = null
            }
            autoPlay = false
        }
        
    }


    /**
     * synchronize controller with auto play
     */
    fun syncControllerWithAutoPlay() {
        if (autoPlay) {
            visilbleForwardController = false 
            visilbleBackwardController = false 
        }
    }
     
    

    /**
     * setup fields
     */
    fun setupFields(settings: Json) {
        autoPlay = settings["control"]?.let {
            val control: dynamic = it
            val autoObj = control["auto"] as Any?
            autoObj?.let {
                when (autoObj) {
                    is Number -> autoObj.toInt() != 0
                    is String -> autoObj.toInt() != 0
                    is Boolean -> autoObj
                    else -> false
                }
            }?: false
        }?: false
    }

    /**
     * tear controll down
     */
    fun teardownController() {

        pagingCommandEnterLeaveHdlr?.let {
            val hdlr = it
            arrayOf(pagingForwardUI, pagingBackwardUI).forEach {
                it?.let {
                    val elm = it
                    arrayOf("mouseenter", "mouseleave").forEach {
                        elm.removeEventListener(it, hdlr)
                    }
                }
            }
            pagingCommandEnterLeaveHdlr = null  
        }
        

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


    /**
     * proceed to page forward.
     */
    fun proceedPage(forward: Boolean) {
        if (pagingPromise == null) {
            paging?.let {
                pagingPromise = it.proceedPage(forward).then {
                    pagingPromise = null
                }
            }
        }
    }


    /**
     * set controller visible
     */
    fun setVisibleController(
        controller: HTMLElement,
        angle: Int?) {
        val iconElem  = controller.querySelector("svg") as SVGElement?
        if (angle != null) {
            colors[0]?.let {
                val color = it
                iconElem?.let {
                    it.style.setProperty("fill", color)
                }
            }
            
            colors[1]?.let {
                val color = it

                val bgParam = "${angle}deg, ${color} 50%, #00000000"

                val bgImg = "linear-gradient(${bgParam})"
                
                controller.style.backgroundImage = bgImg
            }
            
        } else {
            iconElem?.let {
                it.style.setProperty("fill", "transparent")
            }

            val bgParam = "#00000000, #00000000"
            val bgImg = "linear-gradient(${bgParam})"
            controller.style.backgroundImage = bgImg
        }
    }
}

// vi: se ts=4 sw=4 et:
