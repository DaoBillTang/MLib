package com.dtb.utils.sample

import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.dtb.utils.base.DtbBaseActivity
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import java.util.concurrent.TimeUnit

/**
 * Project com.daotangbill.exlib.sample
 * Created by DaoTangBill on 2018/3/26/026.
 * Email:tangbaozi@daotangbill.uu.me
 * @author bill
 * @version 1.0
 * @description
 *
 */
class TimeActivity : DtbBaseActivity() {
    override fun getLayoutResource(): Int {
        return R.layout.activity_time
    }

    private var mTvTime1: Button? = null
    private var mTvTime2: Button? = null
    private var mTvTime3: Button? = null
    private var mTvTime4: Button? = null
    private var mTvTime5: Button? = null

    private var mCompositeDisposable: CompositeDisposable? = null
        get() {
            if (field == null) {
                field = CompositeDisposable()
            }
            return field
        }

    private val timeDemoObserver: DisposableObserver<Long>
        get() = object : DisposableObserver<Long>() {

            override fun onNext(data: Long) {
                Log.d(TAG, "DisposableObserver, onNext=" + data + ",threadId=" + Thread.currentThread().id)
            }

            override fun onError(throwable: Throwable) {
                Log.d(TAG, "DisposableObserver, onError=$throwable")
            }

            override fun onComplete() {
                Log.d(TAG, "DisposableObserver, onComplete")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mTvTime1 = findViewById(R.id.bt_time_1)
        mTvTime1?.setOnClickListener {
            startTimeDemo1()
        }
        mTvTime2 = findViewById(R.id.bt_time_2)
        mTvTime2?.setOnClickListener {
            startTimeDemo2()
        }
        mTvTime3 = findViewById(R.id.bt_time_3)
        mTvTime3?.setOnClickListener {
            startTimeDemo3()
        }
        mTvTime4 = findViewById(R.id.bt_time_4)
        mTvTime4?.setOnClickListener {
            startTimeDemo4()
        }
        mTvTime5 = findViewById(R.id.bt_time_5)
        mTvTime5?.setOnClickListener {
            startTimeDemo5()
        }
    }

    //延迟 1s 后执行一个任务，然后结束
    private fun startTimeDemo1() {
        val disposableObserver = timeDemoObserver
        Observable.timer(1000, TimeUnit.MILLISECONDS).subscribe(disposableObserver)
        mCompositeDisposable?.add(disposableObserver)
    }

    //每隔 1s 执行一次任务，第一次任务执行前有 2s 的间隔，执行无限次
    private fun startTimeDemo2() {
        val disposableObserver = timeDemoObserver
        Observable.interval(1000, TimeUnit.MILLISECONDS).subscribe(disposableObserver)
        mCompositeDisposable?.add(disposableObserver)
    }

    //每隔 1s 执行一次任务，立即执行第一次任务，执行无限次
    private fun startTimeDemo3() {
        val disposableObserver = timeDemoObserver
        Observable.interval(0, 1000, TimeUnit.MILLISECONDS).subscribe(disposableObserver)
        mCompositeDisposable?.add(disposableObserver)
    }

    //每隔 1s 执行一次任务，立即执行第一次任务，只执行五次
    private fun startTimeDemo4() {
        Log.d(TAG, "startTimeDemo4")
        val disposableObserver = timeDemoObserver
        Observable.interval(0, 1000, TimeUnit.MILLISECONDS).take(5).subscribe(disposableObserver)
        mCompositeDisposable?.add(disposableObserver)
    }

    //先执行一个任务，等待 1s，再执行另一个任务，然后结束
    private fun startTimeDemo5() {
        Log.d(TAG, "startTimeDemo5")
        val disposableObserver = timeDemoObserver
        Observable.just(0L).doOnNext {
            Log.d(TAG, "执行第一个任务")
        }.delay(1000, TimeUnit.MILLISECONDS).subscribe(disposableObserver)
        mCompositeDisposable?.add(disposableObserver)
    }

    override fun onStop() {
        super.onStop()
        mCompositeDisposable?.clear()
    }

    companion object {
        private val TAG = TimeActivity::class.java.simpleName
    }
}