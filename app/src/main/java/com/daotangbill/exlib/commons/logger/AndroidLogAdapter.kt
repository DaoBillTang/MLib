package com.daotangbill.exlib.commons.logger

class AndroidLogAdapter : LogAdapter {

    private val formatStrategy: FormatStrategy

    constructor() {
        this.formatStrategy = PrettyFormatStrategy.newBuilder().build()
    }

    constructor(formatStrategy: FormatStrategy) {
        this.formatStrategy = formatStrategy
    }

    override fun isLoggable(priority: Int, tag: String?): Boolean = true

    override fun log(priority: Int, tag: String?, message: String?) {
        formatStrategy.log(priority, tag, message)
    }
}