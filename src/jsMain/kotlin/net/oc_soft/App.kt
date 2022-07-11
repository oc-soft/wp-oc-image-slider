package net.oc_soft

import kotlinx.browser.window
import kotlinx.browser.document

import kotlin.js.jsTypeOf
import kotlin.js.Promise

import kotlin.collections.ArrayList
import kotlin.collections.MutableList

import org.w3c.dom.HTMLElement
import org.w3c.dom.get
import org.w3c.dom.url.URL

import net.oc_soft.wordpress.Slide as WpSlide

/**
 * appliation
 */
class App {


    /**
     * slide containers
     */
    val slideContainers: Array<HTMLElement>
        get() {
            val elems = document.querySelectorAll(".oc-slide")
            return Array<HTMLElement>(elems.length) {
                elems[it] as HTMLElement
            }
        }

    /**
     * site url
     */
    val siteUrl: URL?
        get() {
            val ocNsp = window["oc"]
            return if (jsTypeOf(ocNsp) == "object") {
                if (jsTypeOf(ocNsp.slide) == "object") {
                    if (jsTypeOf(ocNsp.slide.ajax) == "object") {
                        if (jsTypeOf(ocNsp.slide.ajax.url) == "string") {
                            URL(ocNsp.slide.ajax.url as String)
                        } else null
                    } else null
                } else null
            } else null
        }


    /**
     * autopagers
     */
    val autoPagings: MutableList<Paging> = ArrayList<Paging>()


    /**
     * slide object for wordpress
     */
    val wpSlide = WpSlide()

    /**
     * attach this object into html elements
     */
    fun bind() {

        slideContainers.forEach {
            val autoPaging = Paging("autopaging-config")

            autoPaging.bind(it)
            autoPagings.add(autoPaging)
        }

        loadPagings().then {
            startPagings()
        }

        wpSlide.bind()
    }

    /**
     * detach this object from html elements
     */
    fun unbind() {
        wpSlide.unbind()

        autoPagings.forEach {
            it.unbind()
        }
        autoPagings.clear()
    }


    /**
     * load autopaging settings
     */
    fun loadPagings():Promise<Unit> {
        return Promise.all(Array<Promise<Unit>>(autoPagings.size) {
            val autoPaging = autoPagings[it]
            autoPaging.loadSetting(siteUrl).then {
                autoPaging.setupPagingContainer()
                autoPaging.preparePlay() 
                Unit
            }
        }).then { Unit }
    }

    /**
     * start autopaging
     */
    fun startPagings() {
        autoPagings.forEach {
            it.autoPlay()
        }
    }

    /**
     * run application
     */
    fun run() {
        window.addEventListener("load", {
                bind()
            },
            object {
                @JsName("once")
                val once = true
            }) 
        window.addEventListener("unload", {
                unbind()
            },
            object {
                @JsName("once")
                val once = true
            })
            
    }
}

// vi: set ts=4 sw=4 et:
