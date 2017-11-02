package com.daotangbill.exlib.commons.view

import android.widget.EditText
import android.text.InputFilter
import android.text.LoginFilter

/**
 * project com.daotangbill.dt_ext.exlib.commons.view
 * Created by Bill on 2017/10/13.
 * emal: tangbakzi@daotangbill.uu.me
 *
 * @author: Bill
 * @version: 1.0
 * @description:
 */
fun EditText?.Filter(filterStr: String?) {
    val filters = arrayOfNulls<InputFilter>(1)
    if (filterStr != null) filters[0] = MyInputFilter(filterStr)
    this?.filters = filters
}

fun EditText?.Filter(filterStr: String?, max: Int?) {
    if (filterStr == null) return
    if (max == null) return
    val filters = arrayOfNulls<InputFilter>(2)
    filters[0] = MyInputFilter(filterStr)
    filters[1] = InputFilter.LengthFilter(max)
    this?.filters = filters
}

class MyInputFilter(private val mAllowedDigits: String) : LoginFilter.UsernameFilterGeneric() {

    override fun isAllowed(c: Char): Boolean {
        return mAllowedDigits.indexOf(c) != -1
    }
}