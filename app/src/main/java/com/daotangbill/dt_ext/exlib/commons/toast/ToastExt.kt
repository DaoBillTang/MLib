package com.daotangbill.dt_ext.exlib.commons.toast

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.annotation.CheckResult
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.widget.Toast
import com.daotangbill.dt_ext.exlib.R
import com.daotangbill.dt_ext.exlib.commons.Utils.getDrawableBySdk

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
        Toasty.WARNING_COLOR, duration, withIcon, true)

@CheckResult
@JvmOverloads
fun Context.Tinfo(
        message: CharSequence,
        duration: Int = Toast.LENGTH_SHORT,
        withIcon: Boolean = true): Toast?
        = Toasty.custom(this, message,
        this.getDrawableBySdk(R.drawable.ic_info_outline_white_48dp),
        Toasty.INFO_COLOR, duration, withIcon, true)

@CheckResult
@JvmOverloads
fun Context.Tsuccess(
        message: CharSequence,
        duration: Int = Toast.LENGTH_SHORT,
        withIcon: Boolean = true): Toast?
        = Toasty.custom(this, message, this.getDrawableBySdk(R.drawable.ic_check_white_48dp),
        Toasty.SUCCESS_COLOR, duration, withIcon, true)

@CheckResult
@JvmOverloads
fun Context.Terror(
        message: CharSequence,
        duration: Int = Toast.LENGTH_SHORT,
        withIcon: Boolean = true): Toast?
        = Toasty.custom(this, message, this.getDrawableBySdk(R.drawable.ic_clear_white_48dp),
        Toasty.ERROR_COLOR, duration, withIcon, true)

@CheckResult
fun Context.custom(message: CharSequence, icon: Drawable,
                   duration: Int, withIcon: Boolean): Toast?
        = Toasty.custom(this, message, icon, -1, duration, withIcon, false)

@CheckResult
fun Context.custom(message: CharSequence, @DrawableRes iconRes: Int,
                   @ColorInt tintColor: Int, duration: Int,
                   withIcon: Boolean, shouldTint: Boolean): Toast?
        = Toasty.custom(this, message, this.getDrawableBySdk(iconRes),
        tintColor, duration, withIcon, shouldTint)


fun resetToast() {
    val config = Toasty.Config.reset()
}

