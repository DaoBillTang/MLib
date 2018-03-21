package com.daotangbill.exlib.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import com.daotangbill.exlib.exlib.R
import com.daotangbill.exlib.commons.utils.sp2px

/**
 * Created by Bill on 2016/9/7 14:42.
 * emal:1750352866@qq.com
 * 侧边
 */
class SideBar : View {
    // 触摸事件
    private var choose = -1// 选中
    private val paint = Paint()

    private var mTextDialog: TextView? = null

    fun setTextView(mTextDialog: TextView) {
        this.mTextDialog = mTextDialog
    }

    // 26个字母
    var b: Array<CharSequence> = arrayOf("全", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
            "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z")

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val t = context.obtainStyledAttributes(attrs, R.styleable.SideBar)
        val textArray = t.getTextArray(R.styleable.SideBar_side_array)
        if (textArray != null) {
            b = textArray
        }
        t.recycle()
    }

    constructor(context: Context) : super(context)

    /**
     * 重写这个方法
     */
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 获取焦点改变背景颜色.
        val height = height// 获取对应高度
        val width = width // 获取对应宽度
        val singleHeight = height / b.size// 获取每一个字母的高度

        for (i in b.indices) {
            paint.color = Color.rgb(33, 65, 98)
            // paint.setColor(Color.WHITE);
            paint.typeface = Typeface.DEFAULT_BOLD
            paint.isAntiAlias = true
            paint.textSize = sp2px(11f).toFloat()
            // 选中的状态
            if (i == choose) {
                paint.color = Color.parseColor("#a6a6b0")
                paint.isFakeBoldText = true
            }
            // x坐标等于中间-字符串宽度的一半.
            val xPos = width / 2 - paint.measureText(b[i].toString()) / 2
            val yPos = (singleHeight * i + singleHeight).toFloat()
            canvas.drawText(b[i].toString(), xPos, yPos, paint)
            paint.reset()// 重置画笔
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        val action = event.action
        val y = event.y// 点击y坐标
        val oldChoose = choose
        val c = (y / height * b.size).toInt()// 点击y坐标所占总高度的比例*b数组的长度就等于点击b中的个数.

        when (action) {
            MotionEvent.ACTION_UP -> {
                background = ColorDrawable(0x00000000)
                choose = -1//
                invalidate()
                if (mTextDialog != null) {
                    mTextDialog!!.visibility = View.INVISIBLE
                }
            }

            else -> {
                setBackgroundResource(R.drawable.sidebar_background)
                if (oldChoose != c) {
                    if (c >= 0 && c < b.size) {
                        listener?.invoke(b[c].toString())
                        if (mTextDialog != null) {
                            mTextDialog!!.text = b[c]
                            mTextDialog!!.visibility = View.VISIBLE
                        }
                        choose = c
                        invalidate()
                    }
                }
            }
        }
        return true
    }

    /**
     * onTouchingLetterChangedListener
     */
    var listener: ((s: String) -> Unit)? = null

    /**
     * 向外公开的方法
     */
    fun setOnTouchingLetterChangedListener(listener: ((s: String) -> Unit)) {
        this.listener = listener
    }
}