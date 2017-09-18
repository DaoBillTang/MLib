package com.daotangbill.dt_ext.exlib.commons.Utils

import android.app.Activity
import android.app.PendingIntent
import android.app.PendingIntent.CanceledException
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.Uri
import android.support.annotation.IdRes
import android.support.annotation.UiThread
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.util.DisplayMetrics
import android.util.SparseArray
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import com.daotangbill.dt_ext.exlib.Base.DtBaseApp


/**
 * Project hschefu-android
 * Created by BILL on 2017/5/15.
 * email:tangbaozi@daotangbill.uu.me
 *
 * @author BILL
 * @version 2.0
 */

inline fun <reified T : View>
        Activity.findView(@IdRes id: Int): T = findViewById(id) as T

inline fun <reified T : View>
        View.findView(@IdRes id: Int): T = findViewById(id) as T

inline fun <reified T : View>
        Fragment.findView(@IdRes id: Int): T = this.view?.findViewById(id) as T


/**
 * 自带holder 缓存的FindViewById方法
 */
inline fun <reified T : View>
        View.findViewOften(@IdRes viewId: Int): T {
    val ViewHolders: SparseArray<View> = tag as? SparseArray<View> ?: SparseArray()
    tag = ViewHolders
    var childView: View? = ViewHolders.get(viewId)
    if (null == childView) {
        childView = findViewById(viewId)
        ViewHolders.put(viewId, childView)
    }
    return childView as T
}

@UiThread
fun Context.TToast(message: String) {
    val mToast = Toast.makeText(this, message + "", Toast.LENGTH_SHORT)
    mToast.setGravity(Gravity.CENTER, 0, 0)
    mToast.show()
}

@UiThread
fun Activity.TToast(message: String) {
    val mToast = Toast.makeText(this, message + "", Toast.LENGTH_SHORT)
    mToast.setGravity(Gravity.CENTER, 0, 0)
    mToast.show()
}

private var exitTime: Long = 0//记录时间
fun Activity.doubleBackToExit() {
    if (System.currentTimeMillis() - exitTime > 2000) {
        cToast("再按一次退出程序")
        exitTime = System.currentTimeMillis()
    } else {
        DtBaseApp.INSTANCE.exit()
    }
}

@UiThread
fun Fragment.TToast(message: String) {
    val mToast = Toast.makeText(this.activity, message + "", Toast.LENGTH_SHORT)
    mToast.setGravity(Gravity.CENTER, 0, 0)
    mToast.show()
}


@UiThread
fun Context.TToast(str: Int) {
    val mToast = Toast.makeText(this, this.resources.getString(str) + "", Toast.LENGTH_SHORT)
    mToast.setGravity(Gravity.CENTER, 0, 0)
    mToast.show()
}


@UiThread
fun Activity.TToast(str: Int) {
    val mToast = Toast.makeText(this, this.resources.getString(str) + "", Toast.LENGTH_SHORT)
    mToast.setGravity(Gravity.CENTER, 0, 0)
    mToast.show()
}


@UiThread
fun Fragment.TToast(str: Int) {
    val mToast = Toast.makeText(this.activity, this.resources.getString(str) + "", Toast.LENGTH_SHORT)
    mToast.setGravity(Gravity.CENTER, 0, 0)
    mToast.show()
}


@UiThread
fun Context.cToast(message: String): Unit {
    val mToast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
    mToast.setGravity(Gravity.CENTER, 0, 0)
    mToast.show()
}

@UiThread
fun Fragment.cToast(message: String) = this.activity.cToast(message)

/**
 * 显示 回复dialog
 * @param msg   通知的内容
 * *
 * @param title 标题
 */
@UiThread
fun Activity.showResultDialog(title: String?, msg: String?
                              , button: String = "我知道了") {
    AlertDialog.Builder(this).setTitle(title).setMessage(msg)
            .setNegativeButton(button, null).create().show()
}

/**
 * 显示 回复dialog
 * @param msg   通知的内容
 * *
 * @param title 标题
 */
@UiThread
fun Fragment.showResultDialog(msg: String?, title: String?
                              , button: String = "我知道了") {
    AlertDialog.Builder(this.activity).setTitle(title).setMessage(msg)
            .setNegativeButton(button, null).create().show()
}

@UiThread
fun Activity.showConfirmCancelDialog(title: String?, message: String?
                                     , posListener: DialogInterface.OnClickListener?
                                     , cancelListener: DialogInterface.OnClickListener?)
        : AlertDialog {
    val dlg = AlertDialog.Builder(this).setMessage(message).setTitle(title)
            .setPositiveButton("确认", posListener)
            .setNegativeButton("取消", cancelListener).create()
    dlg.setCanceledOnTouchOutside(false)
    dlg.show()
    return dlg
}

@UiThread
fun Fragment.showConfirmCancelDialog(title: String, message: String
                                     , posListener: DialogInterface.OnClickListener
                                     , cancelListener: DialogInterface.OnClickListener)
        : AlertDialog {
    val dlg = AlertDialog.Builder(this.activity).setMessage(message).setTitle(title)
            .setPositiveButton("确认", posListener)
            .setNegativeButton("取消", cancelListener).create()
    dlg.setCanceledOnTouchOutside(false)
    dlg.show()
    return dlg
}

fun Context.isNetworkConnected(): Boolean {
    val mConnectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val ni = mConnectivityManager.activeNetworkInfo
    return ni != null && ni.isConnectedOrConnecting
}


fun Context.isWifiConnected(): Boolean {
    val mConnectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE)
            as ConnectivityManager
    val mWiFiNetworkInfo = mConnectivityManager
            .getNetworkInfo(ConnectivityManager.TYPE_WIFI)
    if (mWiFiNetworkInfo != null) {
        return mWiFiNetworkInfo.isAvailable
    }
    return false
}

/**
 * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
 *
 * @return true 表示开启
 */
fun Context.IsGpsOPen(): Boolean {
    val locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
    val gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。
    // 主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
    val network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    if (gps || network) {
        return true
    }
    return false
}

/**
 * 强制帮用户打开GPS
 */
fun Context.openGPS() {
    val GPSIntent = Intent()
    GPSIntent.setClassName("com.android.settings",
            "com.android.settings.widget.SettingsAppWidgetProvider")
    GPSIntent.addCategory("android.intent.category.ALTERNATIVE")
    GPSIntent.data = Uri.parse("custom:3")
    try {
        PendingIntent.getBroadcast(this, 0, GPSIntent, 0).send()
    } catch (e: CanceledException) {
        e.printStackTrace()
    }
}


fun Activity.hideKeyboard() {
    val inputMethodManager = this.getSystemService(Context.INPUT_METHOD_SERVICE)
            as InputMethodManager?
    inputMethodManager?.hideSoftInputFromWindow(window.decorView.windowToken
            , InputMethodManager.HIDE_NOT_ALWAYS)
}

fun Activity.hideKeyboard(editText: EditText) {
    val inputMethodManager = this.getSystemService(Context.INPUT_METHOD_SERVICE)
            as InputMethodManager?
    inputMethodManager?.hideSoftInputFromWindow(editText.windowToken, 0)
}

fun Fragment.hideKeyboard() {
    this.activity.hideKeyboard()
}

fun Context.getScreenWith(): Int {
    val manager = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val dm = DisplayMetrics()
    manager.defaultDisplay.getMetrics(dm)
    return dm.widthPixels
}

fun Context.getScreenHeight(): Int {
    val manager = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val dm = DisplayMetrics()
    manager.defaultDisplay.getMetrics(dm)
    return dm.heightPixels
}