package com.daotangbill.exlib.commons.logger

interface FormatStrategy {

    fun log(priority: Int, tag: String?, message: String?)
}
