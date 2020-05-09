@file:Suppress("NOTHING_TO_INLINE", "unused")

package com.dtb.core.common.basetype

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.net.Uri
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.*
import android.view.View
import androidx.annotation.DrawableRes
import com.dtb.core.common.utils.px2sp

/**
 * project mlib
 * Created by Bill on 2017/11/13.
 * email: tangbakzi@daotangbill.uu.me
 * @author Bill
 * @version 1.0
 * @description
 */
@SuppressLint("SupportAnnotationUsage")
class SpannableContext(private val str: CharSequence) {
    /**
     * Spannable. SPAN_INCLUSIVE_EXCLUSIVE 前面包括，后面不包括，意思就是在这段文本前部插入新的文本会应用该样式，而在文本后部插入新文本则不会应用该样式
     * Spannable. SPAN_INCLUSIVE_INCLUSIVE 前面包括，后面包括，意思就是在这段文本前部插入新的文本会应用该样式，在文本后部插入新文本也会应用该样式
     * Spanned.SPAN_EXCLUSIVE_EXCLUSIVE 前面不包括，后面不包括
     * Spanned.SPAN_EXCLUSIVE_INCLUSIVE 前面不包括，后面包括
     */
    val span = SpannableString(str)

    fun setSpan(what: Any) {
        span.setSpan(what, 0, str.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    fun relativesSize(proportion: Float) {
        setSpan(RelativeSizeSpan(proportion))
    }

    fun spSize(proportion: Float) {
        absoluteSize(px2sp(proportion))
    }

    fun absoluteSize(proportion: Int) {
        setSpan(AbsoluteSizeSpan(proportion))
    }

    fun color(c: String) {
        color(Color.parseColor(c))
    }

    fun color(c: Int?) {
        c?.apply { setSpan(ForegroundColorSpan(c)) }
    }

    fun colorRes(c: Int, context: Context) {
        color(context.resources?.getColor(c))
    }

    fun backgroundColor(c: Int?) {
        c?.apply { setSpan(BackgroundColorSpan(c)) }
    }

    fun backgroundColor(c: String) {
        backgroundColor(Color.parseColor(c))
    }

    fun backgroundColorRes(c: Int, context: Context) {
        backgroundColor(context.resources?.getColor(c))
    }

    fun underline() {
        setSpan(UnderlineSpan())
    }

    /**
     * 删除线
     */
    fun strikeThrough() {
        setSpan(StrikethroughSpan())
    }

    /**
     * Typeface
     *    public static final Typeface DEFAULT_BOLD;
    /** The NORMAL style of the default sans serif typeface. */
    public static final Typeface SANS_SERIF;
    /** The NORMAL style of the default serif typeface. */
    public static final Typeface SERIF;
    /** The NORMAL style of the default monospace typeface. */
    public static final Typeface MONOSPACE;
     */
    fun style(s: Int) {
        Typeface.BOLD
        setSpan(StyleSpan(s))
    }

    fun superscript() {
        setSpan(SuperscriptSpan())
    }

    fun subscript() {
        setSpan(SubscriptSpan())
    }

    /**
     * Constructs an [ImageSpan] from a [Context] and a [Bitmap] with the default
     * alignment [DynamicDrawableSpan.ALIGN_BOTTOM]
     *
     * @param context context used to create a drawable from {@param bitmap} based on the display
     * metrics of the resources
     * @param bitmap  bitmap to be rendered
     */
    fun img(context: Context, bitmap: Bitmap) {
        setSpan(ImageSpan(context, bitmap))
    }

    /**
     * Constructs an [ImageSpan] from a [Context], a [Bitmap] and a vertical
     * alignment.
     *
     * @param context           context used to create a drawable from {@param bitmap} based on
     * the display metrics of the resources
     * @param bitmap            bitmap to be rendered
     * @param verticalAlignment one of [DynamicDrawableSpan.ALIGN_BOTTOM] or
     * [DynamicDrawableSpan.ALIGN_BASELINE]
     */
    fun img(
        context: Context,
        bitmap: Bitmap,
        verticalAlignment: Int
    ) {
        setSpan(ImageSpan(context, bitmap, verticalAlignment))
    }

    /**
     * Constructs an [ImageSpan] from a drawable with the default
     * alignment [DynamicDrawableSpan.ALIGN_BOTTOM].
     *
     * @param drawable drawable to be rendered
     */
    fun img(drawable: Drawable) {
        setSpan(ImageSpan(drawable, DynamicDrawableSpan.ALIGN_BOTTOM))
    }

    /**
     * Constructs an [ImageSpan] from a drawable and a vertical alignment.
     *
     * @param drawable          drawable to be rendered
     * @param verticalAlignment one of [DynamicDrawableSpan.ALIGN_BOTTOM] or
     * [DynamicDrawableSpan.ALIGN_BASELINE]
     */
    fun img(drawable: Drawable, verticalAlignment: Int) {
        setSpan(ImageSpan(drawable, verticalAlignment))
    }

    /**
     * Constructs an [ImageSpan] from a drawable and a source with the default
     * alignment [DynamicDrawableSpan.ALIGN_BOTTOM]
     *
     * @param drawable drawable to be rendered
     * @param source   drawable's Uri source
     */
    fun img(drawable: Drawable, source: String) {
        setSpan(ImageSpan(drawable, source))
    }

    /**
     * Constructs an [ImageSpan] from a drawable, a source and a vertical alignment.
     *
     * @param drawable          drawable to be rendered
     * @param source            drawable's uri source
     * @param verticalAlignment one of [DynamicDrawableSpan.ALIGN_BOTTOM] or
     * [DynamicDrawableSpan.ALIGN_BASELINE]
     */
    fun img(
        drawable: Drawable,
        source: String,
        verticalAlignment: Int
    ) {
        setSpan(ImageSpan(drawable, source, verticalAlignment))
    }

    /**
     * Constructs an [ImageSpan] from a [Context] and a [Uri] with the default
     * alignment [DynamicDrawableSpan.ALIGN_BOTTOM]. The Uri source can be retrieved via
     * [.getSource]
     *
     * @param context context used to create a drawable from {@param bitmap} based on the display
     * metrics of the resources
     * @param uri     [Uri] used to construct the drawable that will be rendered
     */
    fun img(context: Context, uri: Uri) {
        setSpan(ImageSpan(context, uri))
    }

    /**
     * Constructs an [ImageSpan] from a [Context], a [Uri] and a vertical
     * alignment. The Uri source can be retrieved via [.getSource]
     *
     * @param context           context used to create a drawable from {@param bitmap} based on
     * the display
     * metrics of the resources
     * @param uri               [Uri] used to construct the drawable that will be rendered.
     * @param verticalAlignment one of [DynamicDrawableSpan.ALIGN_BOTTOM] or
     * [DynamicDrawableSpan.ALIGN_BASELINE]
     */
    fun img(
        context: Context,
        uri: Uri,
        verticalAlignment: Int
    ) {
        setSpan(ImageSpan(context, uri, verticalAlignment))
    }

    /**
     * Constructs an [ImageSpan] from a [Context] and a resource id with the default
     * alignment [DynamicDrawableSpan.ALIGN_BOTTOM]
     *
     * @param context    context used to retrieve the drawable from resources
     * @param resourceId drawable resource id based on which the drawable is retrieved
     */
    fun img(
        context: Context,
        @DrawableRes resourceId: Int
    ) {
        setSpan(ImageSpan(context, resourceId))
    }

    /**
     * Constructs an [ImageSpan] from a [Context], a resource id and a vertical
     * alignment.
     *
     * @param context           context used to retrieve the drawable from resources
     * @param resourceId        drawable resource id based on which the drawable is retrieved.
     * @param verticalAlignment one of [DynamicDrawableSpan.ALIGN_BOTTOM] or
     * [DynamicDrawableSpan.ALIGN_BASELINE]
     */
    fun img(
        context: Context, @DrawableRes resourceId: Int, verticalAlignment: Int
    ) {
        setSpan(ImageSpan(context, resourceId, verticalAlignment))
    }

    fun click(click: (view: View) -> Unit) {
        setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                click.invoke(widget)
            }
        })
    }
}

fun createSpannableString(str: CharSequence): SpannableStringBuilder {
    return SpannableStringBuilder(str)
}

fun SpannableStringBuilder.append(
    string: String,
    init: SpannableContext.() -> Unit
): SpannableStringBuilder {
    val s = SpannableContext(string).apply(init)
    this.append(s.span)
    return this
}

