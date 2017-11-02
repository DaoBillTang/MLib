package com.daotangbill.exlib.commons.view

import android.view.View

/**
 * project com.daotangbill.dt_ext.exlib.commons.view
 * Created by Bill on 2017/10/27.
 * emal: tangbakzi@daotangbill.uu.me
 * @author Bill
 * @version 1.0
 * @description
 */


fun View?.bindClick(l: View.OnClickListener, time: Long) {
    val time = System.currentTimeMillis()





    this?.setOnClickListener { l }

}