package com.daotangbill.exlib.ui

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.LinearLayout
import com.daotangbill.exlib.commons.toast.Tnormal

/**
 * Project hschefu-android
 * Created by BILL on 2017/5/24.
 * email:tangbaozi@daotangbill.uu.me

 * @author BILL
 * *
 * @version 1.0
 */
class InterceptTouchLinearLayout : LinearLayout {

    private var canTouch = true
    private var hintStr: String? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr)

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return if (canTouch) {
            super.onInterceptTouchEvent(ev)
        } else {
            if (hintStr != null && hintStr?.isNotBlank() == true) {
                context.Tnormal(hintStr!!)
            }
            true
        }
    }

    fun setHint(string: String?) {
        this.hintStr = string
    }

    fun changeTouch(boolean: Boolean) {
        this.canTouch = boolean
    }
}