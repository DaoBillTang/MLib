package com.daotangbill.exlib.commons.utils

import android.graphics.Bitmap


/**
 * project com.daotangbill.exlib.commons.utils
 * Created by Bill on 2017/11/8.
 * emal: tangbakzi@daotangbill.uu.me
 * @author Bill
 * @version 1.0
 * @description
 */
fun Bitmap?.Blur(radius: Int): Bitmap? {
    if (this == null) {
        return null
    }
    return BitMapUtils.onStackBlurJava(this, radius)
}