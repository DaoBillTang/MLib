package com.dtb.core.common.utils

import android.content.Context
import android.content.SharedPreferences


/**
 * @author: dtb
 * @email: wendaoyuqin1@gmail.com
 * @createTime: 19-3-14下午5:18
 */
@SuppressWarnings("all")
class PreTools {
    private val prefs: SharedPreferences

    constructor(context: Context) {
        prefs = context.getSharedPreferences("config", Context.MODE_PRIVATE)
    }

    constructor(context: Context, nameTable: String?) {
        prefs = context.getSharedPreferences(nameTable ?: "config", Context.MODE_PRIVATE)
    }

    fun editor(): SharedPreferences.Editor {
        return prefs.edit()
    }

    fun put(name: String, value: Any?) {
        val editor = prefs.edit()
        put(editor, name, value)
        editor.apply()
    }


    fun put(editor: SharedPreferences.Editor, name: String, value: Any?) {
        with(editor) {
            when (value) {
                is Long -> putLong(name, value)
                is String -> putString(name, value)
                is Int -> putInt(name, value)
                is Boolean -> putBoolean(name, value)
                is Float -> putFloat(name, value)
                else -> return
            }
        }
    }

    /**
     * 获取String的value
     */
    fun getString(key: String): String {
        return prefs.getString(key, "") ?: ""
    }

    fun getBoolean(key: String, defvalue: Boolean = false): Boolean {
        return prefs.getBoolean(key, defvalue)
    }

    fun getInt(key: String): Int {
        return prefs.getInt(key, 0)
    }
}