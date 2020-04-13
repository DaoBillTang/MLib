package com.dtb.core.base.contract.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dtb.core.base.contract.TimerContract
import com.dtb.core.base.contract.model.CountDownEntity
import com.dtb.core.common.logger.Linfo
import io.reactivex.Observable
import io.reactivex.observers.DisposableObserver
import java.util.concurrent.TimeUnit

/**
 * project frame
 * Created by dtb on 2020/4/11
 * email: 1750352866@qq.com

 * @author: dtb
 * *
 * @version: 1.0
 * *
 * @description:
 *         timerViewmodel= ViewModelProvider(this).get(TimerViewModel::class.java)
 */
class TimerViewModel : ViewModel(), TimerContract {

    val data: MutableLiveData<CountDownEntity> by lazy {
        val v = CountDownEntity()
        val d = MutableLiveData<CountDownEntity>()
        d.postValue(v)
        return@lazy d
    }


    private var timer: DisposableObserver<Long>? = null

    private fun postTime(time: Long, show: Boolean) {
        val v: CountDownEntity? = data.value
        v?.show = show
        v?.time = time
        data.postValue(v)
    }

    override fun startCountDown(timeLimit: Long) {
        if (timer?.isDisposed == true) {
            Linfo { "同一个key 在同一时间只能进行一个计时操作" }
            return
        }

        timer = object : DisposableObserver<Long>() {
            override fun onComplete() {
                postTime(-1, false)
                this.dispose()
            }

            override fun onNext(t: Long) {
                val time = timeLimit - t
                postTime(time, true)
            }

            override fun onError(e: Throwable) {
                postTime(-1, false)
                this.dispose()
            }
        }
        Observable.interval(0, 1000, TimeUnit.MILLISECONDS)
            .take(timeLimit)
            .subscribe(timer as DisposableObserver<Long>)
    }

    override fun startTimer(timeLimit: Long) {
        if (timer?.isDisposed == true) {
            Linfo { "同一个key 在同一时间只能进行一个计时操作" }
            return
        }

        timer = object : DisposableObserver<Long>() {
            override fun onComplete() {
                postTime(-1, false)
                this.dispose()
            }

            override fun onNext(t: Long) {
                postTime(t, true)
            }

            override fun onError(e: Throwable) {
                postTime(-1, false)
                timer?.dispose()
            }
        }
        Observable.interval(0, 1000, TimeUnit.MILLISECONDS)
            .take(timeLimit)
            .subscribe(timer as DisposableObserver<Long>)
    }

    override fun shutdown() {
        timer?.dispose()
    }

    override fun onCleared() {
        timer?.dispose()
    }
}