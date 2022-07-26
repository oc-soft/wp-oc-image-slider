/*
 * Copyright 2022 oc-soft
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
    val save: Save = Save(),
    /**
     * example object
     */
    val example: Example = Example()) {


    /**
     * attach this object into html elements
     */
    fun bind() {

        val app = this
        // register block editor 
        
        wordpress.blocks.registerBlockType(
            net.oc_soft.block.meta,
            object {
                @JsName("icon")
                val icon = Icons.blockEditor

                @JsName("example")
                val example = app.example()

                @JsName("edit")
                val edit: (Any)->react.ReactElement<*> = { app.edit(it) }

                @JsName("save")
                val save: (Any)->react.ReactElement<*> = { app.save(it) }
            })
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
