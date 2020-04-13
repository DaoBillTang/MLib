package com.dtb.core

object Internals {


    internal fun noGetter():Nothing {
        throw RuntimeException("该方法没有get")
    }
}