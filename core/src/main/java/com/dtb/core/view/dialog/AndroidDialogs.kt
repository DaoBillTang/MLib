@file:Suppress("NOTHING_TO_INLINE", "unused")

package com.dtb.core.view.dialog

import android.app.ProgressDialog
import android.content.Context
import androidx.fragment.app.Fragment


@Deprecated(message = "Android progress dialogs are deprecated")
inline fun Fragment.progressDialog(
    message: Int? = null,
    title: Int? = null,
    noinline init: (ProgressDialog.() -> Unit)? = null
) = activity?.progressDialog(message, title, init)

@Deprecated(message = "Android progress dialogs are deprecated")
fun Context.progressDialog(
    message: Int? = null,
    title: Int? = null,
    init: (ProgressDialog.() -> Unit)? = null
) = progressDialog(false, message?.let { getString(it) }, title?.let { getString(it) }, init)


@Deprecated(message = "Android progress dialogs are deprecated")
inline fun Fragment.indeterminateProgressDialog(
    message: Int? = null,
    title: Int? = null,
    noinline init: (ProgressDialog.() -> Unit)? = null
) = activity?.indeterminateProgressDialog(message, title, init)

@Deprecated(message = "Android progress dialogs are deprecated")
fun Context.indeterminateProgressDialog(
    message: Int? = null,
    title: Int? = null,
    init: (ProgressDialog.() -> Unit)? = null
) = progressDialog(true, message?.let { getString(it) }, title?.let { getString(it) }, init)


@Deprecated(message = "Android progress dialogs are deprecated")
inline fun Fragment.progressDialog(
    message: CharSequence? = null,
    title: CharSequence? = null,
    noinline init: (ProgressDialog.() -> Unit)? = null
) = activity?.progressDialog(message, title, init)

@Deprecated(message = "Android progress dialogs are deprecated")
fun Context.progressDialog(
    message: CharSequence? = null,
    title: CharSequence? = null,
    init: (ProgressDialog.() -> Unit)? = null
) = progressDialog(false, message, title, init)


@Deprecated(message = "Android progress dialogs are deprecated")
inline fun Fragment.indeterminateProgressDialog(
    message: CharSequence? = null,
    title: CharSequence? = null,
    noinline init: (ProgressDialog.() -> Unit)? = null
) = activity?.indeterminateProgressDialog(message, title, init)

@Deprecated(message = "Android progress dialogs are deprecated")
fun Context.indeterminateProgressDialog(
    message: CharSequence? = null,
    title: CharSequence? = null,
    init: (ProgressDialog.() -> Unit)? = null
) = progressDialog(true, message, title, init)


@Deprecated(message = "Android progress dialogs are deprecated")
private fun Context.progressDialog(
    indeterminate: Boolean,
    message: CharSequence? = null,
    title: CharSequence? = null,
    init: (ProgressDialog.() -> Unit)? = null
) = ProgressDialog(this).apply {
    isIndeterminate = indeterminate
    if (!indeterminate) setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
    if (message != null) setMessage(message)
    if (title != null) setTitle(title)
    if (init != null) init()
    show()
}
