package com.dtb.utils.rx.bindview.recyclerview

import android.content.Context
import android.support.annotation.CheckResult
import android.support.v7.widget.RecyclerView
import com.dtb.utils.rx.bindview.Preconditions.Companion.checkMainThread
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable

/**
 * Create an observable of fling events on `recyclerView`.
 *
 * *Warning:* The created observable keeps a strong reference to `recyclerView`.
 * Unsubscribe to free this reference.
 */
@CheckResult
fun RecyclerView.flingEvents(): Observable<RecyclerViewFlingEvent> =
    RecyclerViewFlingEventObservable(this)

/**
 * A fling event on a recyclerView.
 *
 * **Warning:** Instances keep a strong reference to the recyclerView. Operators that
 * cache instances have the potential to leak the associated [Context].
 */
data class RecyclerViewFlingEvent(val view: RecyclerView, val velocityX: Int, val velocityY: Int)

private class RecyclerViewFlingEventObservable(
  private val view: RecyclerView
) : Observable<RecyclerViewFlingEvent>() {

  override fun subscribeActual(observer: Observer<in RecyclerViewFlingEvent>) {
    if (!checkMainThread(observer)) {
      return
    }
    val listener = Listener(view, observer)
    observer.onSubscribe(listener)
    view.onFlingListener = listener.scrollListener
  }

  class Listener(
    private val recyclerView: RecyclerView,
    observer: Observer<in RecyclerViewFlingEvent>
  ) : MainThreadDisposable() {

    val scrollListener = object : RecyclerView.OnFlingListener() {
      override fun onFling(velocityX: Int, velocityY: Int): Boolean {
        if (!isDisposed) {
          observer.onNext(RecyclerViewFlingEvent(recyclerView, velocityX, velocityY))
        }
        return false
      }
    }

    override fun onDispose() {
      recyclerView.onFlingListener = null
    }
  }
}
