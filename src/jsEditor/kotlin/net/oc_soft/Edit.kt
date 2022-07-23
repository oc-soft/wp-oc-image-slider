package net.oc_soft

import kotlin.js.Json

import kotlinx.js.Object

import org.w3c.dom.HTMLElement
import org.w3c.dom.url.URL

import react.Fragment
import react.create

import react.dom.html.ReactHTML.div

import wordpress.blockEditor.BlockIcon

import net.oc_soft.slide.settings.Size
import net.oc_soft.components.EditWidth
import net.oc_soft.components.EditHeight
import net.oc_soft.components.PagingControl
import net.oc_soft.components.ImageEffect

import net.oc_soft.slide.settings.Width
import net.oc_soft.slide.settings.Height
import net.oc_soft.slide.settings.SizeOption

import net.oc_soft.px.pxToInt

import net.oc_soft.paging.Settings as PagingSettings


/**
 * edit block
 */
class Edit {


    /**
     * user interface to show images 
     */
    val imagesUi = Images()


    /**
     * editor for pager width
     */
    val editWidth = EditWidth()

    /**
     * editor for pager height
     */
    val editHeight = EditHeight()


    /**
     * paging control
     */
    val pagingControl = PagingControl()


    /**
     * image effect control
     */
    val imageEffect = ImageEffect()

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
        val previewWidth = getPreviewDeviceWidth()?.let{ it.toInt() }
        val pagingController = react.useRef<HTMLElement>()
        react.useEffect {
            val paging = pagingController.current?.let {
                imagesUi.bindPaging(
                    it, 
                    getPagingSize(attr), previewWidth, 
                    getPagingSetting(attr), attr)
            }
            cleanup {
                pagingController.current?.let {
                    val controller = it
                    paging?.let {
                        imagesUi.unbindPaging(it , controller)
                    }
                }
            }
        }
        return if (!images.isEmpty()) {
            createPreviewUi(props, blockProps, previewWidth, 
                pagingController, images)
        } else {
            createUiToAddNewImages(props, blockProps)
        }
    }

    /**
     * create user interface
     */
    fun createPreviewUi(
        props: dynamic,
        blockProps: dynamic,
        previewWidth: Int?,
        pagingController: react.MutableRefObject<HTMLElement>, 
        images: Array<Image>): react.ReactElement<*> {
        val clientId = props.clientId as String
        val attr = props.attributes
        val setAttr = props.setAttributes   

        imagesUi.imageUrls.clear()
        images.forEach {
            imagesUi.imageUrls.add(
                ImageURL(it.url, it.width.toInt(), it.height.toInt()))
        }
        val onSelectImages = createSelectedImagesHandler(clientId)


        return react.Fragment.create { 
            wordpress.blockEditor.InspectorControls {

                + editWidth(attr, setAttr)

                + editHeight(attr, setAttr)

                + pagingControl(attr, setAttr) 

                wordpress.blockEditor.PanelColorSettings {

                    colorSettings = arrayOf(
                        object {
                            
                            @JsName("value")
                            val value = attr["page-color"]

                            @JsName("onChange")
                            val onChange: (String?)->Unit = {
                                val newColor: dynamic = object {}
                                newColor["page-color"] = it
                                setAttr(newColor)
                            }
                            @JsName("label") 
                            val label = wordpress.i18n.gettext(
                                "Page Color",
                                "oc-image-slider")
                        },
                        object {
                            @JsName("value")
                            val value = attr["paging-control-color-1"]

                            @JsName("onChange")
                            val onChange: (String?)->Unit = {
                                val newColor: dynamic = object {}
                                newColor["paging-control-color-1"] = it
                                setAttr(newColor)
                            }
                            @JsName("label") 
                            val label = wordpress.i18n.gettext(
                                "Control Color 1",
                                "oc-image-slider")
                        },
                        object {
                            @JsName("value")
                            val value = attr["paging-control-color-2"]

                            @JsName("onChange")
                            val onChange: (String?)->Unit = {
                                val newColor: dynamic = object {}
                                newColor["paging-control-color-2"] = it
                                setAttr(newColor)
                            }
                            @JsName("label") 
                            val label = wordpress.i18n.gettext(
                                "Control Color 2",
                                "oc-image-slider")
                        }

                    ) 
                    __experimentalHasMultipleOrigins = true
                    __experimentalIsRenderedInSidebar = true

                    enableAlpha = true
                    title = wordpress.i18n.gettext(
                        "Color", "oc-image-slider")
                }

                + imageEffect(attr, setAttr)
            }

            wordpress.blockEditor.BlockControls {
                group = "other"
                wordpress.blockEditor.MediaReplaceFlow {
                    multiple = true
                    accept = "image/*" 
                    allowedTypes = arrayOf("image")
                    addToGallery = true
                    onSelect = onSelectImages
                    
                    mediaIds = Array<String>(images.size) {
                        images[it].id
                    }
                }
            }
            + imagesUi(props, blockProps, 
                getPagingSize(attr), 
                previewWidth,
                pagingController,
                getPagingSetting(attr), attr)
        }
    }



    /**
     * get preview device width
     */
    fun getPreviewDeviceWidth(): Number? {
        val style = wordpress.blockEditor.useResizeCanvas(
            getCurrentPreviewDeviceType()) as Any?
        return style?.let { 
            val styleDyn: dynamic = style
            val widthObj = styleDyn.width
            when (widthObj) {
            is String -> widthObj.pxToInt() 
            is Number -> widthObj
            else -> null
            } 
        }
    }

    /**
     * device type
     */
    fun getCurrentPreviewDeviceType(): String {
        return wordpress.data.useSelect({
            it(wordpress.editPost.store).__experimentalGetPreviewDeviceType()  
        })
    }

    /**
     * create user interface to add new images
     */
    fun createUiToAddNewImages(
        props: dynamic,
        blockProps: dynamic): react.ReactElement<*> {
        val clientId = props.clientId as String

        val mphIcon = BlockIcon.create {
            icon = Icons.blockEditor
        }

        val instructions = wordpress.i18n.gettext(
            "Drop images or select files from your library")
        
        val onSelectImages = createSelectedImagesHandler(clientId)
 
        return Fragment.create {
            div {
                Object.assign(this, blockProps)
                wordpress.blockEditor.MediaPlaceholder {
                    handleUpload = false
                    icon = mphIcon
                    labels = object {
                        @JsName("title")
                        val title = wordpress.i18n.gettext(
                            "Images", "oc-image-slider")
                        @JsName("instrructions")
                        val instructions = instructions
                    }

                    multiple = true
                    accept = "image/*"
                    allowedTypes = arrayOf("image")
                    addToGallery = true
                    onSelect = onSelectImages
                } 
            }
        }
    }

    /**
     * create handler for being selected images
     */
    fun createSelectedImagesHandler(
        clientId: String): (dynamic)->Unit {
        val editorStoreDispatch = wordpress.data.useDispatch(
            wordpress.blockEditor.store) 

        val replaceInnerBlocks:(
            String, Array<dynamic>, Boolean, Int)->dynamic = 
            editorStoreDispatch.replaceInnerBlocks
        val result:(dynamic)->Unit = {
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
        return result
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
    fun getPagingSetting(attr: dynamic): Json {
        val setting = net.oc_soft.paging.setting
        
        return PagingSettings.createPagingSettingsFromAttributes(attr) 
    }
    /**
     * pager size
     */
    fun getPagingSize(
        attr: dynamic): Size? {
        return net.oc_soft.wordpress.Size.createPagingSizeFromSettings(
            attr)
    }

}

// vi: se ts=4 sw=4 et:
