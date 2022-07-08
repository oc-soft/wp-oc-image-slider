package net.oc_soft

import kotlin.js.Json

import kotlinx.js.Object

import org.w3c.dom.url.URL

import react.Fragment
import react.create

import react.dom.html.ReactHTML.div

import wordpress.blockEditor.BlockIcon

import net.oc_soft.slide.settings.Size
import net.oc_soft.components.EditWidth
import net.oc_soft.components.EditHeight
import net.oc_soft.components.PagingControl

import net.oc_soft.slide.settings.Width
import net.oc_soft.slide.settings.Height
import net.oc_soft.slide.settings.SizeOption

import net.oc_soft.px.pxToInt

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
 
        return if (!images.isEmpty()) {
            createPreviewUi(props, blockProps, images)
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
                                "oc-slide")
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
                                "oc-slide")
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
                                "oc-slide")
                        }

                    ) 
                    __experimentalHasMultipleOrigins = true
                    __experimentalIsRenderedInSidebar = true

                    enableAlpha = true
                    title = wordpress.i18n.gettext(
                        "Color", "oc-slide")
                }
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
                getPreviewDeviceWidth()?.let{ it.toInt() },
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
        
        val result: dynamic = Object.assign(object {}, setting)

        var settingValue = attr["paging-control-loop"] as Any?
        val controlSetting: dynamic = object { }
        controlSetting.loop = when (settingValue) {
            is String -> settingValue.toInt() != 0
            is Number -> settingValue.toInt() != 0
            else -> false 
        } 
        settingValue = attr["paging-control-auto"] 

        controlSetting.auto = when (settingValue) {
            is String -> settingValue.toInt() != 0
            is Number -> settingValue.toInt() != 0
            else -> false 
        }

        controlSetting["color-1"] = attr["paging-control-color-1"]
        controlSetting["color-2"] = attr["paging-control-color-2"]
        
        settingValue = attr["paging-control-auto-direction"]
        controlSetting["auto-direction"] = when (settingValue) {
            is String -> settingValue.toInt()
            is Number -> settingValue
            else -> 0
        }

        result.control = controlSetting



        return result as Json
    }
    /**
     * pager size
     */
    fun getPagingSize(
        attr: dynamic): Size? {
        val optionKeys =  arrayOf("page-width-option", "page-height-option")
        val optionSettings = Array<Number?>(optionKeys.size) {
            val value = attr[optionKeys[it]] as Any?
            if (value is Number) {
                value as Number
            } else null
        }

        val result = if (optionSettings[0] != null 
            && optionSettings[1] != null) {
            var sizeKeys = if (optionSettings[0] == 0) {
                arrayOf("page-width-relative")
            } else {
                arrayOf(
                    "page-width-desktop",
                    "page-width-tablet",
                    "page-width-mobile")
                
            }
            var sizeSettings = Array<Number?>(sizeKeys.size) {
                val value = attr[sizeKeys[it]] as Any?
                if (value is Number) {
                    value as Number
                } else null
            }
            val width = if (sizeSettings[0] != null) {
                var lastValue: Double? = null
                Width(SizeOption.values()[optionSettings[0]!!.toInt()],
                    DoubleArray(sizeSettings.size) {
                        val current = sizeSettings[it] 
                        current?.let {
                            lastValue = it.toDouble()
                        }
                        lastValue!!
                    })
            } else null

            sizeKeys = if (optionSettings[1] == 0) {
                arrayOf("page-height-aspect-1",
                    "page-height-aspect-2")
            } else {
                arrayOf(
                    "page-height-desktop",
                    "page-height-tablet",
                    "page-height-mobile")
                
            }
            sizeSettings = Array<Number?>(sizeKeys.size) {
                val value = attr[sizeKeys[it]] as Any?
                if (value is Number) {
                    value as Number
                } else null
            }
            val height = if (sizeSettings[0] != null) {
                var lastValue: Double? = null
                Height(SizeOption.values()[optionSettings[1]!!.toInt()],
                    DoubleArray(sizeSettings.size) {
                        val current = sizeSettings[it] 
                        current?.let {
                            lastValue = it.toDouble()
                        }
                        lastValue!!
                    })
            } else null

            if (width != null && height != null) {
                Size(width, height)
            } else null
        } else null

        return result
    }
}

// vi: se ts=4 sw=4 et:
