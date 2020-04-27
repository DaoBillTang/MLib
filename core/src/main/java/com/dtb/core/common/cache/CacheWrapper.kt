package com.dtb.core.common.cache

import com.dtb.core.common.cache.contract.ICache
import com.dtb.core.common.cache.contract.ISerialize
import java.lang.NullPointerException

abstract class CacheWrapper(val cache: ICache) : ICache {
    var jsonImpl: ISerialize? = null
        get() {
            if (field == null) {
                field = DefSerializeImpl()
            }
            return field
        }

    constructor(cache: ICache, jsonImpl: DefSerializeImpl) : this(cache) {
        this.jsonImpl = jsonImpl
    }

    private fun t1() {
        val a = getValue("s", 2)
    }

    /**
     * 这里暂时还没有实现getObj 的方法
     */
    fun <T> getValue(key: String, default: T): T {
        val res: Any? = when (default) {
            is Long -> getLong(key, default)
            is String -> getString(key, default)
            is Int -> getInt(key, default)
            is Boolean -> getBool(key, default)
            is Float -> getFloat(key, default)
            else -> default
        }
        return res as T
    }

    /**
     * 由于 编译器的问题，这个方法编译不过
     */
    fun <T> getObj(key: String, def: T): T? {
//        if (def == null) {
//            throw NullPointerException("def is non-nullable")
//        }
//        val t = def!!::class.java
//        return getObj<T>(key, t) ?: def
        return null
    }

    fun <T> getObj(key: String, clazz: Class<out T>): T? {
        val s = cache.getString(key, null)
        return if (s != null) {
            jsonImpl?.serialize2Object(key, clazz)
        } else {
            null
        }
    }

    fun putValue(key: String, value: Any) {
        when (value) {
            is Long -> putLong(key, value)
            is String -> putString(key, value)
            is Int -> putInt(key, value)
            is Boolean -> putBoolean(key, value)
            is Float -> putFloat(key, value)
            else -> putString(key, jsonImpl?.object2serialize(value) ?: "")
        }
    }

    fun putJsonRes(key: String, instance: Any): Boolean {
        val s = jsonImpl?.object2serialize(instance)
        return if (s != null) {
            cache.putString(key, s)
            true
        } else {
            false
        }
    }

    override fun getString(key: String, def: String?): String? {
        return cache.getString(key, def)
    }

    override fun getInt(key: String, def: Int): Int {
        return cache.getInt(key, def)
    }

    override fun getBool(key: String, def: Boolean): Boolean {
        return cache.getBool(key, def)
    }

    override fun getFloat(key: String, defValue: Float): Float {
        return cache.getFloat(key, defValue)
    }

    override fun getLong(key: String, defValue: Long): Long {
        return cache.getLong(key, defValue)
    }


    override fun putLong(key: String, value: Long) {
        cache.putLong(key, value)
    }

    override fun putString(key: String, value: String) {
        cache.putString(key, value)
    }

    override fun putInt(key: String, value: Int) {
        cache.putInt(key, value)
    }

    override fun putBoolean(key: String, value: Boolean) {
        cache.putBoolean(key, value)
    }

    override fun putFloat(key: String, value: Float) {
        cache.putFloat(key, value)
    }

    override fun remove(key: String): Boolean? {
        return cache.remove(key)
    }
}