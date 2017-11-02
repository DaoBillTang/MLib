package com.daotangbill.exlib.commons.logger

interface LogStrategy {

    fun log(priority: Int, tag: String?, message: String?)
}
