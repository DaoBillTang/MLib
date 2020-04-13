package com.dtb.core.base.contract

/**
 * @author: dtb
 * @email: wendaoyuqin1@gmail.com
 * @createTime: 19-3-14下午3:05
 */
interface ProgressView {

    fun showProgressDialog(message: String?)
    fun showProgressDialog(message: String?, cancel: Boolean)
    fun proDialogDismiss()
}
