package com.dtb.utils.commons.timer.contract

import com.dtb.utils.base.DtbBasePresenter

/**
 * Project com.daotangbill.exlib.commons.timer.contract
 * Created by DaoTangBill on 2018/3/27/027.
 * Email:tangbaozi@daotangbill.uu.me
 * @author bill
 * @version 1.0
 * @description
 *  用于独立出来处理定 倒计时的
 */
interface CountDownContract {

    interface View {
        /**
         * 时间发生改变
         */
        fun timerChange(timeLimit: Long, key: Int = 0)
    }

    interface Presenter : DtbBasePresenter {

        /**
         * 绑定某个 View
         * @param view 指定的页面
         * @param key 指定的计时器名字
         */
        fun bindView(view: View)

        /**
         * 开始倒计时
         * @param int 计时起始时间
         * @param key 指定的计时器名字
         */
        fun startCountDown(view: View, timeLimit: Long, key: Int = 0)

        /**
         * 停止计时
         * @param key 指定的计时器名字
         */
        fun shutdown(view: View, key: Int = 0)

        fun shutdownAll(view: View)


    }
}