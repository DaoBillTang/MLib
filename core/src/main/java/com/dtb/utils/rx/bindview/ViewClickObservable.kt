package com.dtb.utils.rx.bindview

import android.view.View
import com.dtb.utils.rx.bindview.Preconditions.Companion.checkMainThread

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable


/**
 * project com.daotangbill.rxextlib.lib.rx.bindview
 * Created by Bill on 2017/10/31.
 * emal: tangbakzi@daotangbill.uu.me
 *
 * @author Bill
 * @version 1.0
 * @description
 */
class ViewClickObservable internal constructor(private val view: View) : Observable<Any>() {

    override fun subscribeActual(observer: Observer<in Any>) {
        if (!checkMainThread(observer)) {
            return
        }
        val listener = Listener(view, observer)
        observer.onSubscribe(listener)
        view.setOnClickListener(listener)
    }

    internal class Listener(private val view: View, private val observer: Observer<in Any>)
        : MainThreadDisposable(), View.OnClickListener {

        override fun onClick(v: View) {
            if (!isDisposed) {
                observer.onNext(Notification.INSTANCE)
            }
        }

        override fun onDispose() {
            view.setOnClickListener(null)
        }
    }
}