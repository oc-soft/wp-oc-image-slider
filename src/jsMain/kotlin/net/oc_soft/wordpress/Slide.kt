package net.oc_soft.wordpress

import kotlin.collections.ArrayList
import kotlin.collections.MutableList

import kotlinx.browser.window
import kotlinx.browser.document
import kotlin.js.Json
import kotlin.js.JSON

import kotlinx.js.Object

import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLImageElement
import org.w3c.dom.get
import org.w3c.dom.url.URL

import net.oc_soft.slide.Panel
import net.oc_soft.dashStyleToCamel
import net.oc_soft.ImageURL
import net.oc_soft.ImageURLs
import net.oc_soft.image.ContentsMgr as ImageContentsMgr

import net.oc_soft.paging.Settings as PagingSettings

import net.oc_soft.camelToDashStyle

/**
 * slide for wordpress plugin
 */
class Slide {


    /**
     * containers to slide images
     */
    val slideContainers: Array<HTMLElement>
        get() {
            val elems0 = document.querySelectorAll("div.wp-block-oc-soft-slide")
            
            val elems = ArrayList<HTMLElement>()

            for (idx in 0 until elems0.length) {
                elems.add(elems0[idx] as HTMLElement)
            }
            return elems.toTypedArray()
        }

    /**
     * slide panels
     */
    val panels: MutableList<Panel> = ArrayList<Panel>()

    /**
     * bind  this object into html elements
     */
    fun bind() {
        attachPanels(slideContainers)
    }

    /**
     * detach this object from html elements
     */
    fun unbind() {
        detachPanels()
    }
    

    /**
     * attach slide container into slide panel
     */
    fun attachPanels(elems: Array<HTMLElement>) {
        elems.forEach {
            val panel = Panel()
            bindPanel(it, panel) 
            panels.add(panel)
        }
    }

    /**
     * detach slide container from slide panel
     */
    fun detachPanels() {
        panels.forEach {
            unbindPanel(it)
        }
        panels.clear()
    }


    /**
     * bind root panel into panel
     */
    fun bindPanel(
        rootElement: HTMLElement,
        panel: Panel) {
        val slideSettings = retrieveSettingsFromRootContainer(rootElement)
        val imageUrls = loadImagesFromRootContainer(rootElement)
        val contentsLoader: (HTMLElement)->Array<
            Pair<HTMLElement, (HTMLElement)->HTMLElement>> = {
            val attributes: dynamic = slideSettings
            ImageContentsMgr.createContents(
                imageUrls, it, attributes)
        }
        val pagingSetting = PagingSettings.createPagingSettingsFromAttributes(
            slideSettings)
        setupRootElementBoundingBox(rootElement, slideSettings)
        panel.bind(rootElement)
        panel.bindPaging(null, pagingSetting, contentsLoader)
    }

    /**
     * unbind panel
     */
    fun unbindPanel(panel: Panel) {
        panel.unbindPaging()
        panel.unbind()
    }


    /**
     * setup root element bounding box
     */
    fun setupRootElementBoundingBox(
        rootElement: HTMLElement,
        attr: dynamic) {

        val rootElementStyle: dynamic = object {
            @JsName("position")
            val position = "relative"
        }
        
        net.oc_soft.wordpress.Size.createPagingSizeFromSettings(attr)?.let {
            it.createStyle(null).forEach {
                rootElementStyle[it.first.camelToDashStyle()] = it.second
            }
            rootElementStyle.marginLeft = "auto"
            rootElementStyle.marginRight = "auto"
        }
        
        Object.keys(rootElementStyle).forEach {
            rootElement.style.setProperty(it, rootElementStyle[it])
        }
    }




    /**
     * get setting from root container
     */
    fun retrieveSettingsFromRootContainer(
        rootElement: HTMLElement): Json {

        val result = kotlin.js.json() 
        Object.keys(net.oc_soft.block.meta.attributes).forEach {
            val key = it
            rootElement.dataset[key.dashStyleToCamel()]?.let {
                result[key] = it
            }
        }
        return result
    }


    /**
     * load images from root container
     */
    fun loadImagesFromRootContainer(
        rootElement: HTMLElement): ImageURLs {

        val result = ImageURLs()
        val elems0 = rootElement.querySelectorAll(
            "figure.wp-block-image > img")

        for (idx in 0 until elems0.length) {
            result.add(
                retrieveImageURL(elems0[idx] as HTMLImageElement))
        } 
        
        return result
    }

    /**
     * retrieve
     */
    fun retrieveImageURL(element: HTMLImageElement): ImageURL {
        return ImageURL(URL(element.src), element.width, element.height)
    }


    /**
     * create paging setting
     */
    fun updatePagingSetting(
        pagingSetting: Json,
        slideSetting: Any?) {

        pagingSetting["control"] = 
            PagingSettings.createControlSettingFromAttributes(slideSetting)
          
    }


}


// vi: se ts=4 sw=4 et:
