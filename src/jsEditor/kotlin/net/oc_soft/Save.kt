package net.oc_soft

import kotlinx.browser.window
import kotlin.js.JSON
import kotlinx.js.Object

import react.Fragment
import react.create
import react.dom.html.ReactHTML.div

/**
 * save block
 */
class Save {

    /**
     * save block data
     */
    @Suppress("UNUSED_PARAMETER")
    operator fun invoke(settings: Any): react.ReactElement<*>  {
        val blockProps = wordpress.blockEditor.useBlockProps.save(object {
        })
        val innerBlockProps = wordpress.blockEditor.useInnerBlocksProps.save(
            blockProps 
        )
        val innerBlockProps0 = innerBlockProps as react.PropsWithChildren

        val children0 = react.Children.toArray(innerBlockProps0.children)


        val dynSettings: dynamic = settings

        val attrObj = dynSettings.attributes as Any?

        val objAttr: dynamic = object {}
        attrObj?.let {
            val dynAttr: dynamic = it
            Object.keys(it).forEach {
                val attrValue0 = dynAttr[it] as Any?
            
                val attrValue = when (attrValue0) {
                    is String ->  attrValue0
                    is Number ->  attrValue0.toString()
                    is Boolean -> attrValue0.toString()
                    else -> null
                }
                val keyName = it
                attrValue?.let {
                    objAttr["data-${keyName}"] = attrValue
                }
            }
        }
         
        
        
        return Fragment.create {
            div {
                Object.assign(this, innerBlockProps)
                Object.assign(this, objAttr)
                
                children0.forEach { +it }

            }
        } 
    }

}

// vi: se ts=4 sw=4 et:
