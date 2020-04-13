package com.dtb.core.base.contract

import android.app.ProgressDialog
import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner


/**
 * @author: dtb
 * @email: wendaoyuqin1@gmail.com
 * @createTime: 19-8-1下午7:03
 */
class ProgressViewImpl(val context: Context?) : ProgressView, LifecycleEventObserver {

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

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_DESTROY) {
            proDialogDismiss()
        }
    }

}