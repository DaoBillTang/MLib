package com.daotangbill.exlib.commons.utils

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.graphics.drawable.NinePatchDrawable
import android.os.Build
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.view.View
import com.daotangbill.exlib.exlib.R

/**
 * project com.daotangbill.dt_ext.exlib.commons.Utils
 * Created by Bill on 2017/9/18.
 * emal: tangbakzi@daotangbill.uu.me
 * @author: Bill
 * @version: 1.0
 * @description:处理图片 的方法合集
 */

fun s(){
    "".let {  }


}

/**
 * 染色 图标
 */
fun getTintIcon(drawable: Drawable?, @ColorInt tintColor: Int): Drawable? {
    drawable?.setColorFilter(tintColor, PorterDuff.Mode.SRC_IN)
    return drawable
}

fun Context?.tint9PatchDrawableFrame(@ColorInt tintColor: Int): Drawable? {
    val toastDrawable = this.getDrawableBySdk(R.drawable.toast_frame) as NinePatchDrawable?
    return getTintIcon(toastDrawable!!, tintColor)
}

fun setBackground(view: View?, drawable: Drawable?) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
        view?.background = drawable
    else
        view?.setBackgroundDrawable(drawable)
}

/**
 * 根据 sdk 判断选择的getDrawable方法
 */
fun Context?.getDrawableBySdk(@DrawableRes id: Int): Drawable? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        this?.getDrawable(id)
    else
        this?.resources?.getDrawable(id)
}