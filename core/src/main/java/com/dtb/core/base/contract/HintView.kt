package com.dtb.core.base.contract


/**
 * @author: dtb
 * @email: wendaoyuqin1@gmail.com
 * @createTime: 19-3-14下午3:09
 */
interface HintView {
    fun showErr(msgList: Array<String>?)
    fun showErr(msg: String?)
    fun showErr(msgList: ArrayList<String>?)
}