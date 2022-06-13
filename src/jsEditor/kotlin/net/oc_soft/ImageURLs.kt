package net.oc_soft

import kotlin.collections.LinkedHashSet
import kotlin.collections.ArrayList

import org.w3c.dom.HTMLElement
import org.w3c.dom.url.URL

import net.oc_soft.geom.Size

/**
 * images interface
 */
class ImageURLs() {

    /**
     * url source set
     */
    val sourceSet = LinkedHashSet<ImageURL>()

    /**
     * size
     */
    val size: Int get() = sourceSet.size


    /**
     * source list
     */
    val sourceList: Array<ImageURL>
        get() {
            var cache = sourceListCache
            return if (cache == null) {
                var sources = ArrayList<ImageURL>()
                sourceSet.forEach {
                    sources.add(it)
                }
                sourceListCache = sources.toTypedArray()
                sourceListCache !!
            } else {
                cache 
            }
        }
    /**
     * source list cache
     */
    private var sourceListCache: Array<ImageURL>? = null


    /**
     * url source
     */
    val sources: Array<URL> get() = sourceSet.map { it.url }.toTypedArray()


    /**
     * constructor
     */
    constructor(other: ImageURLs): this() {
        sourceSet.addAll(other.sourceSet)
    }

    /**
     * append url if not exists
     */
    fun add(imgUrl: ImageURL) {
        sourceSet.add(imgUrl)
        sourceListCache = null
    }

    /**
     *  append all
     */
    fun add(imageUrls: Array<ImageURL>) {
        imageUrls.forEach { add(it) }
    } 
    

    /**
     * remove url
     */
    fun remove(imageUrl: ImageURL) {
        val removed = sourceSet.remove(imageUrl)
        if (removed) {
            sourceListCache = null
        }
         
    }

    /**
     * remove urls
     */
    fun remove(imageUrls: Array<ImageURL>) {
        imageUrls.forEach { remove(it) }
    }


    /**
     * find better match size
     */
    fun findBetterMatchSize(): Size? {
        val sizeCountMap = HashMap<Size, Int>()
        sourceSet.forEach {
            val size = Size(it.width.toDouble(), it.height.toDouble())
            if (!(size in sizeCountMap)) {
                sizeCountMap[size] = 1
            } else {
                sizeCountMap[size] = sizeCountMap[size]!! + 1
            }
        }
        return sizeCountMap.maxWithOrNull(
            object: Comparator<Map.Entry<Size, Int>> {
            override fun compare(
                a: Map.Entry<Size, Int>, 
                b: Map.Entry<Size, Int>): Int {
                return a.value - b.value 
            }
        })?.let {
            it.key
        } 
    }

    /**
     * get image url object at index
     */
    operator fun get(index: Int): ImageURL {
        return sourceList[index] 
    }
}



// vi: se ts=4 sw=4 et:
