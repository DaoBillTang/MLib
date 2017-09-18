package com.daotangbill.dt_ext.exlib.commons.logger

import android.util.Log

/**
 * 打印 logcat 日志
 */
class LogcatLogStrategy : LogStrategy {
    override fun log(priority: Int, tag: String?, message: String?) {
        Log.println(priority, tag ?: "null Tag", message ?: "null/no Message")
    }
}