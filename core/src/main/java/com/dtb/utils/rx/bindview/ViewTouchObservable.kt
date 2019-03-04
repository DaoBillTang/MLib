package com.dtb.utils.rx.bindview

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import com.dtb.utils.rx.bindview.Preconditions.Companion.checkMainThread

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable
import io.reactivex.functions.Predicate

/**
 * project com.daotangbill.rxextlib.lib.rx.bindview
 * Created by Bill on 2017/10/31.
 * emal: tangbakzi@daotangbill.uu.me
 *
 * @author Bill
 * @version 1.0
 * @description
 */
class ViewTouchObservable internal constructor(private val view: View,
                                               private val handled: Predicate<in MotionEvent>)
    : Observable<MotionEvent>() {

    override fun subscribeActual(observer: Observer<in MotionEvent>) {
        if (!checkMainThread(observer)) {
            return
        }
        val listener = Listener(view, handled, observer)
        observer.onSubscribe(listener)
        view.setOnTouchListener(listener)
    }

    internal class Listener(private val view: View,
                            private val handled: Predicate<in MotionEvent>,
                            private val observer: Observer<in MotionEvent>)
        : MainThreadDisposable(), View.OnTouchListener {

        @SuppressLint("ClickableViewAccessibility")
        override fun onTouch(v: View, event: MotionEvent): Boolean {
            if (!isDisposed) {
                try {
                    if (handled.test(event)) {
                        observer.onNext(event)
                        return true
                    }
                } catch (e: Exception) {
                    observer.onError(e)
                    dispose()
                }
            }
            return false
        }

        override fun onDispose() {
            view.setOnTouchListener(null)
        }
    }
}