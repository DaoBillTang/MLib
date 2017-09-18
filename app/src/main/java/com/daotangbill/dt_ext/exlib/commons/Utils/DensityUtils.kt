package com.daotangbill.dt_ext.exlib.commons.Utils

import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import android.util.TypedValue

/**
 * Created by BILL on 2016/11/24.
 * email:tangbaozi@daotangbill.uu.me
 * 系统屏幕的 工具类
 *
 * @author BILL
 * @version 1.0
 */

/**
 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
 */
fun dip2px(context: Context, dpValue: Float): Int {
    val px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
            dpValue, Resources.getSystem().displayMetrics)
    return px.toInt()
}

/**
 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
 */
fun px2dip(pxValue: Float): Int {
    val scale = Resources.getSystem().displayMetrics.density
    return (pxValue / scale + 0.5f).toInt()
}

/**
 * 根据手机的分辨率从 px(像素) 的单位 转成为 sp
 */
fun px2sp(pxValue: Float): Int {
    val fontScale = Resources.getSystem().displayMetrics.scaledDensity
    return (pxValue / fontScale + 0.5f).toInt()
}

/**
 * 根据手机的分辨率从 sp 的单位 转成为 px
 */
fun sp2px(spValue: Float): Int {
    val fontScale = Resources.getSystem().displayMetrics.scaledDensity
    return (spValue * fontScale + 0.5f).toInt()
}

/**
 * 获取dialog宽度
 */
fun getDialogW(aty: Context): Int {
    val dm = aty.resources.displayMetrics
    // int w = aty.getWindowManager().getDefaultDisplay().getWidth() - 100;
    return dm.widthPixels - 100
}

/**
 * 获取屏幕宽度
 */
//DisplayMetrics dm = Context.getResources().getDisplayMetrics();
fun screenW(): Int = Resources.getSystem().displayMetrics.widthPixels

/**
 * 获取屏幕高度
 */
fun screenH(): Int = Resources.getSystem().displayMetrics.heightPixels