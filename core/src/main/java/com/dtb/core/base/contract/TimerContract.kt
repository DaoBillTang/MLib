package com.dtb.core.base.contract

/**
 * Project com.daotangbill.exlib.commons.timer.contract
 * Created by DaoTangBill on 2018/3/27/027.
 * Email:tangbaozi@daotangbill.uu.me
 * @author bill
 * @version 1.0
 * @description
 *  用于独立出来处理定 倒计时的
 */
interface TimerContract {


    /**
     * 开始倒计时
     * @param timeLimit 时间限制
     */
    fun startCountDown(timeLimit: Long)

    fun startTimer(timeLimit: Long)

    /**
     * 停止计时
     */
    fun shutdown()
}