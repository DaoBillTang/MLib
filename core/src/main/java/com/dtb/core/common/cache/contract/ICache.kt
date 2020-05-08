package com.dtb.core.common.cache.contract

/**
 *  android 缓存的接口
 */
interface ICache {
    fun getString(key: String, def: String?): String?
    fun getInt(key: String, def: Int): Int
    fun getBool(key: String, def: Boolean): Boolean
    fun getFloat(key: String, defValue: Float): Float
    fun getLong(key: String, defValue: Long): Long

    fun putLong(key: String, value: Long)
    fun putString(key: String, value: String)
    fun putInt(key: String, value: Int)
    fun putBoolean(key: String, value: Boolean)
    fun putFloat(key: String, value: Float)

    fun remove(key: String): Boolean?
}