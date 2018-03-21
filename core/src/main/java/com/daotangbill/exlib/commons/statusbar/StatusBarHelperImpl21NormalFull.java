package com.daotangbill.exlib.commons.statusbar;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.View;

/**
 * Project hschefu-android
 * Created by BILL on 2016/2/21.
 * email:tangbaozi@daotangbill.uu.me
 * 5.x设置Status Bar颜色。使用5.x自带API设置Status Bar颜色，同时设置页面全屏。
 * @author BILL
 * @version 1.0
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
class StatusBarHelperImpl21NormalFull extends StatusBarHelperImpl21Normal {

    public StatusBarHelperImpl21NormalFull(Activity activity) {
        super(activity);
    }

    @Override
    protected void setColor(int color) {
        // 设置全屏
        mActivity.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setActivityRootLayoutFitSystemWindowsInternal();
        // 设置颜色
        super.setColor(color);
    }

}
