package net.oc_soft

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

        return Fragment.create {
            div {
                Object.assign(this, innerBlockProps)
                
                children0.forEach { +it }
            }
        } 
    }

}

// vi: se ts=4 sw=4 et:
