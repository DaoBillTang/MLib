package com.dtb.utils.base

import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager

/**
 * Project com.daotangbill.exlib.base
 * Created by DaoTangBill on 2018/2/27/027.
 * Email:tangbaozi@daotangbill.uu.me
 * @author bill
 * @version 1.0
 * @description
 */
open class DtbBaseDialogFragment : DialogFragment() {

    /**
     *  取消掉 了 commit 改为使用 commitAllowingStateLoss；
     *  
     */
    override fun show(manager: FragmentManager?, tag: String?) = try {
        super.show(manager, tag)
    }catch (e:Exception){

    }
}