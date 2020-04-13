package com.dtb.core.base;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import androidx.fragment.app.Fragment;

import java.lang.ref.WeakReference;


/**
 * @author dtb
 * 名字内存泄漏的 handler
 */
public class SafeHandler extends Handler {

    private WeakReference<Activity> weakAct;
    private WeakReference<Fragment> weakFrag;

    public SafeHandler(Activity activity) {
        weakAct = new WeakReference<>(activity);
    }

    public SafeHandler(Fragment activity) {
        weakFrag = new WeakReference<>(activity);
    }

    @Override
    public void handleMessage(Message msg) {
        if (weakAct.get() == null && weakFrag == null) {
            return;
        }
    }
}