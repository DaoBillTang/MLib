package com.dtb.utils

import com.dtb.utils.base.DtbBaseActivity
import com.dtb.utils.base.contract.ProgressViewImpl


/**
 * @author: dtb
 * @email: wendaoyuqin1@gmail.com
 * @createTime: 19-8-1下午7:11
 */

class TestClass : DtbBaseActivity() {

    override val progressViewImpl by lazy {
        ProgressViewImpl(this)
    }

    override fun getLayoutResource(): Int {
        return 0
    }

    override fun showProgressDialog(message: String?) {
        super.showProgressDialog(message)
    }
}