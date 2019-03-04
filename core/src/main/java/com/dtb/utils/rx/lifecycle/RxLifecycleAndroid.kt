package com.dtb.utils.rx.lifecycle

import android.support.annotation.CheckResult
import com.dtb.utils.rx.lifecycle.RxLifecycle.bind

import io.reactivex.Observable
import io.reactivex.functions.Function


object RxLifecycleAndroid {
    /**
     * @param lifecycle :绑定自己生命周期的 的observabel
     * @return <T>
     */
    @CheckResult
    fun <T> bindActivity(lifecycle: Observable<ActivityEvent>): LifecycleTransformer<T> =
            bind(lifecycle, ACTIVITY_LIFECYCLE)

    /**
     * @param lifecycle :绑定自己生命周期的 的observabel
     * @return <T>
     */
    @CheckResult
    fun <T> bindFragment(lifecycle: Observable<FragmentEvent>): LifecycleTransformer<T> =
            bind(lifecycle, FRAGMENT_LIFECYCLE)

    fun <T> bindPresenter(lifecycle: Observable<PresenterEvent>): LifecycleTransformer<T> =
            bind(lifecycle, PRESENTER_LIFECYCLE)


    /**
     * 一个 附加了默认值的lifecycle
     */
    private val ACTIVITY_LIFECYCLE = Function<ActivityEvent, ActivityEvent> { lastEvent ->
        when (lastEvent) {
            ActivityEvent.CREATE -> ActivityEvent.DESTROY
            ActivityEvent.START -> ActivityEvent.STOP
            ActivityEvent.RESUME -> ActivityEvent.PAUSE
            ActivityEvent.PAUSE -> ActivityEvent.STOP
            ActivityEvent.STOP -> ActivityEvent.DESTROY
            ActivityEvent.DESTROY -> throw OutsideLifecycleException("Cannot bind to Activity lifecycle when outside of it.")
            else -> throw UnsupportedOperationException("Binding to $lastEvent not yet implemented")
        }
    }

    private val FRAGMENT_LIFECYCLE = Function<FragmentEvent, FragmentEvent> { lastEvent ->
        when (lastEvent) {
            FragmentEvent.ATTACH -> FragmentEvent.DETACH
            FragmentEvent.CREATE -> FragmentEvent.DESTROY
            FragmentEvent.CREATE_VIEW -> FragmentEvent.DESTROY_VIEW
            FragmentEvent.START -> FragmentEvent.STOP
            FragmentEvent.RESUME -> FragmentEvent.PAUSE
            FragmentEvent.PAUSE -> FragmentEvent.STOP
            FragmentEvent.STOP -> FragmentEvent.DESTROY_VIEW
            FragmentEvent.DESTROY_VIEW -> FragmentEvent.DESTROY
            FragmentEvent.DESTROY -> FragmentEvent.DETACH
            FragmentEvent.DETACH -> throw OutsideLifecycleException("Cannot bind to Fragment lifecycle when outside of it.")
            else -> throw UnsupportedOperationException("Binding to $lastEvent not yet implemented")
        }
    }

    private val PRESENTER_LIFECYCLE = Function<PresenterEvent, PresenterEvent> { lastEvent ->
        when (lastEvent) {
            PresenterEvent.CREATE -> PresenterEvent.DESTROY
            PresenterEvent.DESTROY -> throw OutsideLifecycleException("Cannot bind to Presenter lifecycle when outside of it.")
            else -> throw UnsupportedOperationException("Binding to $lastEvent not yet implemented")
        }
    }
}