package com.dtb.utils.rx.lifecycle

import java.util.concurrent.CancellationException

import io.reactivex.Completable
import io.reactivex.exceptions.Exceptions
import io.reactivex.functions.Function
import io.reactivex.functions.Predicate

internal class Functions private constructor() {

    init {
        throw AssertionError("No instances!")
    }

    companion object {
        @JvmStatic
        val RESUME_FUNCTION: Function<Throwable, Boolean> = Function { throwable ->
            if (throwable is OutsideLifecycleException) {
                return@Function true
            }
            Exceptions.propagate(throwable)
            false
        }
        @JvmStatic
        val SHOULD_COMPLETE: Predicate<Boolean> = Predicate { shouldComplete -> shouldComplete }
        @JvmStatic
        val CANCEL_COMPLETABLE: Function<Any, Completable> = Function { Completable.error(CancellationException()) }
    }
}