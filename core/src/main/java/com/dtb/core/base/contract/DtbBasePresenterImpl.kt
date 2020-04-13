package com.dtb.core.base.contract

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

/**
 * project frame
 * Created by dtb on 2020/4/11
 * email: 1750352866@qq.com
 *
 * @author: dtb
 * *
 * @version: 1.0
 * *
 * @description:
 *         如果可以的话，使用 lifeCycle 进行
 */
class DtbBasePresenterImpl : DtbBasePresenter, LifecycleEventObserver {
    override fun onCreate() {
    }

    override fun onDestroy() {
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_DESTROY) {
            onDestroy()
        }
    }
}