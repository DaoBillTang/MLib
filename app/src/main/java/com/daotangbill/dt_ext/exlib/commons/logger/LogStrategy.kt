package com.daotangbill.dt_ext.exlib.commons.logger

interface LogStrategy {

    fun log(priority: Int, tag: String?, message: String?)
}
