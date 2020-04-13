@file:JvmMultifileClass
@file:JvmName("RxRecyclerViewAdapter")

package com.dtb.core.rx.bindview.recyclerview

import androidx.annotation.CheckResult
import androidx.recyclerview.widget.RecyclerView
import com.dtb.core.rx.bindview.InitialValueObservable
import com.dtb.core.rx.bindview.Preconditions.Companion.checkMainThread
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable

/**
 * Create an observable of data change events for `RecyclerView.adapter`.
 *
 * *Note:* A value will be emitted immediately on subscribe.
 */
@CheckResult
fun <T : RecyclerView.Adapter<out RecyclerView.ViewHolder>> T.dataChanges(): InitialValueObservable<T> =
        RecyclerAdapterDataChangeObservable(this)

private class RecyclerAdapterDataChangeObservable<T : RecyclerView.Adapter<out RecyclerView.ViewHolder>>(
        private val adapter: T
) : InitialValueObservable<T>() {

    override fun subscribeListener(observer: Observer<in T>) {
        if (!checkMainThread(observer)) {
            return
        }
        val listener = Listener(
                adapter, observer)
        observer.onSubscribe(listener)
        adapter.registerAdapterDataObserver(listener.dataObserver)
    }

    override val initialValue get() = adapter

    class Listener<T : RecyclerView.Adapter<out RecyclerView.ViewHolder>>(
            private val recyclerAdapter: T,
            observer: Observer<in T>
    ) : MainThreadDisposable() {

        val dataObserver = object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                if (!isDisposed) {
                    observer.onNext(recyclerAdapter)
                }
            }
        }

        override fun onDispose() {
            recyclerAdapter.unregisterAdapterDataObserver(dataObserver)
        }
    }
}
