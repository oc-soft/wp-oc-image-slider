package net.oc_soft

import kotlin.js.Json

import kotlinx.js.Object

import org.w3c.dom.url.URL

import react.Fragment
import react.create

import react.dom.html.ReactHTML.div

import wordpress.blockEditor.BlockIcon

import net.oc_soft.geom.Size

/**
 * edit block
 */
class Edit {


    /**
     * user interface to show images 
     */
    val imagesUi = Images()

    /**
     * edit block
     */
    @Suppress("UNUSED_PARAMETER")
    operator fun invoke(props: dynamic): react.ReactElement<*> {
        return createUi(props)
    } 

    /**
     * create user interface
     */
    fun createUi(props: dynamic): react.ReactElement<*> {

        val setAttr = props.setAttributes
        val attr = props.attributes
        val clientId = props.clientId as String
        val innerBlockImages = wordpress.data.useSelect({
            val container = it(
                wordpress.blockEditor.store).getBlock(clientId) as Any?
            container?.let {
                val blockContainer: dynamic = it
                blockContainer.innerBlocks
            }
        }, arrayOf(clientId)) as Array<*>?


        val images = innerBlockImages?.let {
            val blockImages = it
            Array<Image>(blockImages.size) {
                val imgSrc: dynamic = blockImages[it]

                Image(URL(imgSrc.attributes.url as String),
                    imgSrc.attributes.id.toString(),
                    imgSrc.attributes.width.toString(),
                    imgSrc.attributes.height.toString())
            }             
        }?: emptyArray<Image>()
        val blockProps: dynamic = wordpress.blockEditor.useBlockProps() 
 
        return return if (!images.isEmpty()) {
            
            imagesUi.imageUrls.clear()
            images.forEach {
                imagesUi.imageUrls.add(
                    ImageURL(it.url,
                        it.width.toInt(),
                        it.height.toInt()))
            }
            imagesUi(
                props,
                blockProps, 
                getPagingSize(), 
                getPagingSetting(),
                attr)
        } else {
            createUiToAddNewImages(props, blockProps)
        }
    }


    /**
     * create user interface to add new images
     */
    fun createUiToAddNewImages(
        props: dynamic,
        blockProps: dynamic): react.ReactElement<*> {
        val clientId = props.clientId as String

        val editorStoreDispatch = wordpress.data.useDispatch(
            wordpress.blockEditor.store) 

        val replaceInnerBlocks:(
            String, Array<dynamic>, Boolean, Int)->dynamic = 
            editorStoreDispatch.replaceInnerBlocks


        val mphIcon = BlockIcon.create {
            icon = Icons.blockEditor
        }

        val instructions = wordpress.i18n.gettext(
            "Drop images or select files from your library")

        
        val onSelectImages: (dynamic)->Unit = {
            val images = it as Array<dynamic>

            val innerBlocks = ArrayList<Any>()
            images.forEach {
                readImageBlockParameter(it)?.let {
                    innerBlocks.add(wordpress.blocks.createBlock(
                        "core/image", it))
                }
            }
            replaceInnerBlocks(clientId, innerBlocks.toTypedArray(), false, 0)
        }

        return Fragment.create {
            div {
                Object.assign(this, blockProps)
                wordpress.blockEditor.MediaPlaceholder {
                    handleUpload = false
                    icon = mphIcon
                    labels = object {
                        @JsName("title")
                        val title = wordpress.i18n.gettext(
                            "Images", "oc-slide")
                        @JsName("instrructions")
                        val instructions = instructions
                    }
                    multiple = true
                    accept = "image/*"
                    allowedTypes = arrayOf("image")
                    addToGallery = false
                    onSelect = onSelectImages
                } 
            }
        }
    }

    /**
     * read size from image
     */
    fun readFullImage(image: dynamic): Any? {
        val sizes = image.sizes as Any?
        return sizes?.let {
            val sizesDyn: dynamic = it
            val full: Any? = sizesDyn.full
            full
        }
    }

    /**
     * read image block parameter
     */
    fun readImageBlockParameter(
        image: dynamic): Any? {
        return readFullImage(image)?.let {
            val fullImage: dynamic = it
            object {
                @JsName("id") 
                val id = image.id
                @JsName("url")
                val url = fullImage.url
                @JsName("caption")
                val caption = image.caption
                @JsName("alt")
                val alt = image.alt
                @JsName("width")
                val width = fullImage.width
                @JsName("height")
                val height = fullImage.height
            }
        }
    }


    /**
     * pager setting
     */
    fun getPagingSetting(): Json{
        return net.oc_soft.paging.setting
    }
    /**
     * pager size
     */
    fun getPagingSize(): Size? {
        return null
    }
}

// vi: se ts=4 sw=4 et:
