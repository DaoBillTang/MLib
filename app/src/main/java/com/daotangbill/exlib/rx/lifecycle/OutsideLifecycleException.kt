package com.daotangbill.exlib.rx.lifecycle

/**
 * 超出 生命周期的错误
 */
class OutsideLifecycleException(detailMessage: String?) : IllegalStateException(detailMessage)