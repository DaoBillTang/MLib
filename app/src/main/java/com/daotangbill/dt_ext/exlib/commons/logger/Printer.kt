package com.daotangbill.dt_ext.dtext.commons.logger

interface Printer {

    fun addAdapter(adapter: LogAdapter)

    fun d(message: String)

    fun d(`object`: Any)

    fun e(message: String)

    fun e(throwable: Throwable, message: String)

    fun w(message: String)

    fun i(message: String)

    fun v(message: String)

    fun wtf(message: String)

    /**
     * Formats the given json content and print it
     */
    fun json(json: String)

    /**
     * Formats the given xml content and print it
     */
    fun xml(xml: String)

    fun log(priority: Int, tag: String, message: String, throwable: Throwable)

    fun clearLogAdapters()
}
