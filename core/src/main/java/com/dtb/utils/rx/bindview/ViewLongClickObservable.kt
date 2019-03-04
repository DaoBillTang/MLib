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
class ViewLongClickObservable internal constructor(private val view: View, private val call: Boolean) : Observable<Any>() {

    override fun subscribeActual(observer: Observer<in Any>) {
        if (!checkMainThread(observer)) {
            return
        }
        val listener = Listener(view, call, observer)
        observer.onSubscribe(listener)
        view.setOnLongClickListener(listener)
    }

    internal class Listener(private val view: View,
                            private val call: Boolean,
                            private val observer: Observer<in Any>)
        : MainThreadDisposable(), View.OnLongClickListener {

        override fun onDispose() {
            view.setOnLongClickListener(null)
        }

        /**
         * true为不加短按,false为加入短按
         */
        override fun onLongClick(v: View): Boolean {
            if (!isDisposed) {
                observer.onNext(Notification.INSTANCE)
            }
            return false
        }
    }
}