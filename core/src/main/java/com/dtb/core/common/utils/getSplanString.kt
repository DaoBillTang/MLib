package com.dtb.core.common.utils

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan

/**
 * project com.ecentm.customer.utils
 * Created by Bill on 2017/11/13.
 * emal: tangbakzi@daotangbill.uu.me
 * @author Bill
 * @version 1.0
 * @description
 */
/**
 *对一个文字添加样式
 * @param string :需要的文字
 * @param proportion :缩放倍数
 * @param color :需要改变的颜色
 * @param isBold :是否缩放
 */
fun getSplanString(string: String, proportion: Float, color: String = "#CFAF78")
        : SpannableString? {
    return newSpan(string).addSize(proportion).addColor(color)
}

fun getSplanString(string: String, proportion: Float, color: Int = 0)
        : SpannableString? {
    return newSpan(string).addSize(proportion).addColor(color)
}

fun newSpan(string: String): SpannableString {
    return SpannableString(string)
}

fun SpannableString.setNormalSpan(what: Any): SpannableString {
    this.setSpan(what, 0, this.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
    return this
}

fun SpannableString.addColor(color: Int = 0): SpannableString {
    this.setNormalSpan(ForegroundColorSpan(color))
    return this
}

fun SpannableString.addColor(color: String): SpannableString {
    this.setNormalSpan(ForegroundColorSpan(Color.parseColor(color)))
    return this
}

fun SpannableString.addColorId(color: Int, context: Context): SpannableString {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        this.setNormalSpan(ForegroundColorSpan(context.resources.getColor(color, null)))
    } else {
        this.setNormalSpan(ForegroundColorSpan(context.resources.getColor(color)))
    }
    return this
}


fun SpannableString.addSize(proportion: Float): SpannableString {
    this.setNormalSpan(RelativeSizeSpan(proportion))
    return this
}

fun SpannableString.addStringStyle(style: Int): SpannableString {
    this.setNormalSpan(StyleSpan(style))
    return this
}

