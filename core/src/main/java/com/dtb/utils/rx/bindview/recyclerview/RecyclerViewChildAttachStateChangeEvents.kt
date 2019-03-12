package com.dtb.utils.rx.bindview.recyclerview

import android.support.v7.widget.RecyclerView
import android.view.View

// TODO move all of this next to the Observable https://youtrack.jetbrains.com/issue/KT-27097

/**
 * **Warning:** Instances keep a strong reference to the view. Operators that cache
 * instances have the potential to leak the associated [Context].
 */
sealed class RecyclerViewChildAttachStateChangeEvent {
  /** The view from which this event occurred.  */
  abstract val view: RecyclerView

  /** The child from which this event occurred.  */
  abstract val child: View
}

/**
 * A child view attach event on a [RecyclerView].
 *
 * **Warning:** Instances keep a strong reference to the view. Operators that cache
 * instances have the potential to leak the associated [Context].
 */
data class RecyclerViewChildAttachEvent(
  override val view: RecyclerView,
  override val child: View
) : RecyclerViewChildAttachStateChangeEvent()

/**
 * A child view detach event on a [RecyclerView].
 *
 * **Warning:** Instances keep a strong reference to the view. Operators that cache
 * instances have the potential to leak the associated [Context].
 */
data class RecyclerViewChildDetachEvent(
  override val view: RecyclerView,
  override val child: View
) : RecyclerViewChildAttachStateChangeEvent()
