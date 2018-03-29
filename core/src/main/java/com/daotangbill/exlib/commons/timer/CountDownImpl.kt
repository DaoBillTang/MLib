package com.daotangbill.exlib.commons.timer

import com.daotangbill.exlib.commons.logger.Lerror
import com.daotangbill.exlib.commons.logger.Linfo
import com.daotangbill.exlib.commons.timer.contract.CountDownContract
import com.daotangbill.exlib.commons.utils.isFalse
import com.daotangbill.exlib.commons.utils.isTrue
import com.daotangbill.exlib.commons.utils.notNullAndEqualTo
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import java.util.concurrent.TimeUnit

/**
 * Project com.daotangbill.exlib.commons.timer
 * Created by DaoTangBill on 2018/3/27/027.
 * Email:tangbaozi@daotangbill.uu.me
 * @author bill
 * @version 1.0
 * @description 倒计时 的定时器
 *
 */
class CountDownImpl constructor(private val coreSize: Int = 1) : CountDownContract.Presenter {

    /**
     * 所有流 的管理
     */
    private val mCompositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    private val timerMap: Array<DisposableObserver<Long>?> = arrayOfNulls(coreSize)

    private var mView: CountDownContract.View? = null

    override fun onCreate() {
    }

    override fun onDestroy() {
        mCompositeDisposable.clear()
    }

    override fun bindView(view: CountDownContract.View) {
        this.mView = view
    }

    override fun startCountDown(view: CountDownContract.View, timeLimit: Long, key: Int) {
        if (key >= coreSize) {
            Lerror { "超过 coreSize 的 key " }
            return
        }

        val t = timerMap[key]

        if (t?.isDisposed == true) {
            Linfo { "同一个key 在同一时间只能进行一个计时操作" }
            return
        }

        val disposableObserver = object : DisposableObserver<Long>() {
            override fun onComplete() {
                mView.notNullAndEqualTo(view, {
                    view.timerChange(-1, key)
                })
                timerMap[key] = null
            }

            override fun onNext(t: Long) {
                val time = timeLimit - t
                mView.notNullAndEqualTo(view, {
                    view.timerChange(time, key)
                }).isFalse {
                    this.dispose()
                }
            }

            override fun onError(e: Throwable) {
                Lerror { "onError====${e.printStackTrace()}" }
                mView.notNullAndEqualTo(view, {
                    view.timerChange(-1, key)
                }).isFalse {
                    this.dispose()
                }
                timerMap[key] = null
            }
        }
        Observable.interval(0, 1000, TimeUnit.MILLISECONDS)
                .take(timeLimit)
                .subscribe(disposableObserver)
        mCompositeDisposable.add(disposableObserver)
    }

    override fun shutdown(view: CountDownContract.View, key: Int) {
        if (key >= coreSize) {
            Lerror { "超过 coreSize 的 key " }
            return
        }
        val t = timerMap[key]

        t?.let {
            timerMap[key] = null
            mCompositeDisposable.remove(it).isTrue {
                mView.notNullAndEqualTo(view, {
                    view.timerChange(-1, key)
                })
            }.isFalse {
                Lerror { "停止指定定时器失败==key =$key" }
            }
        }
    }

    override fun shutdownAll(view: CountDownContract.View) {
        mCompositeDisposable.clear()
    }
}