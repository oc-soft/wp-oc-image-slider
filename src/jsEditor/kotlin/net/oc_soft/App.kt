package net.oc_soft

import kotlinx.browser.window
import kotlinx.browser.document

import org.w3c.dom.HTMLElement
import org.w3c.dom.get



/**
 * appliation
 */
class App( 
    /**
     * edit block
     */
    val edit: Edit = Edit(),

    /**
     * save block
     */
    val save: Save = Save()) {


    /**
     * attach this object into html elements
     */
    fun bind() {

        val app = this
        // register block editor 
        
        wordpress.blocks.registerBlockType(
            net.oc_soft.block.name,
            net.oc_soft.block.createMeta(
                object {
                    @JsName("icon")
                    val icon = Icons.blockEditor

                    @JsName("edit")
                    val edit: (Any)->react.ReactElement<*> = { app.edit(it) }

                    @JsName("save")
                    val save: (Any)->react.ReactElement<*> = { app.save(it) }
                }))
    }

    /**
     * detach this object from html elements
     */
    fun unbind() {
        wordpress.blocks.unregisterBlockType(
            net.oc_soft.block.name)
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
