package com.dtb.utils.rx.lifecycle


import io.reactivex.Observable
import io.reactivex.annotations.CheckReturnValue

interface LifecycleProvider<E> {
    @CheckReturnValue
    fun lifecycle(): Observable<E>

    @CheckReturnValue
    fun <T> bindUntilEvent(event: E): LifecycleTransformer<T>

    @CheckReturnValue
    fun <T> bindToLifecycle(): LifecycleTransformer<T>
}