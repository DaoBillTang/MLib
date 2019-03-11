package com.dtb.utils.commons.img.webapi


/**
 *  glide 加载的设置
 */
object ImgOption {

    var apiImpl: WebImgApiImpl<Any>? = null

}


fun initImgOption(apiImpl: WebImgApiImpl<Any>) {
    ImgOption.apiImpl = apiImpl
}


