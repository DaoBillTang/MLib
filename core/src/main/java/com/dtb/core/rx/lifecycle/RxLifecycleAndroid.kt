package com.dtb.core.rx.lifecycle

import androidx.annotation.CheckResult
import androidx.lifecycle.Lifecycle
import com.dtb.core.rx.lifecycle.RxLifecycle.bind
import io.reactivex.Observable
import io.reactivex.functions.Function


object RxLifecycleAndroid {
    /**
     * @param lifecycle :绑定自己生命周期的 的observabel
     * @return <T>
     */
    @CheckResult
    fun <T> bindLifecycle(lifecycle: Observable<Lifecycle.Event>): LifecycleTransformer<T> =
        bind(lifecycle, ACTIVITY_LIFECYCLE)


    /**
     * 一个 附加了默认值的lifecycle
     */
    private val ACTIVITY_LIFECYCLE = Function<Lifecycle.Event, Lifecycle.Event> { lastEvent ->
        when (lastEvent) {
            Lifecycle.Event.ON_CREATE -> Lifecycle.Event.ON_DESTROY
            Lifecycle.Event.ON_START -> Lifecycle.Event.ON_STOP
            Lifecycle.Event.ON_RESUME -> Lifecycle.Event.ON_PAUSE
            Lifecycle.Event.ON_PAUSE -> Lifecycle.Event.ON_STOP
            Lifecycle.Event.ON_STOP -> Lifecycle.Event.ON_DESTROY
            Lifecycle.Event.ON_DESTROY -> throw OutsideLifecycleException("Cannot bind to lifecycle when outside of it.")
            else -> throw UnsupportedOperationException("Binding to $lastEvent not yet implemented")
        }
    }
}