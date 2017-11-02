package com.daotangbill.exlib.commons.logger

interface LogAdapter {

    fun isLoggable(priority: Int, tag: String?): Boolean

    fun log(priority: Int, tag: String?, message: String?)
}