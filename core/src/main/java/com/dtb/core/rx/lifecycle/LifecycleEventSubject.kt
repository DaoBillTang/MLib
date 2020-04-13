package com.dtb.core.rx.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class LifecycleEventSubject : LifecycleEventObserver, LifecycleProvider<Lifecycle.Event> {
    private val lifecycleSubject = BehaviorSubject.create<Lifecycle.Event>()

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        lifecycleSubject.onNext(event)
    }

    override fun lifecycle(): Observable<Lifecycle.Event> =
        lifecycleSubject.hide()

    override fun <T> bindUntilEvent(event: Lifecycle.Event): LifecycleTransformer<T> =
        RxLifecycle.bindUntilEvent(lifecycleSubject, event)


    override fun <T> bindToLifecycle(): LifecycleTransformer<T> =
        RxLifecycleAndroid.bindLifecycle(lifecycleSubject)

}