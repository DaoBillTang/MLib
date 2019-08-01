package com.dtb.utils.base.contract

import android.app.ProgressDialog
import android.content.Context


/**
 * @author: dtb
 * @email: wendaoyuqin1@gmail.com
 * @createTime: 19-8-1下午7:03
 */
class ProgressViewImpl(val context: Context?) : ProgressView {

    private var proDialog: ProgressDialog? = null

    /**
     * 显示[.proDialog],附带文字
     */
    override fun showProgressDialog(message: String?) {
        showProgressDialog(message, true)
    }


    override fun showProgressDialog(message: String?, candel: Boolean) {
        val msg = message ?: "正在处理中请稍后……"
        if (proDialog == null) {
            proDialog = ProgressDialog(context)
        }
        proDialog!!.setMessage(msg)
        proDialog!!.setCancelable(candel)
        proDialog!!.show()
    }


    /**
     * 隐藏 progress dialog
     */
    override fun proDialogDismiss() {
        if (proDialog != null) proDialog!!.dismiss()
        proDialog = null
    }

}