package com.dtb.core.base

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

/**
 * Project com.daotangbill.exlib.base
 * Created by DaoTangBill on 2018/2/27/027.
 * Email:tangbaozi@daotangbill.uu.me
 * @author bill
 * @version 1.0
 * @description
 */
open class DtbBaseDialogFragment : DialogFragment() {

    override fun show(manager: FragmentManager, tag: String?) = try {
        super.show(manager, tag)
    } catch (e: Exception) {

    }
}