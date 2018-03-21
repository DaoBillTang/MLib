package com.daotangbill.exlib.commons.logger

import android.util.Log

/**
 * 打印 logcat 日志
 */
class LogcatLogStrategy {
    fun log(priority: Int, tag: String?, message: String?) {
        Log.println(priority, tag ?: "null Tag", message ?: "null/no Message")
    }
}