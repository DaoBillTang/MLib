package com.daotangbill.exlib.base

import com.daotangbill.exlib.commons.logger.Lerror
import com.daotangbill.exlib.commons.logger.Linfo
import com.daotangbill.exlib.commons.utils.isFalse
import com.daotangbill.exlib.commons.utils.isTrue
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import java.util.concurrent.TimeUnit

/**
 * Project com.daotangbill.exlib.base
 * Created by DaoTangBill on 2018/3/28/028.
 * Email:tangbaozi@daotangbill.uu.me
 *
 * @author bill
 * @version 1.0
 * @description
 */
class RxHandler {

    private val mCompositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    private val disposableMap: HashMap<String, Disposable> by lazy {
        HashMap<String, Disposable>()
    }

    /**
     * 这个 添加方式  会顶掉前面的 可能 会有问题
     */
    fun put(key: String, disposable: Disposable) {
        disposableMap[key] = disposable
    }

    fun putSafe(key: String, disposable: Disposable): Boolean {
        val t = disposableMap[key]
        return if (t == null) {
            disposableMap[key] = disposable
            true
        } else {
            false
        }
    }

    fun post(block: () -> Unit) {
        postDelayed(block, 0)
    }

    fun postDelayed(block: () -> Unit, time: Long) {
        val disposableObserver = object : DisposableObserver<Any>() {
            override fun onComplete() {
            }

            override fun onNext(t: Any) {
                block()
            }

            override fun onError(e: Throwable) {
            }
        }

        Observable.timer(time, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver)
        mCompositeDisposable.add(disposableObserver)
    }

    fun timer(timeLimit: Long, key: String, block: (time: Long, key: String) -> Unit) {
        val t = disposableMap[key]
        if (t != null) {
            Linfo { "同一个key 在同一时间只能进行一个计时操作" }
            return
        }

        val disposableObserver = object : DisposableObserver<Long>() {
            override fun onComplete() {
                disposableMap.remove(key)
            }

            override fun onNext(t: Long) {
                block(t, key)
            }

            override fun onError(e: Throwable) {
                Lerror { "倒计时 出现异常： ${e.printStackTrace()}" }
                disposableMap.remove(key)
            }
        }
        disposableMap[key] = disposableObserver

        Observable.interval(0, 1000, TimeUnit.MILLISECONDS)
                .take(timeLimit)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver)
        mCompositeDisposable.add(disposableObserver)
    }

    fun removeCallbacksAndMessages(key: String? = null, block: ((b: Boolean) -> Unit)? = null) {
        if (key == null) {
            mCompositeDisposable.clear()
            disposableMap.clear()
        } else {
            val t = disposableMap[key]
            t?.let {
                mCompositeDisposable.remove(it)
                        .isTrue {
                            disposableMap.remove(key)
                            block?.invoke(true)
                        }.isFalse {
                            Lerror { "停止指定 disposable 失败==key =$key" }
                            block?.invoke(false)
                        }
            }
        }
    }
}