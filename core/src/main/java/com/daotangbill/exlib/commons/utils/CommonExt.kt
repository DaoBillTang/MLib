package com.daotangbill.exlib.commons.utils

import android.content.Context
import android.os.Looper
import com.daotangbill.exlib.commons.cache.Cache
import com.daotangbill.exlib.commons.logger.Lerror
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.Serializable
import java.lang.ref.WeakReference

/**
 * Project com.daotangbill.exlib.commons.utils
 * Created by DaoTangBill on 2018/3/26/026.
 * Email:tangbaozi@daotangbill.uu.me
 * @author bill
 * @version 1.0
 * @description
 *
 */

/**
 * 安全的调用某个方法，
 */
fun <T> T.safe(block: (T) -> Unit) {
    try {
        block(this)
    } catch (e: Exception) {
        "safe".Lerror { e.toString() }
    }
}

/**
 *  讲操作 放入IO 线程中
 */
fun <T> T.asyncIO(block: (T) -> Unit) {
    Flowable.create(FlowableOnSubscribe<Any> {
        Looper.prepare()
        safe { block(this) }
        /**
         *    注意：写在Looper.loop()之后的代码不会被执行，这个函数内部应该是一个循环，
         *    当调用mHandler.getLooper().quit()后，
         *    loop才会中止，其后的代码才能得以运行。
         */
        Looper.loop()
    }, BackpressureStrategy.BUFFER)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
}

class CommonExtContext<T>(val weakRef: WeakReference<T>) {
    var key: String = "Cache"
    var expiry: Int = 0
}

/**
 * 缓存 数据的方案
 */
fun Context.cache(block: CommonExtContext<Context>.() -> Any?) {
    val context = CommonExtContext(WeakReference(this))
    val result: Any? = context.block()

    val key = context.key
    val expiry = context.expiry
    val cache = Cache.get(context.weakRef.get(), key)

    if (expiry > 0) {
        cache.put(key, result as Serializable?, expiry)
    } else {
        cache.put(key, result as Serializable?)
    }
}

fun <T> T?.notNullAndEqualTo(other: Any?, block: (T) -> Unit): Boolean {
    if (this != null && this == other) {
        block(this)
        return true
    }
    return false
}

fun <T> T?.notNullAndDiffTo(other: Any?, block: (T) -> Unit): Boolean {
    if (this != null && this != other) {
        block(this)
        return true
    }
    return false
}

fun Boolean?.isTrue(block: () -> Unit): Boolean {
    if (this == true) {
        block()
        return true
    }
    return false
}

fun Boolean?.isFalse(block: () -> Unit): Boolean {
    if (this != true) {
        block()
        return true
    }
    return false
}