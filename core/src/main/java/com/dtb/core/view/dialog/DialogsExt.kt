@file:Suppress("NOTHING_TO_INLINE", "unused")

package com.dtb.core.view.dialog

import android.content.Context
import android.content.DialogInterface
import androidx.fragment.app.Fragment

typealias AlertBuilderFactory<D> = (Context) -> AlertBuilder<D>

//alert
fun <D : DialogInterface> Fragment.alert(
    factory: AlertBuilderFactory<D>,
    message: String,
    title: String? = null,
    init: (AlertBuilder<D>.() -> Unit)? = null
) = activity?.alert(factory, message, title, init)

fun <D : DialogInterface> Context.alert(
    factory: AlertBuilderFactory<D>,
    message: String,
    title: String? = null,
    init: (AlertBuilder<D>.() -> Unit)? = null
): AlertBuilder<D> {
    return factory(this).apply {
        if (title != null) {
            this.title = title
        }
        this.message = message
        if (init != null) init()
    }
}

fun Fragment.alertCompat(
    message: String,
    title: String? = null,
    init: (AppcompatAlertBuilder.() -> Unit)? = null
): AppcompatAlertBuilder? {
    return activity?.alertCompat(message, title, init)
}

fun Context.alertCompat(
    message: String,
    title: String? = null,
    init: (AppcompatAlertBuilder.() -> Unit)? = null
): AppcompatAlertBuilder {
    return AppcompatAlertBuilder(this).apply {
        if (title != null) {
            this.title = title
        }
        this.message = message
        if (init != null) init()
    }
}
//selector


inline fun Fragment.selector(
    title: CharSequence? = null,
    items: List<CharSequence>,
    noinline onClick: (DialogInterface, Int) -> Unit
) = activity?.selector(title, items, onClick)

fun Context.selector(
    title: CharSequence? = null,
    items: List<CharSequence>,
    onClick: (DialogInterface, Int) -> Unit
) {
    with(AndroidAlertBuilder(this)) {
        if (title != null) {
            this.title = title
        }
        items(items, onClick)
        show()
    }
}
