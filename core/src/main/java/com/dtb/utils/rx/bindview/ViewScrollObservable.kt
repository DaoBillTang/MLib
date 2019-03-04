package com.dtb.utils.rx.bindview

import android.annotation.TargetApi
import android.os.Build
import android.view.View
import com.dtb.utils.rx.bindview.Preconditions.Companion.checkMainThread
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable


/**
 * project com.daotangbill.rxextlib.lib.rx.bindview
 * Created by Bill on 2017/10/31.
 * emal: tangbakzi@daotangbill.uu.me
 * @author Bill
 * @version 1.0
 * @description
 */
@TargetApi(Build.VERSION_CODES.M)
class ViewScrollObservable internal constructor(private val view: View) : Observable<ViewScrollChangeEvent>() {

    override fun subscribeActual(observer: Observer<in ViewScrollChangeEvent>) {
        if (!checkMainThread(observer)) {
            return
        }
        val listener = Listener(view, observer)
        observer.onSubscribe(listener)
        view.setOnScrollChangeListener(listener)
    }

    internal class Listener(private val view: View,
                            private val observer: Observer<in ViewScrollChangeEvent>) :
            MainThreadDisposable(), View.OnScrollChangeListener {

        override fun onScrollChange(v: View, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
            if (!isDisposed) {
                observer.onNext(ViewScrollChangeEvent(v, scrollX, scrollY, oldScrollX, oldScrollY))
            }
        }

        override fun onDispose() {
            view.setOnScrollChangeListener(null)
        }
    }
}

data class ViewScrollChangeEvent(val v: View, val scrollX: Int, val scrollY: Int,
                                 val oldScrollX: Int, val oldScrollY: Int)