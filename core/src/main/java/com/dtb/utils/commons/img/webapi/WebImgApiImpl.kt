package com.dtb.utils.commons.img.webapi

/**
 * @author: dtb
 * @email: wendaoyuqin1@gmail.com
 * @createTime: 19-3-4下午5:43
 */
interface WebImgApiImpl<T> {

    fun checkUrl(url: String?, process: String = ""): String

    fun loadImgIntoView(url: String?, option: WebImgOptionImpl<T>)

}


interface WebImgOptionImpl<T> {
    var option: T
}

