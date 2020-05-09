package com.dtb.core.common.cache.contract

import android.content.Context
import java.lang.reflect.Type
import java.util.*

interface ISerialize {
    fun init(context: Context)

    /**
     *
     */
    fun <T> serialize2Object(input: String, clazz: Class<T>): T

    /**
     * Serialize ``obj`` to a String
     */
    fun object2serialize(instance: Any): String
}