package com.daotangbill.exlib.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.support.annotation.LayoutRes
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.daotangbill.exlib.exlib.R

@Suppress("UNREACHABLE_CODE")
/**
 * View that contains 4 different states: Content, Error, Empty, and Loading.<br></br>
 * Each state has their own separate layout which can be shown/hidden by setting
 * the [MultiStateView.ViewState] accordingly
 * Every MultiStateView ***MUST*** contain a content view. The content view
 * is obtained from whatever is inside of the tags of the view via its XML declaration
 *
 * @author bill
 * 2018.3.14 更新： 加载布局的时机 修改为按需加载。
 *
 */
class MultiStateView : FrameLayout {

    private val mInflater: LayoutInflater by lazy { LayoutInflater.from(context) }

    /**
     * 真是的展示页面
     */
    private var mContentView: View? = null

    /**
     * 加载页面
     */
    private var mLoadingView: View? = null
    private var loadingViewResId: Int = 0
    /**
     * 错误页面
     * 一般用于展示 网络错误
     */
    private var mErrorView: View? = null
    private var errorViewResId: Int = 0

    /**
     * 空页面
     */
    private var mEmptyView: View? = null
    private var emptyViewResId: Int = 0

    private var mAnimateViewChanges = false

    private var mListener: StateListener? = null

    private var mViewState = VIEW_STATE_UNKNOWN

    var viewState: Int
        get() = mViewState
        set(state) {
            if (state != mViewState) {
                val previous = mViewState
                mViewState = state
                setView(previous)
                mListener?.onStateChanged(mViewState)
            }
        }

    @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : super(context, attrs) {
        initView(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        initView(attrs)
    }

    private fun initView(attrs: AttributeSet?) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.MultiStateView)

        loadingViewResId = a.getResourceId(R.styleable.MultiStateView_msv_loadingView, -1)
        emptyViewResId = a.getResourceId(R.styleable.MultiStateView_msv_emptyView, -1)
        errorViewResId = a.getResourceId(R.styleable.MultiStateView_msv_errorView, -1)
        mViewState = a.getInt(R.styleable.MultiStateView_msv_viewState, VIEW_STATE_CONTENT)
        mAnimateViewChanges = a.getBoolean(R.styleable.MultiStateView_msv_animateViewChanges, false)
        a.recycle()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (mContentView == null) {
            throw IllegalArgumentException("Content view is not defined")
        }
        setView(VIEW_STATE_UNKNOWN)
    }

    /* All of the addView methods have been overridden so that it can obtain the content view via XML
     It is NOT recommended to add views into MultiStateView via the addView methods, but rather use
     any of the setViewForState methods to set views for their given ViewState accordingly */
    override fun addView(child: View?) {
        if (isValidContentView(child)) {
            mContentView = child
        }
        super.addView(child)
    }

    override fun addView(child: View, index: Int) {
        if (isValidContentView(child)) {
            mContentView = child
        }
        super.addView(child, index)
    }

    override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams) {
        if (isValidContentView(child)) {
            mContentView = child
        }
        super.addView(child, index, params)
    }

    override fun addView(child: View, params: ViewGroup.LayoutParams) {
        if (isValidContentView(child)) {
            mContentView = child
        }
        super.addView(child, params)
    }

    override fun addView(child: View, width: Int, height: Int) {
        if (isValidContentView(child)) {
            mContentView = child
        }
        super.addView(child, width, height)
    }

    override fun addViewInLayout(child: View, index: Int, params: ViewGroup.LayoutParams): Boolean {
        if (isValidContentView(child)) {
            mContentView = child
        }
        return super.addViewInLayout(child, index, params)
    }

    override fun addViewInLayout(child: View, index: Int, params: ViewGroup.LayoutParams, preventRequestLayout: Boolean): Boolean {
        if (isValidContentView(child)) {
            mContentView = child
        }
        return super.addViewInLayout(child, index, params, preventRequestLayout)
    }

    /**
     *  进行了修改，在调用此方法时 才会初始化某个状态的view
     */
    fun getView(state: Int): View? {
        when (state) {
            VIEW_STATE_LOADING -> {
                if (mLoadingView == null) {
                    mLoadingView = mInflater.inflate(loadingViewResId, this, false)
                    addView(mLoadingView!!, mLoadingView!!.layoutParams)
                }
                return mLoadingView
            }

            VIEW_STATE_CONTENT -> {
                return mContentView
            }

            VIEW_STATE_EMPTY -> {
                if (mEmptyView == null) {
                    mEmptyView = mInflater.inflate(emptyViewResId, this, false)
                    addView(mEmptyView!!, mEmptyView!!.layoutParams)
                }
                return mEmptyView
            }

            VIEW_STATE_ERROR -> {
                if (mErrorView == null) {
                    mErrorView = mInflater.inflate(errorViewResId, this, false)
                    addView(mErrorView!!, mErrorView!!.layoutParams)
                }
                return mErrorView
            }
            else -> return null
        }
    }

    /**
     * Shows the [View] based on the [ViewState]
     */
    private fun setView(previousState: Int) {
        when (mViewState) {
            VIEW_STATE_LOADING -> {
                if (mLoadingView == null && loadingViewResId == 0) {
                    throw NullPointerException("Loading View")
                }
                mContentView?.visibility = View.GONE
                mErrorView?.visibility = View.GONE
                mEmptyView?.visibility = View.GONE

                if (mAnimateViewChanges) {
                    animateLayoutChange(getView(previousState))
                } else {
                    getView(VIEW_STATE_LOADING)?.visibility = View.VISIBLE
                }
            }

            VIEW_STATE_EMPTY -> {
                if (mEmptyView == null && emptyViewResId == 0) {
                    throw NullPointerException("Empty View")
                }
                mLoadingView?.visibility = View.GONE
                mErrorView?.visibility = View.GONE
                mContentView?.visibility = View.GONE

                if (mAnimateViewChanges) {
                    animateLayoutChange(getView(previousState))
                } else {
                    getView(VIEW_STATE_EMPTY)?.visibility = View.VISIBLE
                }
            }

            VIEW_STATE_ERROR -> {
                if (mErrorView == null && errorViewResId == 0) {
                    throw NullPointerException("Error View")
                }

                mLoadingView?.visibility = View.GONE
                mContentView?.visibility = View.GONE
                mEmptyView?.visibility = View.GONE

                if (mAnimateViewChanges) {
                    animateLayoutChange(getView(previousState))
                } else {
                    getView(VIEW_STATE_ERROR)?.visibility = View.VISIBLE
                }
            }

            VIEW_STATE_CONTENT -> {
                if (mContentView == null) {
                    // Should never happen, the view should throw an exception if no content view is present upon creation
                    throw NullPointerException("Content View")
                }
                mLoadingView?.visibility = View.GONE
                mErrorView?.visibility = View.GONE
                mEmptyView?.visibility = View.GONE

                if (mAnimateViewChanges) {
                    animateLayoutChange(getView(previousState))
                } else {
                    mContentView?.visibility = View.VISIBLE
                }
            }
            else -> {
                if (mContentView == null) {
                    throw NullPointerException("Content View")
                }
                mLoadingView?.visibility = View.GONE
                mErrorView?.visibility = View.GONE
                mEmptyView?.visibility = View.GONE
                if (mAnimateViewChanges) {
                    animateLayoutChange(getView(previousState))
                } else {
                    mContentView?.visibility = View.VISIBLE
                }
            }
        }
    }

    /**
     * Checks if the given [View] is valid for the Content View
     *
     * @param view The [View] to check
     * @return
     */
    private fun isValidContentView(view: View?): Boolean {
        return if (mContentView != null && mContentView != view) {
            false
        } else view != mLoadingView && view != mErrorView && view != mEmptyView
    }

    /**
     * Sets the view for the given view state
     *
     * @param view          The [View] to use
     * @param state         The [ViewState]to set
     * @param switchToState If the [ViewState] should be switched to
     */
    @JvmOverloads
    fun setViewForState(view: View, state: Int, switchToState: Boolean = false) {
        when (state) {
            VIEW_STATE_LOADING -> {
                if (mLoadingView != null) {
                    removeView(mLoadingView)
                }
                mLoadingView = view
                addView(mLoadingView)
            }

            VIEW_STATE_EMPTY -> {
                if (mEmptyView != null) {
                    removeView(mEmptyView)
                }
                mEmptyView = view
                addView(mEmptyView)
            }

            VIEW_STATE_ERROR -> {
                if (mErrorView != null) {
                    removeView(mErrorView)
                }
                mErrorView = view
                addView(mErrorView)
            }

            VIEW_STATE_CONTENT -> {
                if (mContentView != null) {
                    removeView(mContentView)
                }
                mContentView = view
                addView(mContentView)
            }
            else -> {
            }
        }

        setView(VIEW_STATE_UNKNOWN)
        if (switchToState) {
            viewState = state
        }
    }

    /**
     * Sets the [View] for the given [ViewState]
     *
     * @param layoutRes     Layout resource id
     * @param state         The [ViewState] to set
     * @param switchToState If the [ViewState] should be switched to
     */
    @JvmOverloads
    fun setViewForState(@LayoutRes layoutRes: Int, state: Int, switchToState: Boolean = false) {
        val view = mInflater.inflate(layoutRes, this, false)
        setViewForState(view, state, switchToState)
    }

    /**
     * Sets whether an animate will occur when changing between [ViewState]
     *
     * @param animate
     */
    fun setAnimateLayoutChanges(animate: Boolean) {
        mAnimateViewChanges = animate
    }

    /**
     * Sets the [StateListener] for the view
     *
     * @param listener The [StateListener] that will receive callbacks
     */
    fun setStateListener(listener: StateListener) {
        mListener = listener
    }

    /**
     * Animates the layout changes between [ViewState]
     *
     * @param previousView The view that it was currently on
     */
    private fun animateLayoutChange(previousView: View?) {
        if (previousView == null) {
            getView(mViewState)?.visibility = View.VISIBLE
            return
        }

        previousView.visibility = View.VISIBLE
        val anim = ObjectAnimator.ofFloat(previousView, "alpha", 1.0f, 0.0f).setDuration(250L)
        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                previousView.visibility = View.GONE
                getView(mViewState)?.visibility = View.VISIBLE
                ObjectAnimator.ofFloat(getView(mViewState), "alpha", 0.0f, 1.0f).setDuration(250L).start()
            }
        })
        anim.start()
    }

    interface StateListener {
        /**
         * Callback for when the [ViewState] has changed
         *
         * @param viewState The [ViewState] that was switched to
         */
        fun onStateChanged(viewState: Int)
    }
    /**
     * Sets the [View] for the given [ViewState]
     *
     * @param view  The [View] to use
     * @param state The [ViewState] to set
     */
    /**
     * Sets the [View] for the given [ViewState]
     *
     * @param layoutRes Layout resource id
     * @param state     The [View] state to set
     */
    companion object {

        val VIEW_STATE_UNKNOWN = -1

        val VIEW_STATE_CONTENT = 0

        val VIEW_STATE_ERROR = 1

        val VIEW_STATE_EMPTY = 2

        val VIEW_STATE_LOADING = 3
    }
}
