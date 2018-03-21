package com.daotangbill.exlib.ui.suppertext

import android.content.Context
import android.text.InputFilter
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView

/**
 * 基础TextView
 */
class BaseTextView @JvmOverloads constructor(mContext: Context,
                                             attrs: AttributeSet? = null,
                                             defStyleAttr: Int = 0)
    : LinearLayout(mContext, attrs, defStyleAttr) {

    private var topInit = false

    val topTextView: TextView  by lazy {
        val textView = TextView(context)
        textView.layoutParams = topTVParams
        textView.gravity = Gravity.CENTER
        addView(textView, 0)
        topInit = true
        return@lazy textView
    }

    val centerTextView: TextView by lazy {
        val textView = TextView(context)
        textView.layoutParams = centerTVParams
        textView.gravity = Gravity.CENTER
        if (topInit) {
            addView(textView, 1)
        } else {
            addView(textView, 0)
        }
        return@lazy textView
    }
    val bottomTextView: TextView by lazy {
        val textView = TextView(context)
        textView.layoutParams = bottomTVParams
        textView.gravity = Gravity.CENTER
        addView(textView)
        return@lazy textView
    }
    /**
     * TODO: 2017/7/21 问题记录 ：
     * 之前设置 MATCH_PARENT导致每次重新设置string的时候，
     * textView的宽度都已第一次为准，在列表中使用的时候服用出现混乱，
     * 特此记录一下，以后处理好布局之间套用时候设置WRAP_CONTENT和MATCH_PARENT出现问题
     */
    private val topTVParams: LinearLayout.LayoutParams by lazy {
        LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
    }

    private val centerTVParams: LinearLayout.LayoutParams by lazy {
        LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
    }

    private val bottomTVParams: LinearLayout.LayoutParams by lazy {
        LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
    }

    init {
        this.orientation = LinearLayout.VERTICAL
    }

    private fun setTextString(textView: TextView?, textString: CharSequence) {
        textView?.text = textString
        if (!TextUtils.isEmpty(textString)) {
            textView?.visibility = View.VISIBLE
        }
    }

    fun setTopTextString(s: CharSequence) {
        topTextView.text = s
        setTextString(topTextView, s)
    }

    fun setCenterTextString(s: CharSequence) {
        setTextString(centerTextView, s)
    }

    fun setBottomTextString(s: CharSequence) {
        setTextString(bottomTextView, s)
    }

    fun setMaxEms(topMaxEms: Int, centerMaxEms: Int, bottomMaxEms: Int) {
        topTextView.ellipsize = TextUtils.TruncateAt.END
        centerTextView.ellipsize = TextUtils.TruncateAt.END
        bottomTextView.ellipsize = TextUtils.TruncateAt.END

        topTextView.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(topMaxEms))
        centerTextView.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(centerMaxEms))
        bottomTextView.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(bottomMaxEms))
    }

    fun setCenterSpaceHeight(centerSpaceHeight: Int) {
        topTVParams.setMargins(0, 0, 0, centerSpaceHeight / 2)
        centerTVParams.setMargins(0, centerSpaceHeight / 2, 0, centerSpaceHeight / 2)
        bottomTVParams.setMargins(0, centerSpaceHeight / 2, 0, 0)
    }
}