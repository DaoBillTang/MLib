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
import com.daotangbill.dt_ext.exlib.commons.toast.ToastExt.DEFAULT_TEXT_COLOR
import com.daotangbill.dt_ext.exlib.commons.toast.ToastExt.currentTypeface
import com.daotangbill.dt_ext.exlib.commons.toast.ToastExt.textSize
import com.daotangbill.dt_ext.exlib.commons.toast.ToastExt.tintIcon

/**
 * project com.daotangbill.dt_ext.exlib.commons.toast
 * Created by Bill on 2017/9/18.
 * emal: tangbakzi@daotangbill.uu.me
 * @author: Bill
 * @version: 1.0
 * @description:
 */
fun Context.Tnormal(message: CharSequence) =
        this.Tnormal(message, Toast.LENGTH_SHORT, null, false)?.show()

@CheckResult
fun Context.Tnormal(message: CharSequence, icon: Drawable) =
        this.Tnormal(message, Toast.LENGTH_SHORT, icon, true)?.show()

@CheckResult
fun Context.Tnormal(message: CharSequence, duration: Int) =
        this.Tnormal(message, duration, null, false)?.show()

@CheckResult
fun Context.Tnormal(message: CharSequence, duration: Int,
                    icon: Drawable) =
        this.Tnormal(message, duration, icon, true)?.show()

@CheckResult
fun Context.Tnormal(message: CharSequence, duration: Int,
                    icon: Drawable?, withIcon: Boolean) =
        custom(this, message, icon, ToastExt.NORMAL_COLOR, duration, withIcon, true)

@CheckResult
fun Context.Twarning(message: CharSequence) =
        this.Twarning(message, Toast.LENGTH_SHORT, true)?.show()

@CheckResult
fun Context.Twarning(message: CharSequence, duration: Int) =
        this.Twarning(message, duration, true)?.show()

@CheckResult
fun Context.Twarning(message: CharSequence,
                     duration: Int,
                     withIcon: Boolean): Toast?
        = custom(message, R.drawable.ic_error_outline_white_48dp,
        ToastExt.WARNING_COLOR, duration, withIcon, true)

@CheckResult
@JvmOverloads
fun Context.Tinfo(
        message: CharSequence,
        duration: Int = Toast.LENGTH_SHORT,
        withIcon: Boolean = true)
        = custom(message,
        R.drawable.ic_info_outline_white_48dp,
        ToastExt.INFO_COLOR, duration, withIcon, true)?.show()

@CheckResult
@JvmOverloads
fun Context.Tsuccess(
        message: CharSequence,
        duration: Int = Toast.LENGTH_SHORT,
        withIcon: Boolean = true)
        = custom(message, R.drawable.ic_check_white_48dp,
        ToastExt.SUCCESS_COLOR, duration, withIcon, true)?.show()

@CheckResult
@JvmOverloads
fun Context.Terror(
        message: CharSequence,
        duration: Int = Toast.LENGTH_SHORT,
        withIcon: Boolean = true)
        = custom(message, R.drawable.ic_clear_white_48dp,
        ToastExt.ERROR_COLOR, duration, withIcon, true)?.show()

fun Context.custom(message: CharSequence, icon: Drawable,
                   duration: Int, withIcon: Boolean)
        = custom(this, message, icon, -1, duration, withIcon, false)?.show()

@CheckResult
fun Context.custom(message: CharSequence, @DrawableRes iconRes: Int,
                   @ColorInt tintColor: Int, duration: Int,
                   withIcon: Boolean, shouldTint: Boolean): Toast?
        = custom(this, message, this.getDrawableBySdk(iconRes),
        tintColor, duration, withIcon, shouldTint)

object ToastExt {
    var DEFAULT_TEXT_COLOR = Color.parseColor("#FFFFFF")
    var ERROR_COLOR = Color.parseColor("#D50000")
    var INFO_COLOR = Color.parseColor("#3F51B5")
    var SUCCESS_COLOR = Color.parseColor("#388E3C")
    var WARNING_COLOR = Color.parseColor("#FFA900")
    var NORMAL_COLOR = Color.parseColor("#353A3E")

    val LOADED_TOAST_TYPEFACE = Typeface.create("sans-serif-condensed", Typeface.NORMAL)
    var currentTypeface = LOADED_TOAST_TYPEFACE
    var textSize = 16 // in SP

    var tintIcon = true
}

private fun custom(context: Context, message: CharSequence, inIcon: Drawable?,
                   @ColorInt tintColor: Int, duration: Int,
                   withIcon: Boolean, shouldTint: Boolean): Toast? {
    var icon = inIcon
    val currentToast = Toast(context)
    val toastLayout = (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
            .inflate(R.layout.toast_layout, null)
    val toastIcon = toastLayout.findViewById(R.id.toast_icon) as ImageView
    val toastTextView = toastLayout.findViewById(R.id.toast_text) as TextView
    val drawableFrame: Drawable? = if (shouldTint) {
        context.tint9PatchDrawableFrame(tintColor)
    } else {
        context.getDrawableBySdk(R.drawable.toast_frame)
    }
    setBackground(toastLayout, drawableFrame)

    if (withIcon) {
        if (icon == null)
            throw IllegalArgumentException("Avoid passing 'icon' as null if 'withIcon' is set to true")
        if (tintIcon)
            icon = getTintIcon(icon, DEFAULT_TEXT_COLOR)
        setBackground(toastIcon, icon)
    } else {
        toastIcon.visibility = View.GONE
    }

    toastTextView.setTextColor(DEFAULT_TEXT_COLOR)
    toastTextView.text = message
    toastTextView.typeface = currentTypeface
    toastTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize.toFloat())

    currentToast.view = toastLayout
    currentToast.duration = duration
    return currentToast
}

class Config private constructor() {
    @ColorInt
    private var DEFAULT_TEXT_COLOR = ToastExt.DEFAULT_TEXT_COLOR
    @ColorInt
    private var ERROR_COLOR = ToastExt.ERROR_COLOR
    @ColorInt
    private var INFO_COLOR = ToastExt.INFO_COLOR
    @ColorInt
    private var SUCCESS_COLOR = ToastExt.SUCCESS_COLOR
    @ColorInt
    private var WARNING_COLOR = ToastExt.WARNING_COLOR

    private var typeface = ToastExt.currentTypeface
    private var textSize = ToastExt.textSize

    private var tintIcon = ToastExt.tintIcon

    @CheckResult
    fun setTextColor(@ColorInt textColor: Int): Config {
        DEFAULT_TEXT_COLOR = textColor
        return this
    }

    @CheckResult
    fun setErrorColor(@ColorInt errorColor: Int): Config {
        ERROR_COLOR = errorColor
        return this
    }

    @CheckResult
    fun setInfoColor(@ColorInt infoColor: Int): Config {
        INFO_COLOR = infoColor
        return this
    }

    @CheckResult
    fun setSuccessColor(@ColorInt successColor: Int): Config {
        SUCCESS_COLOR = successColor
        return this
    }

    @CheckResult
    fun setWarningColor(@ColorInt warningColor: Int): Config {
        WARNING_COLOR = warningColor
        return this
    }

    @CheckResult
    fun setToastTypeface(typeface: Typeface): Config {
        this.typeface = typeface
        return this
    }

    @CheckResult
    fun setTextSize(sizeInSp: Int): Config {
        this.textSize = sizeInSp
        return this
    }

    @CheckResult
    fun tintIcon(tintIcon: Boolean): Config {
        this.tintIcon = tintIcon
        return this
    }

    fun apply() {
        ToastExt.DEFAULT_TEXT_COLOR = DEFAULT_TEXT_COLOR
        ToastExt.ERROR_COLOR = ERROR_COLOR
        ToastExt.INFO_COLOR = INFO_COLOR
        ToastExt.SUCCESS_COLOR = SUCCESS_COLOR
        ToastExt.WARNING_COLOR = WARNING_COLOR
        ToastExt.currentTypeface = typeface
        ToastExt.textSize = textSize
        ToastExt.tintIcon = tintIcon
    }

    companion object {

        val instance: Config
            @CheckResult
            get() = Config()

        fun reset() {
            ToastExt.DEFAULT_TEXT_COLOR = Color.parseColor("#FFFFFF")
            ToastExt.ERROR_COLOR = Color.parseColor("#D50000")
            ToastExt.INFO_COLOR = Color.parseColor("#3F51B5")
            ToastExt.SUCCESS_COLOR = Color.parseColor("#388E3C")
            ToastExt.WARNING_COLOR = Color.parseColor("#FFA900")
            ToastExt.currentTypeface = Typeface.create("sans-serif-condensed", Typeface.NORMAL)
            ToastExt.textSize = 16
            ToastExt.tintIcon = true
        }
    }
}