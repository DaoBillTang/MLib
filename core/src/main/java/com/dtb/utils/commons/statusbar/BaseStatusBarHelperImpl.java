package com.dtb.utils.commons.statusbar;

import android.app.Activity;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;

/**
 * Project hschefu-android
 * Created by BILL on 2016/2/21.
 * email:tangbaozi@daotangbill.uu.me
 *
 * @author BILL
 * @version 1.0
 */
abstract class BaseStatusBarHelperImpl {

    final Activity mActivity;

    boolean mActivityRootLayoutFitSystemWindows = true;

    public BaseStatusBarHelperImpl(Activity activity) {
        this.mActivity = activity;
    }

    /**
     * 处理全屏模式下adjust resize失效的问题
     */
    void setConsumeInsets(final StatusBarHelper.OnConsumeInsetsCallback callback) {
        ViewGroup contentLayout = (ViewGroup) mActivity.findViewById(android.R.id.content);
        View activityLayout = contentLayout.getChildAt(0);
        ConsumeInsetsFrameLayout insetsFrameLayout = new ConsumeInsetsFrameLayout(mActivity);
        contentLayout.removeView(activityLayout);
        insetsFrameLayout.addView(activityLayout);
        contentLayout.addView(insetsFrameLayout);

        insetsFrameLayout.setOnInsetsCallback(new ConsumeInsetsFrameLayout.OnInsetsCallback() {
            @Override
            public void onInsetsChanged(Rect insets) {
                if (callback != null) {
                    callback.onInsetsChanged(insets);
                }
            }
        });
    }

    /**
     * 获得状态栏高度
     */
    protected int getStatusBarHeight() {
        int resourceId = mActivity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return mActivity.getResources().getDimensionPixelSize(resourceId);
    }

    protected void setActivityRootLayoutFitSystemWindowsInternal() {
        ViewGroup contentLayout = (ViewGroup) mActivity.findViewById(android.R.id.content);
        contentLayout.getChildAt(0).setFitsSystemWindows(isActivityRootLayoutFitSystemWindows());
    }

    public void setActivityRootLayoutFitSystemWindows(boolean fitSystemWindows) {
        mActivityRootLayoutFitSystemWindows = fitSystemWindows;
    }

    public boolean isActivityRootLayoutFitSystemWindows() {
        return mActivityRootLayoutFitSystemWindows;
    }

    /**
     * 用颜色值设置Status Bar的颜色
     */
    protected abstract void setColor(int color);

    /**
     * 用Drawable设置Status Bar的颜色，5.x以上只有在
     * {@link StatusBarHelper#LEVEL_21_VIEW LEVEL_21_VIEW}下才支持。
     */
    protected abstract void setDrawable(Drawable drawable);

    /**
     * 如果Status Bar不用需要着色了，调用该方法移除相关View。
     */
    protected abstract void destroy();
}