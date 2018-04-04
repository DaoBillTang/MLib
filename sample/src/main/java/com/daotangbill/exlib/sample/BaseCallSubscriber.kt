package com.daotangbill.exlib.sample

import com.daotangbill.exlib.commons.logger.Ldebug
import com.daotangbill.exlib.commons.logger.Lerror
import com.google.gson.Gson
import io.reactivex.observers.DisposableObserver
import retrofit2.Call
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Project com.mcworle.ecentm.consumer.api
 * Created by DaoTangBill on 2018/4/3/003.
 * Email:tangbaozi@daotangbill.uu.me
 * @author bill
 * @version 1.0
 * @description
 *
 */
abstract class BaseCallSubscriber<T> : DisposableObserver<Response<T>>() {

    override fun onNext(response: Response<T>) {
        if (response.isSuccessful) {
            onSuccess(response.body())
        } else {
            val s = response.errorBody()?.string()
            var b: ErrorRsps? = null
            try {
                b = Gson().fromJson(s, ErrorRsps::class.java)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            onError(response.code(), b)
        }
        onEnd()
    }

    abstract fun onSuccess(baseRsps: T?)

    /**
     * 所有的错误都会进入 的错误处理点
     * 注意：需要对 401 错误的特殊处理在其他的地方
     */
    abstract fun onError(code: Int?, s: ErrorRsps?)

    fun onFailure(call: Call<T>?, t: Throwable?) {
        if (call?.isCanceled == true) {
            //如果是手动取消请求
            Ldebug { "call cancel====$call" }
        } else {
            onError(-1, ErrorRsps("网络连接错误，请检查网络连接再试"))
            Lerror {
                "url===${call?.request()?.url()}\nbody==${call?.request()?.body()?.toString()}" +
                        "\n${call?.request()?.headers()}\n error=====$t"
            }
            onEnd()
        }
    }

    override fun onComplete() {
        dispose()
    }

    open fun onEnd() {}

    override fun onError(t: Throwable) {
        //处理常见的几种连接错误
        val msgEvent: ErrorRsps = when (t) {
            is SocketTimeoutException -> ErrorRsps("网络请求失败，请检查网络连接或请稍后重试")
            is ConnectException -> ErrorRsps("服务器连接失败，请稍后重试")
            is UnknownHostException -> ErrorRsps("请求地址错误，请联系管理员")
            else -> ErrorRsps("网络错误，请检查网络连接或请稍后重试")
        }
        onError(-1, msgEvent)
    }
}