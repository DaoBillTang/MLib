package com.dtb.core.common.cache

import android.content.Context
import android.content.SharedPreferences
import com.dtb.core.common.cache.contract.ICache


/**
 * Project com.daotangbill.exlib.commons.utils
 * Created by DaoTangBill on 2020/2/23/023.
 * Email:tangbaozi@daotangbill.uu.me
 *
 * @author bill
 * @version 1.0
 * @description
 *
 */
class PreferenceCacheImpl(context: Context, nameTable: String?) : ICache {
    private val prefs: SharedPreferences =
        context.getSharedPreferences(nameTable ?: "config", Context.MODE_PRIVATE)

    override fun getString(key: String, def: String?): String? = with(prefs) {
        this.getString(key, def)
    }

    override fun getInt(key: String, def: Int): Int = with(prefs) {
        this.getInt(key, def)
    }

    override fun getBool(key: String, def: Boolean): Boolean = with(prefs) {
        this.getBoolean(key, def)
    }

    override fun getFloat(key: String, defValue: Float): Float = with(prefs) {
        this.getFloat(key, defValue)
    }

    override fun getLong(key: String, defValue: Long): Long= with(prefs) {
        this.getLong(key, defValue)
    }

    override fun putLong(key: String, value: Long) = with(prefs.edit()) {
        putLong(key, value)
    }.apply()

    override fun putString(key: String, value: String) = with(prefs.edit()) {
        this.putString(key, value)
    }.apply()

    override fun putInt(key: String, value: Int) = with(prefs.edit()) {
        this.putInt(key, value)
    }.apply()

    override fun putBoolean(key: String, value: Boolean) = with(prefs.edit()) {
        this.putBoolean(key, value)
    }.apply()

    override fun putFloat(key: String, value: Float) = with(prefs.edit()) {
        this.putFloat(key, value)
    }.apply()

    override fun remove(key: String): Boolean? = prefs.edit().remove(key).commit()
}