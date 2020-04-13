package com.dtb.core.rx.bindview

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.view.MotionEvent
import android.view.View
import android.widget.RadioGroup
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.dtb.core.base.DtbBaseActivity
import com.dtb.core.base.DtbBaseFragment
import com.dtb.core.common.logger.Lerror
import io.reactivex.Observable
import io.reactivex.functions.Predicate
import java.util.concurrent.TimeUnit

/**
 * project com.daotangbill.rxextlib.lib.rx.bindview
 * Created by Bill on 2017/10/31.
 * emal: tangbakzi@daotangbill.uu.me
 *
 * @author Bill
 * @version 1.0
 * @description 专为 rxjava 的bind 准备的
 */

/**
 *@param listener 需要实现的功能
 *@param time 需要限制的时间，单位 秒
 *@param act 需要绑定的DtBaseActivity
 *@param event 需要限制 当前事件最后的生命周期
 *@warning 同时，一个View 有且只应该有一个 事件绑定进去
 */


fun Observable<Any>?.bindActLife(
    act: Activity?,
    event: Lifecycle.Event = Lifecycle.Event.ON_DESTROY
)
        : Observable<Any> {
    if (act is DtbBaseActivity) {
        return this?.compose(act.bindUntilEvent(event))!!
    } else {
        throw  IllegalAccessException("需要Activity继承自DtBaseActivity")
    }
}

fun Observable<Any>?.bindFraLife(
    fra: Fragment?,
    event: Lifecycle.Event = Lifecycle.Event.ON_DESTROY
)
        : Observable<Any> {
    if (fra is DtbBaseFragment) {
        return this?.compose(fra.bindUntilEvent(event))!!
    } else {
        throw  IllegalAccessException("需要Activity继承自DtBaseActivity")
    }
}


@MainThread
fun View.bindClick(
    listener: () -> Unit, time: Long, act: Activity?,
    event: Lifecycle.Event = Lifecycle.Event.ON_DESTROY
) {
    ViewClickObservable(this)
        .bindActLife(act, event)
        .throttleFirst(time, TimeUnit.SECONDS)
        .subscribe { listener.invoke() }
}

@MainThread
fun View.bindClick(
    listener: () -> Unit, time: Long, fra: Fragment,
    event: Lifecycle.Event = Lifecycle.Event.ON_DESTROY
) {
    ViewClickObservable(this)
        .bindFraLife(fra, event)
        .throttleFirst(time, TimeUnit.SECONDS)
        .subscribe { listener.invoke() }
}


@SuppressLint("CheckResult")
fun RadioGroup.bindOnCheckChange(
    listener: (checkedId: Int) -> Boolean,
    time: Long,
    act: Activity?,
    event: Lifecycle.Event = Lifecycle.Event.ON_DESTROY
) {
    if (act is DtbBaseActivity) {
        RadioGroupCheckedChangeObservable(this)
            .compose(act.bindUntilEvent(event))
            .throttleFirst(time, TimeUnit.SECONDS)
            .subscribe { listener.invoke(it) }
    }
}


/**
 *@param listener 需要实现的功能
 *@param time 需要限制的时间，单位 秒
 *@param call longClickListener 的是否消费当前事件
 *@param act 需要绑定的DtBaseActivity
 *@param event 需要限制 当前事件最后的生命周期
 */
@MainThread
fun View.bindLongClick(
    listener: () -> Boolean, time: Long, act: Activity?,
    call: Boolean = true,
    event: Lifecycle.Event = Lifecycle.Event.ON_DESTROY
) {
    if (act is DtbBaseActivity) {
        ViewLongClickObservable(this, call)
            .compose(act.bindUntilEvent(event))
            .throttleFirst(time, TimeUnit.SECONDS)
            .subscribe { listener.invoke() }
    } else {
        throw  IllegalAccessException("需要Activity继承自DtBaseActivity")
    }
}

@MainThread
fun View.bindLongClick(
    listener: () -> Boolean, time: Long, fra: Fragment,
    call: Boolean = true,
    event: Lifecycle.Event = Lifecycle.Event.ON_DESTROY
) {
    val act = fra.activity
    this.bindLongClick(listener, time, act, call, event)
}

/**
 *@param listener 需要实现的功能
 *@param time 需要限制的时间，单位 秒
 *@param call longClickListener 的是否消费当前事件
 *@param act 需要绑定的DtBaseActivity
 *@param event 需要限制 当前事件最后的生命周期
 */
@MainThread
fun View.bindTouch(
    listener: (MotionEvent) -> Boolean,
    act: Activity?,
    event: Lifecycle.Event = Lifecycle.Event.ON_DESTROY
) {
    if (act is DtbBaseActivity) {
        ViewTouchObservable(this, Predicate { listener.invoke(it) })
            .compose(act.bindUntilEvent(event))
            .subscribe { }
    } else {
        throw  IllegalAccessException("需要Activity继承自DtBaseActivity")
    }
}

@MainThread
fun View.bindTouch(
    listener: (MotionEvent) -> Boolean,
    fra: Fragment,
    event: Lifecycle.Event = Lifecycle.Event.ON_DESTROY
) {
    val act = fra.activity
    this.bindTouch(listener, act, event)
}

/**
 *@param listener 需要实现的功能
 *@param call longClickListener 的是否消费当前事件
 *@param act 需要绑定的DtBaseActivity
 *@param event 需要限制 当前事件最后的生命周期
 */
@MainThread
fun View.bindScroll(
    listener: (ViewScrollChangeEvent) -> Unit,
    act: Activity?,
    event: Lifecycle.Event = Lifecycle.Event.ON_DESTROY
) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
        Lerror("当前版本小于23")
    }
    if (act is DtbBaseActivity) {
        ViewScrollObservable(this)
            .compose(act.bindUntilEvent(event))
            .subscribe { listener.invoke(it) }
    } else {
        throw  IllegalAccessException("需要Activity继承自DtBaseActivity")
    }
}

@MainThread
fun View.bindScroll(
    listener: (ViewScrollChangeEvent) -> Unit,
    fra: Fragment,
    event: Lifecycle.Event = Lifecycle.Event.ON_DESTROY
) {
    val act = fra.activity
    this.bindScroll(listener, act, event)
}