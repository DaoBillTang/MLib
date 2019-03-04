package com.dtb.utils.rx.bindview

import android.os.Looper
import android.support.annotation.RestrictTo
import io.reactivex.Observer
import io.reactivex.disposables.Disposables

import android.support.annotation.RestrictTo.Scope.LIBRARY_GROUP

@RestrictTo(LIBRARY_GROUP)
class Preconditions private constructor() {

    init {
        throw AssertionError("No instances.")
    }

    companion object {
        fun checkNotNull(value: Any?, message: String) {
            if (value == null) {
                throw NullPointerException(message)
            }
        }

        fun checkMainThread(observer: Observer<*>): Boolean {
            if (Looper.myLooper() != Looper.getMainLooper()) {
                observer.onSubscribe(Disposables.empty())
                observer.onError(IllegalStateException(
                        "Expected to be called on the main thread but was " + Thread.currentThread().name))
                return false
            }
            return true
        }
    }
}