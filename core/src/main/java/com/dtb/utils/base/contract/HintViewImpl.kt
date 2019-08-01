package com.dtb.utils.base.contract

import android.content.Context
import com.dtb.utils.commons.toast.Terror

/**
 * @author: dtb
 * @email: wendaoyuqin1@gmail.com
 * @createTime: 19-8-1下午7:00
 */
class HintViewImpl(val context: Context?) : HintView {

    override fun showErr(msg: String?) {
        context?.Terror(msg ?: "")
    }

    override fun showErr(msgList: ArrayList<String>?) {
        msgList?.forEach {
            showErr(it)
        }
    }

    override fun showErr(msgList: Array<String>?) {
        msgList?.forEach {
            showErr(it)
        }
    }
}
