package com.dtb.utils.rx.bindview.recyclerview

import android.support.annotation.CheckResult
import android.support.v7.widget.RecyclerView
import android.view.View
import com.dtb.utils.rx.bindview.Preconditions.Companion.checkMainThread
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable

/**
 * Create an observable of child attach state change events on `recyclerView`.
 *
 * *Warning:* The created observable keeps a strong reference to `recyclerView`.
 * Unsubscribe to free this reference.
 */
@CheckResult
fun RecyclerView.childAttachStateChangeEvents(): Observable<RecyclerViewChildAttachStateChangeEvent> =
    RecyclerViewChildAttachStateChangeEventObservable(this)

private class RecyclerViewChildAttachStateChangeEventObservable(
  private val view: RecyclerView
) : Observable<RecyclerViewChildAttachStateChangeEvent>() {

  override fun subscribeActual(observer: Observer<in RecyclerViewChildAttachStateChangeEvent>) {
    if (!checkMainThread(observer)) {
      return
    }
    val listener = Listener(
        view, observer)
    observer.onSubscribe(listener)
    view.addOnChildAttachStateChangeListener(listener)
  }

  class Listener(
    private val recyclerView: RecyclerView,
    private val observer: Observer<in RecyclerViewChildAttachStateChangeEvent>
  ) : MainThreadDisposable(), RecyclerView.OnChildAttachStateChangeListener {

    override fun onChildViewAttachedToWindow(childView: View) {
      if (!isDisposed) {
        observer.onNext(RecyclerViewChildAttachEvent(recyclerView, childView))
      }
    }

    override fun onChildViewDetachedFromWindow(childView: View) {
      if (!isDisposed) {
        observer.onNext(RecyclerViewChildDetachEvent(recyclerView, childView))
      }
    }

    override fun onDispose() {
      recyclerView.removeOnChildAttachStateChangeListener(this)
    }
  }
}
