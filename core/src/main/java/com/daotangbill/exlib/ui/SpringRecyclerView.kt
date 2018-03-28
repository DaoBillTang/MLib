package com.daotangbill.exlib.ui

import android.content.Context
import android.graphics.Canvas
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.TempRecyclerView
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.animation.Animation
import android.view.animation.Interpolator
import android.view.animation.Transformation
import com.daotangbill.exlib.exlib.R

/**
 * A RecyclerView With Spring Effect.
 *
 * @author 郭佳哲
 * https://github.com/gjiazhe
 * Created by gjz on 17/11/2016.
 * @modify bill
 */
class SpringRecyclerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : TempRecyclerView(context, attrs, defStyle) {
    private var mState = STATE_NORMAL
    private var mReleaseBackAnimDuration: Int = 0
    private var mFlingBackAnimDuration: Int = 0

    private val mTouchSlop: Int
    // horizontal or vertical
    private var mOrientation: Int = 0
    // x-coordinate or y-coordinate of last event, base on mOrientation
    private var mLastMotionPos: Float = 0.toFloat()
    private var mFrom: Float = 0.toFloat()
    private var mOffset: Float = 0.toFloat()
    private var mActivePointerId = INVALID_POINTER

    private var mEnableSpringEffectWhenDrag: Boolean = false
    private var mEnableSpringEffectWhenFling: Boolean = false

    private var springAnimation: Animation? = null
    private var releaseBackAnimInterpolator: Interpolator? = null
    private var flingBackAnimInterpolator: Interpolator? = null

    private val isDragged: Boolean
        get() = mState == STATE_DRAG_TOP_OR_LEFT || mState == STATE_DRAG_BOTTOM_OR_RIGHT

    private val isDraggedTopOrLeft: Boolean
        get() = mState == STATE_DRAG_TOP_OR_LEFT

    private val isDraggedBottomOrRight: Boolean
        get() = mState == STATE_DRAG_BOTTOM_OR_RIGHT

    init {
        overScrollMode = View.OVER_SCROLL_ALWAYS

        mTouchSlop = ViewConfiguration.get(context).scaledTouchSlop

        val a = context.obtainStyledAttributes(attrs, R.styleable.SpringRecyclerView)
        mReleaseBackAnimDuration = a.getInt(R.styleable.SpringRecyclerView_srv_releaseBackAnimDuration, DEF_RELEASE_BACK_ANIM_DURATION)
        mFlingBackAnimDuration = a.getInt(R.styleable.SpringRecyclerView_srv_flingBackAnimDuration, DEF_FLING_BACK_ANIM_DURATION)
        mEnableSpringEffectWhenDrag = a.getBoolean(R.styleable.SpringRecyclerView_srv_enableSpringEffectWhenDrag, true)
        mEnableSpringEffectWhenFling = a.getBoolean(R.styleable.SpringRecyclerView_srv_enableSpringEffectWhenFling, true)
        a.recycle()

        initAnimation()
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return if (mEnableSpringEffectWhenDrag && onInterceptTouchEventInternal(ev)) {
            true
        } else super.onInterceptTouchEvent(ev)
    }

    private fun onInterceptTouchEventInternal(ev: MotionEvent): Boolean {
        val action = ev.action
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                mLastMotionPos = if (mOrientation == RecyclerView.VERTICAL) ev.y else ev.x
                mActivePointerId = ev.getPointerId(0)
                // If STATE_SPRING_BACK, we intercept the event and stop the animation.
                // If STATE_FLING, we do not intercept and allow the animation to finish.
                if (mState == STATE_SPRING_BACK) {
                    if (mOffset != 0f) {
                        clearAnimation()
                        setState(if (mOffset > 0) STATE_DRAG_TOP_OR_LEFT else STATE_DRAG_BOTTOM_OR_RIGHT)
                    } else {
                        setState(STATE_NORMAL)
                    }
                    invalidate()
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (mActivePointerId == INVALID_POINTER) {
                    return isDragged
                }
                val pointerIndex = ev.findPointerIndex(mActivePointerId)
                if (pointerIndex == -1) {
                    return isDragged
                }
                val pos = if (mOrientation == RecyclerView.VERTICAL) ev.getY(pointerIndex) else ev.getX(pointerIndex)
                val posDiff = pos - mLastMotionPos
                mLastMotionPos = pos
                if (!isDragged) {
                    val canScrollUpOrLeft: Boolean
                    val canScrollDownOrRight: Boolean
                    val offset = if (mOrientation == RecyclerView.VERTICAL)
                        super.computeVerticalScrollOffset()
                    else
                        super.computeHorizontalScrollOffset()
                    val range = if (mOrientation == RecyclerView.VERTICAL)
                        super.computeVerticalScrollRange() - super.computeVerticalScrollExtent()
                    else
                        super.computeHorizontalScrollRange() - super.computeHorizontalScrollExtent()
                    if (range == 0) {
                        canScrollUpOrLeft = false
                        canScrollDownOrRight = canScrollUpOrLeft
                    } else {
                        canScrollUpOrLeft = offset > 0
                        canScrollDownOrRight = offset < range - 1
                    }
                    if (canScrollUpOrLeft && canScrollDownOrRight) {
                        return isDragged
                    }
                    if (Math.abs(posDiff) > mTouchSlop) {
                        var isOverScroll = false
                        if (!canScrollUpOrLeft && posDiff > 0) {
                            setState(STATE_DRAG_TOP_OR_LEFT)
                            isOverScroll = true
                        } else if (!canScrollDownOrRight && posDiff < 0) {
                            setState(STATE_DRAG_BOTTOM_OR_RIGHT)
                            isOverScroll = true
                        }
                        if (isOverScroll) {
                            // Prevent touch effect on item
                            val fakeCancelEvent = MotionEvent.obtain(ev)
                            fakeCancelEvent.action = MotionEvent.ACTION_CANCEL
                            super.onTouchEvent(fakeCancelEvent)
                            fakeCancelEvent.recycle()
                            super.awakenScrollBars()

                            val parent = parent
                            parent?.requestDisallowInterceptTouchEvent(true)
                        }
                    }
                }
            }

            MotionEvent.ACTION_POINTER_UP -> onSecondaryPointerUp(ev)

            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> mActivePointerId = INVALID_POINTER
            else -> {
            }
        }
        return isDragged
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return if (mEnableSpringEffectWhenDrag && onTouchEventInternal(ev)) {
            true
        } else super.onTouchEvent(ev)
    }

    private fun onTouchEventInternal(ev: MotionEvent): Boolean {
        val action = ev.action

        when (action) {
            MotionEvent.ACTION_DOWN -> {
                mLastMotionPos = if (mOrientation == RecyclerView.VERTICAL) ev.y else ev.x
                mActivePointerId = ev.getPointerId(0)
            }
            MotionEvent.ACTION_MOVE -> {
                if (mActivePointerId == INVALID_POINTER) {
                    return isDragged
                }
                val pointerIndex = ev.findPointerIndex(mActivePointerId)
                if (pointerIndex < 0) {
                    return isDragged
                }
                val pos = if (mOrientation == RecyclerView.VERTICAL) ev.getY(pointerIndex) else ev.getX(pointerIndex)
                val posDiff = pos - mLastMotionPos
                mLastMotionPos = pos
                if (!isDragged) {
                    val canScrollUpOrLeft: Boolean
                    val canScrollDownOrRight: Boolean
                    val offset = if (mOrientation == RecyclerView.VERTICAL)
                        super.computeVerticalScrollOffset()
                    else
                        super.computeHorizontalScrollOffset()
                    val range = if (mOrientation == RecyclerView.VERTICAL)
                        super.computeVerticalScrollRange() - super.computeVerticalScrollExtent()
                    else
                        super.computeHorizontalScrollRange() - super.computeHorizontalScrollExtent()
                    if (range == 0) {
                        canScrollUpOrLeft = false
                        canScrollDownOrRight = canScrollUpOrLeft
                    } else {
                        canScrollUpOrLeft = offset > 0
                        canScrollDownOrRight = offset < range - 1
                    }
                    if (canScrollUpOrLeft && canScrollDownOrRight) {
                        return isDragged
                    }

                    if (Math.abs(posDiff) >= mTouchSlop) {
                        var isOverScroll = false
                        if (!canScrollUpOrLeft && posDiff > 0) {
                            setState(STATE_DRAG_TOP_OR_LEFT)
                            isOverScroll = true
                        } else if (!canScrollDownOrRight && posDiff < 0) {
                            setState(STATE_DRAG_BOTTOM_OR_RIGHT)
                            isOverScroll = true
                        }
                        if (isOverScroll) {
                            // Prevent touch effect on item
                            val fakeCancelEvent = MotionEvent.obtain(ev)
                            fakeCancelEvent.action = MotionEvent.ACTION_CANCEL
                            super.onTouchEvent(fakeCancelEvent)
                            fakeCancelEvent.recycle()
                            super.awakenScrollBars()

                            val parent = parent
                            parent?.requestDisallowInterceptTouchEvent(true)
                        }
                    }
                }
                if (isDragged) {
                    mOffset += posDiff
                    // correct mOffset
                    val b = isDraggedTopOrLeft && mOffset <= 0 || isDraggedBottomOrRight && mOffset >= 0
                    if (b) {
                        setState(STATE_NORMAL)
                        mOffset = 0f
                        // return to touch item
                        val fakeDownEvent = MotionEvent.obtain(ev)
                        fakeDownEvent.action = MotionEvent.ACTION_DOWN
                        super.onTouchEvent(fakeDownEvent)
                        fakeDownEvent.recycle()
                    }
                    invalidate()
                }
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                val index = ev.action
                mLastMotionPos = if (mOrientation == RecyclerView.VERTICAL) ev.getY(index) else ev.getX(index)
                mActivePointerId = ev.getPointerId(index)
            }

            MotionEvent.ACTION_POINTER_UP -> {
                onSecondaryPointerUp(ev)
                val index = ev.findPointerIndex(mActivePointerId)
                if (index != -1) {
                    mLastMotionPos = if (mOrientation == RecyclerView.VERTICAL) ev.getY(index) else ev.getX(index)
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> run {
                if (mOffset != 0f) {
                    // Spring back
                    mFrom = mOffset
                    startReleaseAnimation()
                    setState(STATE_SPRING_BACK)
                }
                mActivePointerId = INVALID_POINTER
            }
            else -> {
            }
        }
        return isDragged
    }

    override fun draw(canvas: Canvas) {
        if (mState == STATE_NORMAL) {
            super.draw(canvas)
        } else {
            val sc = canvas.save()

            // scale the canvas
            if (mOrientation == RecyclerView.VERTICAL) {
                val viewHeight = height
                val scaleY = 1 + Math.abs(mOffset) / viewHeight * 0.3f
                canvas.scale(1f, scaleY, 0f, (if (mOffset >= 0) 0 else viewHeight + scrollY).toFloat())
            } else {
                val viewWidth = width
                val scaleX = 1 + Math.abs(mOffset) / viewWidth * 0.3f
                canvas.scale(scaleX, 1f, (if (mOffset >= 0) 0 else viewWidth + scrollX).toFloat(), 0f)
            }

            super.draw(canvas)
            canvas.restoreToCount(sc)
        }
    }

    override fun absorbGlows(velocityX: Int, velocityY: Int) {
        if (mEnableSpringEffectWhenFling && mState != STATE_FLING) {
            val v = if (mOrientation == RecyclerView.VERTICAL) velocityY else velocityX
            mFrom = -v * (1f / 60)
            startFlingAnimation()
            setState(STATE_FLING)
        } else {
            super.absorbGlows(velocityX, velocityY)
        }
    }

    private fun initAnimation() {
        springAnimation = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                mOffset = mFrom * interpolatedTime
                if (hasEnded()) {
                    mOffset = 0f
                    setState(STATE_NORMAL)
                }
                invalidate()
            }
        }

        releaseBackAnimInterpolator = Interpolator { v -> Math.cos(Math.PI * v / 2).toFloat() }

        flingBackAnimInterpolator = Interpolator { v -> Math.sin(Math.PI * v).toFloat() }
    }

    private fun startReleaseAnimation() {
        springAnimation!!.duration = mReleaseBackAnimDuration.toLong()
        springAnimation!!.interpolator = releaseBackAnimInterpolator
        startAnimation(springAnimation)
    }

    private fun startFlingAnimation() {
        springAnimation!!.duration = mFlingBackAnimDuration.toLong()
        springAnimation!!.interpolator = flingBackAnimInterpolator
        startAnimation(springAnimation)
    }

    private fun setState(newState: Int) {
        if (mState != newState) {
            mState = newState
        }
    }

    private fun onSecondaryPointerUp(ev: MotionEvent) {
        val pointerIndex = ev.actionIndex
        val pointerId = ev.getPointerId(pointerIndex)
        if (pointerId == mActivePointerId) {
            // This was our active pointer going up. Choose a new
            // active pointer and adjust accordingly.
            val newPointerIndex = if (pointerIndex == 0) 1 else 0
            mActivePointerId = ev.getPointerId(newPointerIndex)
        }
    }

    override fun setLayoutManager(layout: RecyclerView.LayoutManager?) {
        super.setLayoutManager(layout)
        if (layout != null) {
            mOrientation = if (layout.canScrollHorizontally()) RecyclerView.HORIZONTAL else RecyclerView.VERTICAL
        }
    }

    fun setEnableSpringEffectWhenDrag(enable: Boolean) {
        mEnableSpringEffectWhenDrag = enable
    }

    fun setEnableSpringEffectWhenFling(enable: Boolean) {
        mEnableSpringEffectWhenFling = enable
    }

    fun setReleaseBackAnimDuration(duration: Int) {
        mReleaseBackAnimDuration = duration
    }

    fun setFlingBackAnimDuration(duration: Int) {
        mFlingBackAnimDuration = duration
    }

    companion object {
        private val STATE_NORMAL = 0
        private val STATE_DRAG_TOP_OR_LEFT = 1
        private val STATE_DRAG_BOTTOM_OR_RIGHT = 2
        private val STATE_SPRING_BACK = 3
        private val STATE_FLING = 4

        // ms
        private val DEF_RELEASE_BACK_ANIM_DURATION = 300
        private val DEF_FLING_BACK_ANIM_DURATION = 300

        private val INVALID_POINTER = -1
    }

}
