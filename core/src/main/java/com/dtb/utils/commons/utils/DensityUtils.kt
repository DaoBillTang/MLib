package com.dtb.utils.commons.utils

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Rect
import android.util.TypedValue
import android.view.View

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
fun dip2px(dpValue: Float): Int {
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

fun screenShotWithStatusBar(activity: Activity): Bitmap? {
    val view = activity.window.decorView
    return screenShot(view, 0, 0, view.width, view.height)
}

fun screenShotWithStatusBar(fragment: Fragment): Bitmap? =
        screenShotWithStatusBar(fragment.activity)

/**
 * 获取当前屏幕截图，不包含状濁栏
 *
 * @param activity
 * @return
 */
fun screenShotWithoutStatusBar(activity: Activity): Bitmap? {
    val view = activity.window.decorView
    val frame = Rect()
    activity.window.decorView.getWindowVisibleDisplayFrame(frame)
    val statusBarHeight = frame.top
    val width = screenW()
    val height = screenH()
    return screenShot(view, 0, statusBarHeight, width, height - statusBarHeight)
}

/**
 * 获取某个View 的截屏
 * @param view 需要截屏的View
 * @param paddingX 需要偏离X轴的位移
 * @param paddingY 需要偏移Y轴的位移
 * @param width 需要绘制的图像的宽度
 * @param height 需要绘制的图像的高度
 * @return
 */
fun screenShot(view: View, paddingX: Int, paddingY: Int, width: Int, height: Int): Bitmap? {
    view.isDrawingCacheEnabled = true
    view.buildDrawingCache()
    val bmp = view.drawingCache
    val bp: Bitmap? = Bitmap.createBitmap(bmp, paddingX, paddingY, width, height)
    view.destroyDrawingCache()
    return bp
}