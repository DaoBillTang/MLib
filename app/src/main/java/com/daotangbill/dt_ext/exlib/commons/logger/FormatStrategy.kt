package com.daotangbill.dt_ext.exlib.commons.logger

interface FormatStrategy {

    fun log(priority: Int, tag: String?, message: String?)
}
