package com.dtb.utils.commons.statusbar;

import android.app.Activity;
import android.graphics.drawable.Drawable;

/**
 * Project hschefu-android
 * Created by BILL on 2016/2/21.
 * email:tangbaozi@daotangbill.uu.me
 *
 * @author BILL
 * @version 1.0
 */
class StatusBarHelperImplBase extends BaseStatusBarHelperImpl {

    public StatusBarHelperImplBase(Activity activity) {
        super(activity);
    }

    @Override
    protected void setColor(int color) {
        // do noting
    }

    @Override
    protected void setDrawable(Drawable drawable) {
        // do noting
    }

    @Override
    protected void destroy() {
        // do noting
    }

}
