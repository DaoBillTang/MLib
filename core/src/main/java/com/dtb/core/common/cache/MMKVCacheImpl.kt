package com.dtb.core.common.cache

import com.dtb.core.common.cache.contract.ICache
import com.tencent.mmkv.MMKV


class MMKVCacheImpl : ICache {

    override fun getString(key: String, def: String?): String? =
        MMKV.defaultMMKV().getString(key, def)

    override fun getInt(key: String, def: Int): Int = MMKV.defaultMMKV().getInt(key, def)

    override fun getBool(key: String, def: Boolean): Boolean =
        MMKV.defaultMMKV().getBoolean(key, def)

    override fun getFloat(key: String, defValue: Float): Float =
        MMKV.defaultMMKV().getFloat(key, defValue)

    override fun getLong(key: String, defValue: Long): Long =
        MMKV.defaultMMKV().getLong(key, defValue)

    override fun putLong(key: String, value: Long) {
        MMKV.defaultMMKV().putLong(key, value)
    }

    override fun putString(key: String, value: String) {
        MMKV.defaultMMKV().putString(key, value)
    }

    override fun putInt(key: String, value: Int) {
        MMKV.defaultMMKV().putInt(key, value)
    }

    override fun putBoolean(key: String, value: Boolean) {
        MMKV.defaultMMKV().putBoolean(key, value)
    }

    override fun putFloat(key: String, value: Float) {
        MMKV.defaultMMKV().putFloat(key, value)
    }

    override fun remove(key: String): Boolean? {
        MMKV.defaultMMKV().remove(key)
        return true
    }

}