package com.daotangbill.exlib.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.daotangbill.exlib.exlib.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * View that contains 4 different states: Content, Error, Empty, and Loading.<br>
 * Each state has their own separate layout which can be shown/hidden by setting
 * the {@link MultiStateView.ViewState} accordingly
 * Every MultiStateView <b><i>MUST</i></b> contain a content view. The content view
 * is obtained from whatever is inside of the tags of the view via its XML declaration
 *
 * @author bill
 *         暂时放在这里用于 找bug
 */
public class MultiStateView extends FrameLayout {

    public static final int VIEW_STATE_UNKNOWN = -1;

    public static final int VIEW_STATE_CONTENT = 0;

    public static final int VIEW_STATE_ERROR = 1;

    public static final int VIEW_STATE_EMPTY = 2;

    public static final int VIEW_STATE_LOADING = 3;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({VIEW_STATE_UNKNOWN, VIEW_STATE_CONTENT, VIEW_STATE_ERROR, VIEW_STATE_EMPTY, VIEW_STATE_LOADING})
    public @interface ViewState {
    }

    private LayoutInflater mInflater;

    /**
     * 真是的展示页面
     */
    private View mContentView;

    /**
     * 加载页面
     */
    private View mLoadingView;

    /**
     * 错误页面
     * 一般用于展示 网络错误
     */
    private View mErrorView;

    /**
     * 空页面
     */
    private View mEmptyView;

    private boolean mAnimateViewChanges = false;

    @Nullable
    private StateListener mListener;

    @ViewState
    private int mViewState = VIEW_STATE_UNKNOWN;

    public MultiStateView(Context context) {
        this(context, null);
    }

    public MultiStateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MultiStateView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        mInflater = LayoutInflater.from(getContext());
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MultiStateView);

        int loadingViewResId = a.getResourceId(R.styleable.MultiStateView_msv_loadingView, -1);
        if (loadingViewResId > -1) {
            mLoadingView = mInflater.inflate(loadingViewResId, this, false);
            addView(mLoadingView, mLoadingView.getLayoutParams());
        }

        int emptyViewResId = a.getResourceId(R.styleable.MultiStateView_msv_emptyView, -1);
        if (emptyViewResId > -1) {
            mEmptyView = mInflater.inflate(emptyViewResId, this, false);
            addView(mEmptyView, mEmptyView.getLayoutParams());
        }

        int errorViewResId = a.getResourceId(R.styleable.MultiStateView_msv_errorView, -1);
        if (errorViewResId > -1) {
            mErrorView = mInflater.inflate(errorViewResId, this, false);
            addView(mErrorView, mErrorView.getLayoutParams());
        }

        int viewState = a.getInt(R.styleable.MultiStateView_msv_viewState, VIEW_STATE_CONTENT);
        mAnimateViewChanges = a.getBoolean(R.styleable.MultiStateView_msv_animateViewChanges, false);

        switch (viewState) {
            case VIEW_STATE_CONTENT:
                mViewState = VIEW_STATE_CONTENT;
                break;

            case VIEW_STATE_ERROR:
                mViewState = VIEW_STATE_ERROR;
                break;

            case VIEW_STATE_EMPTY:
                mViewState = VIEW_STATE_EMPTY;
                break;

            case VIEW_STATE_LOADING:
                mViewState = VIEW_STATE_LOADING;
                break;

            case VIEW_STATE_UNKNOWN:
            default:
                mViewState = VIEW_STATE_UNKNOWN;
                break;
        }

        a.recycle();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mContentView == null) {
            throw new IllegalArgumentException("Content view is not defined");
        }
        setView(VIEW_STATE_UNKNOWN);
    }

    /* All of the addView methods have been overridden so that it can obtain the content view via XML
     It is NOT recommended to add views into MultiStateView via the addView methods, but rather use
     any of the setViewForState methods to set views for their given ViewState accordingly */
    @Override
    public void addView(View child) {
        if (isValidContentView(child)) {
            mContentView = child;
        }
        super.addView(child);
    }

    @Override
    public void addView(View child, int index) {
        if (isValidContentView(child)) {
            mContentView = child;
        }
        super.addView(child, index);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (isValidContentView(child)) {
            mContentView = child;
        }
        super.addView(child, index, params);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        if (isValidContentView(child)) {
            mContentView = child;
        }
        super.addView(child, params);
    }

    @Override
    public void addView(View child, int width, int height) {
        if (isValidContentView(child)) {
            mContentView = child;
        }
        super.addView(child, width, height);
    }

    @Override
    protected boolean addViewInLayout(View child, int index, ViewGroup.LayoutParams params) {
        if (isValidContentView(child)) {
            mContentView = child;
        }
        return super.addViewInLayout(child, index, params);
    }

    @Override
    protected boolean addViewInLayout(View child, int index, ViewGroup.LayoutParams params, boolean preventRequestLayout) {
        if (isValidContentView(child)) {
            mContentView = child;
        }
        return super.addViewInLayout(child, index, params, preventRequestLayout);
    }

    /**
     * Returns the {@link View} associated with the {@link ViewState}
     *
     * @param state The {@link ViewState} with to return the view for
     * @return The {@link View} associated with the {@link ViewState}, null if no view is present
     */
    @Nullable
    public View getView(@ViewState int state) {
        switch (state) {
            case VIEW_STATE_LOADING:
                return mLoadingView;

            case VIEW_STATE_CONTENT:
                return mContentView;

            case VIEW_STATE_EMPTY:
                return mEmptyView;

            case VIEW_STATE_ERROR:
                return mErrorView;

            default:
                return null;
        }
    }

    /**
     * Returns the current {@link ViewState}
     *
     * @return
     */
    @ViewState
    public int getViewState() {
        return mViewState;
    }

    /**
     * Sets the current {@link ViewState}
     *
     * @param state The {@link ViewState} to set {@link MultiStateView} to
     */
    public void setViewState(@ViewState int state) {
        if (state != mViewState) {
            int previous = mViewState;
            mViewState = state;
            setView(previous);
            if (mListener != null) {
                mListener.onStateChanged(mViewState);
            }
        }
    }

    /**
     * Shows the {@link View} based on the {@link ViewState}
     */
    private void setView(@ViewState int previousState) {
        switch (mViewState) {
            case VIEW_STATE_LOADING:
                if (mLoadingView == null) {
                    throw new NullPointerException("Loading View");
                }

                if (mContentView != null) {
                    mContentView.setVisibility(View.GONE);
                }
                if (mErrorView != null) {
                    mErrorView.setVisibility(View.GONE);
                }
                if (mEmptyView != null) {
                    mEmptyView.setVisibility(View.GONE);
                }

                if (mAnimateViewChanges) {
                    animateLayoutChange(getView(previousState));
                } else {
                    mLoadingView.setVisibility(View.VISIBLE);
                }
                break;

            case VIEW_STATE_EMPTY:
                if (mEmptyView == null) {
                    throw new NullPointerException("Empty View");
                }


                if (mLoadingView != null) {
                    mLoadingView.setVisibility(View.GONE);
                }
                if (mErrorView != null) {
                    mErrorView.setVisibility(View.GONE);
                }
                if (mContentView != null) {
                    mContentView.setVisibility(View.GONE);
                }

                if (mAnimateViewChanges) {
                    animateLayoutChange(getView(previousState));
                } else {
                    mEmptyView.setVisibility(View.VISIBLE);
                }
                break;

            case VIEW_STATE_ERROR:
                if (mErrorView == null) {
                    throw new NullPointerException("Error View");
                }


                if (mLoadingView != null) {
                    mLoadingView.setVisibility(View.GONE);
                }
                if (mContentView != null) {
                    mContentView.setVisibility(View.GONE);
                }
                if (mEmptyView != null) {
                    mEmptyView.setVisibility(View.GONE);
                }

                if (mAnimateViewChanges) {
                    animateLayoutChange(getView(previousState));
                } else {
                    mErrorView.setVisibility(View.VISIBLE);
                }
                break;

            case VIEW_STATE_CONTENT:
            default:
                if (mContentView == null) {
                    // Should never happen, the view should throw an exception if no content view is present upon creation
                    throw new NullPointerException("Content View");
                }


                if (mLoadingView != null) {
                    mLoadingView.setVisibility(View.GONE);
                }
                if (mErrorView != null) {
                    mErrorView.setVisibility(View.GONE);
                }
                if (mEmptyView != null) {
                    mEmptyView.setVisibility(View.GONE);
                }

                if (mAnimateViewChanges) {
                    animateLayoutChange(getView(previousState));
                } else {
                    mContentView.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    /**
     * Checks if the given {@link View} is valid for the Content View
     *
     * @param view The {@link View} to check
     * @return
     */
    private boolean isValidContentView(View view) {
        if (mContentView != null && mContentView != view) {
            return false;
        }

        return view != mLoadingView && view != mErrorView && view != mEmptyView;
    }

    /**
     * Sets the view for the given view state
     *
     * @param view          The {@link View} to use
     * @param state         The {@link ViewState}to set
     * @param switchToState If the {@link ViewState} should be switched to
     */
    public void setViewForState(View view, @ViewState int state, boolean switchToState) {
        switch (state) {
            case VIEW_STATE_LOADING:
                if (mLoadingView != null) {
                    removeView(mLoadingView);
                }
                mLoadingView = view;
                addView(mLoadingView);
                break;

            case VIEW_STATE_EMPTY:
                if (mEmptyView != null) {
                    removeView(mEmptyView);
                }
                mEmptyView = view;
                addView(mEmptyView);
                break;

            case VIEW_STATE_ERROR:
                if (mErrorView != null) {
                    removeView(mErrorView);
                }
                mErrorView = view;
                addView(mErrorView);
                break;

            case VIEW_STATE_CONTENT:
                if (mContentView != null) {
                    removeView(mContentView);
                }
                mContentView = view;
                addView(mContentView);
                break;
            default:
                break;
        }

        setView(VIEW_STATE_UNKNOWN);
        if (switchToState) {
            setViewState(state);
        }
    }

    /**
     * Sets the {@link View} for the given {@link ViewState}
     *
     * @param view  The {@link View} to use
     * @param state The {@link ViewState} to set
     */
    public void setViewForState(View view, @ViewState int state) {
        setViewForState(view, state, false);
    }

    /**
     * Sets the {@link View} for the given {@link ViewState}
     *
     * @param layoutRes     Layout resource id
     * @param state         The {@link ViewState} to set
     * @param switchToState If the {@link ViewState} should be switched to
     */
    public void setViewForState(@LayoutRes int layoutRes, @ViewState int state, boolean switchToState) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(getContext());
        }
        View view = mInflater.inflate(layoutRes, this, false);
        setViewForState(view, state, switchToState);
    }

    /**
     * Sets the {@link View} for the given {@link ViewState}
     *
     * @param layoutRes Layout resource id
     * @param state     The {@link View} state to set
     */
    public void setViewForState(@LayoutRes int layoutRes, @ViewState int state) {
        setViewForState(layoutRes, state, false);
    }

    /**
     * Sets whether an animate will occur when changing between {@link ViewState}
     *
     * @param animate
     */
    public void setAnimateLayoutChanges(boolean animate) {
        mAnimateViewChanges = animate;
    }

    /**
     * Sets the {@link StateListener} for the view
     *
     * @param listener The {@link StateListener} that will receive callbacks
     */
    public void setStateListener(StateListener listener) {
        mListener = listener;
    }

    /**
     * Animates the layout changes between {@link ViewState}
     *
     * @param previousView The view that it was currently on
     */
    private void animateLayoutChange(@Nullable final View previousView) {
        if (previousView == null) {
            getView(mViewState).setVisibility(View.VISIBLE);
            return;
        }

        previousView.setVisibility(View.VISIBLE);
        ObjectAnimator anim = ObjectAnimator.ofFloat(previousView, "alpha", 1.0f, 0.0f).setDuration(250L);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                previousView.setVisibility(View.GONE);
                getView(mViewState).setVisibility(View.VISIBLE);
                ObjectAnimator.ofFloat(getView(mViewState), "alpha", 0.0f, 1.0f).setDuration(250L).start();
            }
        });
        anim.start();
    }

    public interface StateListener {
        /**
         * Callback for when the {@link ViewState} has changed
         *
         * @param viewState The {@link ViewState} that was switched to
         */
        void onStateChanged(@ViewState int viewState);
    }
}
