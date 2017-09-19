package com.daotangbill.dt_ext.exlib.commons.toast

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.support.annotation.CheckResult
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.daotangbill.dt_ext.exlib.R
import com.daotangbill.dt_ext.exlib.commons.Utils.getDrawableBySdk
import com.daotangbill.dt_ext.exlib.commons.Utils.getTintIcon
import com.daotangbill.dt_ext.exlib.commons.Utils.setBackground
import com.daotangbill.dt_ext.exlib.commons.Utils.tint9PatchDrawableFrame
import com.daotangbill.dt_ext.exlib.commons.toast.ToastConfig.DEFAULT_TEXT_COLOR
import com.daotangbill.dt_ext.exlib.commons.toast.ToastConfig.ERROR_COLOR
import com.daotangbill.dt_ext.exlib.commons.toast.ToastConfig.INFO_COLOR
import com.daotangbill.dt_ext.exlib.commons.toast.ToastConfig.SUCCESS_COLOR
import com.daotangbill.dt_ext.exlib.commons.toast.ToastConfig.WARNING_COLOR
import com.daotangbill.dt_ext.exlib.commons.toast.ToastConfig.currentTypeface
import com.daotangbill.dt_ext.exlib.commons.toast.ToastConfig.textSize
import com.daotangbill.dt_ext.exlib.commons.toast.ToastConfig.tintIcon

/**
 * project com.daotangbill.dt_ext.exlib.commons.toast
 * Created by Bill on 2017/9/18.
 * emal: tangbakzi@daotangbill.uu.me
 * @author: Bill
 * @version: 1.0
 * @description:
 */
@CheckResult
fun Context.Tnormal(message: CharSequence): Toast? =
        this.Tnormal(message, Toast.LENGTH_SHORT, null, false)

@CheckResult
fun Context.Tnormal(message: CharSequence, icon: Drawable): Toast? =
        this.Tnormal(message, Toast.LENGTH_SHORT, icon, true)

@CheckResult
fun Context.Tnormal(message: CharSequence, duration: Int): Toast? =
        this.Tnormal(message, duration, null, false)

@CheckResult
fun Context.Tnormal(message: CharSequence, duration: Int,
                    icon: Drawable): Toast? =
        this.Tnormal(message, duration, icon, true)

@CheckResult
fun Context.Tnormal(message: CharSequence, duration: Int,
                    icon: Drawable?, withIcon: Boolean): Toast? =
        Toasty.custom(this, message, icon, Toasty.NORMAL_COLOR, duration, withIcon, true)

@CheckResult
fun Context.Twarning(message: CharSequence): Toast? =
        this.Twarning(message, Toast.LENGTH_SHORT, true)

@CheckResult
fun Context.Twarning(message: CharSequence, duration: Int): Toast? =
        this.Twarning(message, duration, true)

@CheckResult
fun Context.Twarning(message: CharSequence,
                     duration: Int,
                     withIcon: Boolean): Toast?
        = Toasty.custom(this, message, this.getDrawableBySdk(R.drawable.ic_error_outline_white_48dp),
        WARNING_COLOR, duration, withIcon, true)

@CheckResult
@JvmOverloads
fun Context.Tinfo(
        message: CharSequence,
        duration: Int = Toast.LENGTH_SHORT,
        withIcon: Boolean = true): Toast?
        = Toasty.custom(this, message,
        this.getDrawableBySdk(R.drawable.ic_info_outline_white_48dp),
        INFO_COLOR, duration, withIcon, true)

@CheckResult
@JvmOverloads
fun Context.Tsuccess(
        message: CharSequence,
        duration: Int = Toast.LENGTH_SHORT,
        withIcon: Boolean = true): Toast?
        = Toasty.custom(this, message, this.getDrawableBySdk(R.drawable.ic_check_white_48dp),
        SUCCESS_COLOR, duration, withIcon, true)

@CheckResult
@JvmOverloads
fun Context.Terror(
        message: CharSequence,
        duration: Int = Toast.LENGTH_SHORT,
        withIcon: Boolean = true): Toast?
        = Toasty.custom(this, message, this.getDrawableBySdk(R.drawable.ic_clear_white_48dp),
        ERROR_COLOR, duration, withIcon, true)

@CheckResult
fun Context.custom(message: CharSequence, icon: Drawable,
                   duration: Int, withIcon: Boolean): Toast?
        = Toasty.custom(this,message, icon, -1, duration, withIcon, false)

@CheckResult
fun Context.custom(message: CharSequence, @DrawableRes iconRes: Int,
                   @ColorInt tintColor: Int, duration: Int,
                   withIcon: Boolean, shouldTint: Boolean): Toast?
        = Toasty.custom(this,message, this.getDrawableBySdk(iconRes),
        tintColor, duration, withIcon, shouldTint)

object ToastConfig {

    internal var DEFAULT_TEXT_COLOR = Color.parseColor("#FFFFFF")
    internal var ERROR_COLOR = Color.parseColor("#D50000")
    internal var INFO_COLOR = Color.parseColor("#3F51B5")
    internal var SUCCESS_COLOR = Color.parseColor("#388E3C")
    internal var WARNING_COLOR = Color.parseColor("#FFA900")
    internal var NORMAL_COLOR = Color.parseColor("#353A3E")

    internal var LOADED_TOAST_TYPEFACE = Typeface.create("sans-serif-condensed", Typeface.NORMAL)
    internal var currentTypeface = LOADED_TOAST_TYPEFACE
    internal var textSize = 16 // in SP

    internal var tintIcon = true

    fun reset() {
        DEFAULT_TEXT_COLOR = Color.parseColor("#FFFFFF")
        ERROR_COLOR = Color.parseColor("#D50000")
        INFO_COLOR = Color.parseColor("#3F51B5")
        SUCCESS_COLOR = Color.parseColor("#388E3C")
        WARNING_COLOR = Color.parseColor("#FFA900")
        NORMAL_COLOR = Color.parseColor("#353A3E")

        LOADED_TOAST_TYPEFACE = Typeface.create("sans-serif-condensed", Typeface.NORMAL)
        currentTypeface = LOADED_TOAST_TYPEFACE
        textSize = 16 // in SP

        tintIcon = true
    }
}