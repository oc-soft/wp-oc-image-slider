package net.oc_soft

import kotlinx.browser.window
import kotlinx.browser.document

import org.w3c.dom.HTMLElement
import org.w3c.dom.get



/**
 * appliation
 */
class App {

    /**
     * attach this object into html elements
     */
    fun bind() {
    }

    /**
     * detach this object from html elements
     */
    fun unbind() {
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
