package com.dtb.utils.commons.validate

import android.content.Context
import java.lang.ref.WeakReference

/**
 * Project com.daotangbill.exlib.commons.validate
 * Created by DaoTangBill on 2018/4/23/023.
 * Email:tangbaozi@daotangbill.uu.me
 * @author bill
 * @version 1.0
 * @description
 *
 */
object ValidationReulationUtils {


}


class Validation<T>() {

    private val result = ValidateResult()
    private lateinit var data: WeakReference<T>
    var contextWr: WeakReference<Context>? = null
    /**
     * 是否因为一次检测失败就 全部失败
     */
    var abortOnError = true

    constructor(obj: T) : this() {
        data = WeakReference(obj)
    }

    constructor(obj: T, context: Context) : this(obj) {
        contextWr = WeakReference(context)
    }

    fun getContext(): Context? {
        return contextWr?.get()
    }

    class ValidationRegulation {
        /**
         *  如果 是abortOnError ==False ,同时校验不合格，触发事件
         */
        var errorBlock: (() -> Unit) = {}

        /**
         * 校验的规则
         */
        var regulation: (() -> Boolean) = {
            true
        }
        var msg: String? = null

        fun validate(): Boolean {
            return regulation()
        }
    }

    fun result(): ValidateResult {
        return result
    }

    fun CharSequence?.parity(validate: ValidationRegulation.() -> Unit) {
        if (checkAbortOnErrorIsError()) {
            return
        }
        val regulation = ValidationRegulation().apply(validate)

        val b = regulation.validate()
        if (!b) {
            result.result = false
            regulation.msg?.let {
                result.msg.add(it)
            }
            if (abortOnError) {
                regulation.errorBlock()
            }
        }
    }

    /**
     * 检查 是否已经失败了
     */
    private fun checkAbortOnErrorIsError(): Boolean {
        if (abortOnError && !result.result) {
            return true
        }
        return false
    }
}