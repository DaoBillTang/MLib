package com.daotangbill.dt_ext.exlib.commons.logger

import com.daotangbill.dt_ext.exlib.commons.logger.LoggerPrinter.log
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.StringReader
import java.io.StringWriter
import javax.xml.transform.OutputKeys
import javax.xml.transform.TransformerException
import javax.xml.transform.TransformerFactory
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource

interface DtLogger {
//    这是所有的 log的前提
}

const val JSON_INDENT = 2

const val VERBOSE = 2
const val DEBUG = 3
const val INFO = 4
const val WARN = 5
const val ERROR = 6
const val ASSERT = 7

fun DtLogger.outPut(b: Boolean) {
    LoggerPrinter.outPut = b
}

fun DtLogger.v(msg: String) {
    log(VERBOSE, null, msg, { priority, thr, msg -> log(priority, thr, msg) })
}

fun DtLogger.d(msg: String) {
    log(DEBUG, null, msg, { priority, thr, msg -> log(priority, thr, msg) })
}

fun DtLogger.d(thr: Throwable?, msg: String) {
    log(DEBUG, thr, msg, { priority, thr, msg -> log(priority, thr, msg) })
}

fun DtLogger.i(msg: String) {
    log(INFO, null, msg, { priority, thr, msg -> log(priority, thr, msg) })
}

fun DtLogger.w(msg: String) {
    log(WARN, null, msg, { priority, thr, msg -> log(priority, thr, msg) })
}

fun DtLogger.e(msg: String) {
    log(ERROR, null, msg, { priority, thr, msg -> log(priority, thr, msg) })
}

fun DtLogger.e(thr: Throwable?, msg: String) {
    log(ERROR, thr, msg, { priority, thr, msg -> log(priority, thr, msg) })
}

fun DtLogger.wtf(msg: String) {
    log(ASSERT, null, msg, { priority, thr, msg -> log(priority, thr, msg) })
}

fun DtLogger.json(mjson: String) {
    var json = mjson
    if (json.isBlank()) {
        this.d("Empty/Null json content")
        return
    }
    try {
        json = json.trim { it <= ' ' }
        if (json.startsWith("{")) {
            val jsonObject = JSONObject(json)
            val message = jsonObject.toString(JSON_INDENT)
            this.d(message)
            return
        }
        if (json.startsWith("[")) {
            val jsonArray = JSONArray(json)
            val message = jsonArray.toString(JSON_INDENT)
            this.d(message)
            return
        }
        this.e("Invalid Json")
    } catch (e: JSONException) {
        this.e("Invalid Json")
    }
}

fun DtLogger.xml(xml: String?) {
    if (xml != null && xml.isBlank()) {
        this.d("Empty/Null xml content")
        return
    }
    try {
        val xmlInput = StreamSource(StringReader(xml))
        val xmlOutput = StreamResult(StringWriter())
        val transformer = TransformerFactory.newInstance().newTransformer()
        transformer.setOutputProperty(OutputKeys.INDENT, "yes")
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2")
        transformer.transform(xmlInput, xmlOutput)
        this.d(xmlOutput.writer.toString().replaceFirst(">".toRegex(), ">\n"))
    } catch (e: TransformerException) {
        this.e("Invalid xml")
    }
}

inline fun DtLogger.v(msg: () -> Any?) {
    log(VERBOSE, null, msg()?.toString() ?: "null")

}

inline fun DtLogger.d(msg: () -> Any?) {
    log(DEBUG, null, msg()?.toString() ?: "null")
}

inline fun DtLogger.i(msg: () -> Any?) {
    log(INFO, null, msg()?.toString() ?: "null")
}

inline fun DtLogger.w(msg: () -> Any?) {
    log(WARN, null, msg()?.toString() ?: "null")
}

inline fun DtLogger.e(msg: () -> Any?) {
    log(ERROR, null, msg()?.toString() ?: "null")
}

inline fun DtLogger.wtf(msg: () -> Any?) {
    log(ASSERT, null, msg()?.toString() ?: "null")
}

inline fun DtLogger.json(mjson: () -> Any?) {
    var json = mjson()?.toString()
    if (json == null || json.isBlank()) {
        this.d("Empty/Null json content")
        return
    }
    try {
        json = json.trim { it <= ' ' }
        if (json.startsWith("{")) {
            val jsonObject = JSONObject(json)
            val message = jsonObject.toString(JSON_INDENT)
            this.d(message)
            return
        }
        if (json.startsWith("[")) {
            val jsonArray = JSONArray(json)
            val message = jsonArray.toString(JSON_INDENT)
            this.d(message)
            return
        }
        this.e("Invalid Json")
    } catch (e: JSONException) {
        this.e("Invalid Json")
    }
}

inline fun DtLogger.xml(mxml: () -> Any?) {
    val xml = mxml()?.toString()
    if (xml == null || xml.isBlank()) {
        this.d("Empty/Null xml content")
        return
    }
    try {
        val xmlInput = StreamSource(StringReader(xml))
        val xmlOutput = StreamResult(StringWriter())
        val transformer = TransformerFactory.newInstance().newTransformer()
        transformer.setOutputProperty(OutputKeys.INDENT, "yes")
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2")
        transformer.transform(xmlInput, xmlOutput)
        this.d(xmlOutput.writer.toString().replaceFirst(">".toRegex(), ">\n"))
    } catch (e: TransformerException) {
        this.e("Invalid xml")
    }
}

private inline fun log(priority: Int, thr: Throwable?, msg: String,
                       f: (priority: Int, thr: Throwable?, msg: String) -> Unit) {
    f(priority, thr, msg)
}

object LoggerPrinter {
    var outPut = true

    private val localTag = ThreadLocal<String>()
    private val androidLogger = AndroidLogAdapter()

    @Synchronized
    fun log(priority: Int, tag: String?, msg: String?, throwable: Throwable?) {
        var message = msg
        if (throwable != null && message != null) {
            message += " : " + Utils.getStackTraceString(throwable)
        }
        if (throwable != null && message == null) {
            message = Utils.getStackTraceString(throwable)
        }
        if (Utils.isEmpty(message)) {
            message = "Empty/NULL log message"
        }
        androidLogger.log(priority, tag, message)
    }

    /**
     * 第一次调用的方法
     */
    @Synchronized
    fun log(priority: Int, throwable: Throwable?, msg: String) {
        if (!outPut) {
            return
        }
        val tag = tag
        log(priority, tag, msg, throwable)
    }

    private val tag: String?
        get() {
            val tag = localTag.get()
            if (tag != null) {
                localTag.remove()
                return tag
            }
            return null
        }
}