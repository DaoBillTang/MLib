package com.dtb.utils.commons.view

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.support.annotation.IdRes
import android.support.annotation.StringRes
import android.support.v7.widget.RecyclerView
import android.text.util.Linkify
import android.util.SparseArray
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.*
import java.lang.ref.SoftReference

/**
 * project com.daotangbill.dt_ext.exlib.commons.view
 * Created by Bill on 2017/10/10.
 * emal: tangbakzi@daotangbill.uu.me
 *
 * @author: Bill
 * @version: 1.0
 * @description:
 */
open class BaseRecyleHolder(view: View) : RecyclerView.ViewHolder(view) {

    /**
     * Views indexed with their IDs
     */
    private val views: SparseArray<SoftReference<View>> by lazy {
        SparseArray<SoftReference<View>>()
    }

    /**
     * Will set the text of a TextView.
     *
     * @param viewId The view id.
     * @param value  The text to put in the text view.
     * @return The BaseRecyleHolder for chaining.
     */
    fun setText(@IdRes viewId: Int, value: CharSequence?): BaseRecyleHolder {
        val view = getView<TextView>(viewId)
        view?.text = value
        return this
    }

    fun setText(@IdRes viewId: Int, @StringRes strId: Int): BaseRecyleHolder {
        val view = getView<TextView>(viewId)
        view?.setText(strId)
        return this
    }

    /**
     * Will set the image of an ImageView from a resource id.
     *
     * @param viewId     The view id.
     * @param imageResId The image resource id.
     * @return The BaseRecyleHolder for chaining.
     */
    fun setImageResource(@IdRes viewId: Int, @DrawableRes imageResId: Int): BaseRecyleHolder {
        val view = getView<ImageView>(viewId)
        view?.setImageResource(imageResId)
        return this
    }

    /**
     * Will set background color of a view.
     *
     * @param viewId The view id.
     * @param color  A color, not a resource id.
     * @return The BaseRecyleHolder for chaining.
     */
    fun setBackgroundColor(@IdRes viewId: Int, @ColorInt color: Int): BaseRecyleHolder {
        val view = getView<View>(viewId)
        view?.setBackgroundColor(color)
        return this
    }

    /**
     * Will set background of a view.
     *
     * @param viewId        The view id.
     * @param backgroundRes A resource to use as a background.
     * @return The BaseRecyleHolder for chaining.
     */
    fun setBackgroundRes(@IdRes viewId: Int, @DrawableRes backgroundRes: Int): BaseRecyleHolder {
        val view = getView<View>(viewId)
        view?.setBackgroundResource(backgroundRes)
        return this
    }

    /**
     * Will set text color of a TextView.
     *
     * @param viewId    The view id.
     * @param textColor The text color (not a resource id).
     * @return The BaseRecyleHolder for chaining.
     */
    fun setTextColor(@IdRes viewId: Int, @ColorInt textColor: Int): BaseRecyleHolder {
        val view = getView<TextView>(viewId)
        view?.setTextColor(textColor)
        return this
    }

    /**
     * Will set the image of an ImageView from a drawable.
     *
     * @param viewId   The view id.
     * @param drawable The image drawable.
     * @return The BaseRecyleHolder for chaining.
     */
    fun setImageDrawable(@IdRes viewId: Int, drawable: Drawable?): BaseRecyleHolder {
        val view = getView<ImageView>(viewId)
        view?.setImageDrawable(drawable)
        return this
    }

    /**
     * Add an action to set the image of an image view. Can be called multiple times.
     */
    fun setImageBitmap(@IdRes viewId: Int, bitmap: Bitmap?): BaseRecyleHolder {
        val view = getView<ImageView>(viewId)
        view?.setImageBitmap(bitmap)
        return this
    }

    /**
     * Add an action to set the alpha of a view. Can be called multiple times.
     * Alpha between 0-1.
     */
    fun setAlpha(@IdRes viewId: Int, value: Float): BaseRecyleHolder {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getView<View>(viewId)?.alpha = value
        } else {
            // Pre-honeycomb hack to set Alpha value
            val alpha = AlphaAnimation(value, value)
            alpha.duration = 0
            alpha.fillAfter = true
            getView<View>(viewId)?.startAnimation(alpha)
        }
        return this
    }

    /**
     * Set a view visibility to VISIBLE (true) or GONE (false).
     *
     * @param viewId  The view id.
     * @param visible True for VISIBLE, false for GONE.
     * @return The BaseRecyleHolder for chaining.
     */
    fun setVisible(@IdRes viewId: Int, visible: Boolean): BaseRecyleHolder {
        val view = getView<View>(viewId)
        view?.visibility = if (visible) View.VISIBLE else View.GONE
        return this
    }

    /**
     * Add links into a TextView.
     *
     * @param viewId The id of the TextView to linkify.
     * @return The BaseRecyleHolder for chaining.
     */
    fun linkify(@IdRes viewId: Int): BaseRecyleHolder {
        val view = getView<TextView>(viewId)
        Linkify.addLinks(view, Linkify.ALL)
        return this
    }

    /**
     * Apply the typeface to the given viewId, and enable subpixel rendering.
     */
//    fun setTypeface(@IdRes viewId: Int, typeface: Typeface): BaseRecyleHolder {
//        val view = getView<TextView>(viewId)
//        view?.typeface = typeface
//        view?.paintFlags = view?.paintFlags or Paint.SUBPIXEL_TEXT_FLAG
//        return this
//    }

    /**
     * Apply the typeface to all the given viewIds, and enable subpixel rendering.
     */
//    fun setTypeface(typeface: Typeface, vararg viewIds: Int): BaseRecyleHolder {
//        for (viewId in viewIds) {
//            val view = getView<TextView>(viewId)
//            view?.typeface = typeface
//            view?.paintFlags = view?.paintFlags or Paint.SUBPIXEL_TEXT_FLAG
//        }
//        return this
//    }

    /**
     * Sets the progress of a ProgressBar.
     *
     * @param viewId   The view id.
     * @param progress The progress.
     * @return The BaseRecyleHolder for chaining.
     */
    fun setProgress(@IdRes viewId: Int, progress: Int): BaseRecyleHolder {
        val view = getView<ProgressBar>(viewId)
        view?.progress = progress
        return this
    }

    /**
     * Sets the progress and max of a ProgressBar.
     *
     * @param viewId   The view id.
     * @param progress The progress.
     * @param max      The max value of a ProgressBar.
     * @return The BaseRecyleHolder for chaining.
     */
    fun setProgress(@IdRes viewId: Int, progress: Int, max: Int = 100): BaseRecyleHolder {
        val view = getView<ProgressBar>(viewId)
        view?.max = max
        view?.progress = progress
        return this
    }

    /**
     * Sets the range of a ProgressBar to 0...max.
     *
     * @param viewId The view id.
     * @param max    The max value of a ProgressBar.
     * @return The BaseRecyleHolder for chaining.
     */
    fun setMax(@IdRes viewId: Int, max: Int): BaseRecyleHolder {
        val view = getView<ProgressBar>(viewId)
        view?.max = max
        return this
    }

    /**
     * Sets the rating (the number of stars filled) of a RatingBar.
     *
     * @param viewId The view id.
     * @param rating The rating.
     * @return The BaseRecyleHolder for chaining.
     */
    fun setRating(@IdRes viewId: Int, rating: Float): BaseRecyleHolder {
        val view = getView<RatingBar>(viewId)
        view?.rating = rating
        return this
    }

    /**
     * Sets the rating (the number of stars filled) and max of a RatingBar.
     *
     * @param viewId The view id.
     * @param rating The rating.
     * @param max    The range of the RatingBar to 0...max.
     * @return The BaseRecyleHolder for chaining.
     */
    fun setRating(@IdRes viewId: Int, rating: Float, max: Int): BaseRecyleHolder {
        val view = getView<RatingBar>(viewId)
        view?.max = max
        view?.rating = rating
        return this
    }

    /**
     * Sets the on click listener of the view.
     *
     * @param viewId   The view id.
     * @param listener The on click listener;
     * @return The BaseRecyleHolder for chaining.
     */
    fun setOnClickListener(@IdRes viewId: Int, listener: (() -> Unit)?): BaseRecyleHolder {
        val view = getView<View>(viewId)
        view?.setOnClickListener {
            listener?.invoke()
        }
        return this
    }

    /**
     * Sets the on touch listener of the view.
     *
     * @param viewId   The view id.
     * @param listener The on touch listener;
     * @return The BaseRecyleHolder for chaining.
     */
    fun setOnTouchListener(@IdRes viewId: Int, listener: View.OnTouchListener): BaseRecyleHolder {
        val view = getView<View>(viewId)
        view?.setOnTouchListener(listener)
        return this
    }

    /**
     * Sets the on checked change listener of the view.
     *
     * @param viewId   The view id.
     * @param listener The checked change listener of compound button.
     * @return The BaseRecyleHolder for chaining.
     */
    fun setOnCheckedChangeListener(@IdRes viewId: Int,
                                   listener: CompoundButton.OnCheckedChangeListener): BaseRecyleHolder {
        val view = getView<CompoundButton>(viewId)
        view?.setOnCheckedChangeListener(listener)
        return this
    }

    /**
     * Sets the tag of the view.
     *
     * @param viewId The view id.
     * @param tag    The tag;
     * @return The BaseRecyleHolder for chaining.
     */
    fun setTag(@IdRes viewId: Int, tag: Any): BaseRecyleHolder {
        val view = getView<View>(viewId)
        view?.tag = tag
        return this
    }

    /**
     * Sets the tag of the view.
     *
     * @param viewId The view id.
     * @param key    The key of tag;
     * @param tag    The tag;
     * @return The BaseRecyleHolder for chaining.
     */
    fun setTag(@IdRes viewId: Int, key: Int, tag: Any): BaseRecyleHolder {
        val view = getView<View>(viewId)
        view?.setTag(key, tag)
        return this
    }

    /**
     * Sets the checked status of a checkable.
     *
     * @param viewId  The view id.
     * @param checked The checked status;
     * @return The BaseRecyleHolder for chaining.
     */
    fun setChecked(@IdRes viewId: Int, checked: Boolean): BaseRecyleHolder {
        val view = getView<View>(viewId)
        // View unable cast to Checkable
        if (view is Checkable) {
            (view as Checkable).isChecked = checked
        }
        return this
    }

    fun <T : View> getView(@IdRes viewId: Int): T? {
        var view: View? = views.get(viewId)?.get()
        if (view == null) {
            view = itemView.findViewById(viewId)
            views.put(viewId, SoftReference(view))
        }
        return view as T?
    }
}