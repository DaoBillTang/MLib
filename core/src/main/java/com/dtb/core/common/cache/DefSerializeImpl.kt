package com.dtb.core.common.cache

import android.content.Context
import com.dtb.core.common.cache.contract.ISerialize
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class DefSerializeImpl : ISerialize {
    override fun init(context: Context) {
    }

    override fun <T> serialize2Object(input: String, clazz: Class<T>): T {
        val redStr = java.net.URLDecoder.decode(input, "UTF-8")
        ByteArrayInputStream(
            redStr.toByteArray(charset("ISO-8859-1"))
        ).use { bais ->
            ObjectInputStream(bais).use {
                return it.readObject() as T
            }
        }
    }

    override fun object2serialize(instance: Any): String {
        ByteArrayOutputStream()
            .use { byteArrayOutputStream ->
                ObjectOutputStream(
                    byteArrayOutputStream
                ).use { objectOutputStream ->
                    objectOutputStream.writeObject(instance)
                    var serStr = byteArrayOutputStream.toString("ISO-8859-1")
                    serStr = java.net.URLEncoder.encode(serStr, "UTF-8")
                    return serStr
                }
            }
    }

}