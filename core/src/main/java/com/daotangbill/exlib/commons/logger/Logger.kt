package com.daotangbill.exlib.commons.logger

import android.util.Log
import com.daotangbill.exlib.commons.logger.LoggerPrinter.log
import com.daotangbill.exlib.commons.utils.StringExtend
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

const val JSON_INDENT = 2

/**
 * 是否设置
 */
fun Any.setLogOutPut(b: Boolean) {
    LoggerPrinter.outPut = b
}

/**
 *2..7
 */
fun Any.setLoggerLevel(int: Int) {
    LoggerPrinter.logLevel = int
}

fun Any.Lverbose(msg: String) {
    log(Log.VERBOSE, null, msg, { priority, thr, msg -> log(priority, thr, msg) })
}

fun Any.Ldebug(msg: String) {
    log(Log.DEBUG, null, msg, { priority, thr, msg -> log(priority, thr, msg) })
}

fun Any.Ldebug(thr: Throwable?, msg: String) {
    log(Log.DEBUG, thr, msg, { priority, thr, msg -> log(priority, thr, msg) })
}

fun Any.Linfo(msg: String) {
    log(Log.INFO, null, msg, { priority, thr, msg -> log(priority, thr, msg) })
}

fun Any.Lwarn(msg: String) {
    log(Log.WARN, null, msg, { priority, thr, msg -> log(priority, thr, msg) })
}

fun Any.Lerror(msg: String) {
    log(Log.ERROR, null, msg, { priority, thr, msg -> log(priority, thr, msg) })
}

fun Any.Lerror(thr: Throwable?, msg: String) {
    log(Log.ERROR, thr, msg, { priority, thr, msg -> log(priority, thr, msg) })
}

fun Any.Lwtf(msg: String) {
    log(Log.ASSERT, null, msg, { priority, thr, msg -> log(priority, thr, msg) })
}

fun Any.Ljson(mjson: String) {
    var json = mjson
    if (json.isBlank()) {
        this.Ldebug("Empty/Null json content")
        return
    }
    try {
        json = json.trim { it <= ' ' }
        if (json.startsWith("{")) {
            val jsonObject = JSONObject(json)
            val message = jsonObject.toString(JSON_INDENT)
            this.Ldebug(message)
            return
        }
        if (json.startsWith("[")) {
            val jsonArray = JSONArray(json)
            val message = jsonArray.toString(JSON_INDENT)
            this.Ldebug(message)
            return
        }
        this.Lerror("Invalid Json")
    } catch (e: JSONException) {
        this.Lerror("Invalid Json")
    }
}

fun Any.Lxml(xml: String?) {
    if (xml != null && xml.isBlank()) {
        this.Ldebug("Empty/Null xml content")
        return
    }
    try {
        val xmlInput = StreamSource(StringReader(xml))
        val xmlOutput = StreamResult(StringWriter())
        val transformer = TransformerFactory.newInstance().newTransformer()
        transformer.setOutputProperty(OutputKeys.INDENT, "yes")
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2")
        transformer.transform(xmlInput, xmlOutput)
        this.Ldebug(xmlOutput.writer.toString().replaceFirst(">".toRegex(), ">\n"))
    } catch (e: TransformerException) {
        this.Ldebug("Invalid xml")
    }
}

inline fun Any.Lverbose(msg: () -> Any?) {
    log(Log.VERBOSE, null, msg()?.toString() ?: "null")
}

inline fun Any.Ldebug(msg: () -> Any?) {
    log(Log.DEBUG, null, msg()?.toString() ?: "null")
}

inline fun Any.Linfo(msg: () -> Any?) {
    log(Log.INFO, null, msg()?.toString() ?: "null")
}

inline fun Any.Lwarn(msg: () -> Any?) {
    log(Log.WARN, null, msg()?.toString() ?: "null")
}

inline fun Any.Lerror(msg: () -> Any?) {
    log(Log.ERROR, null, msg()?.toString() ?: "null")
}

inline fun Any.Lwtf(msg: () -> Any?) {
    log(Log.ASSERT, null, msg()?.toString() ?: "null")
}

inline fun Any.Ljson(mjson: () -> Any?) {
    var json = mjson()?.toString()
    if (json == null || json.isBlank()) {
        this.Ldebug("Empty/Null json content")
        return
    }
    try {
        json = json.trim { it <= ' ' }
        if (json.startsWith("{")) {
            val jsonObject = JSONObject(json)
            val message = jsonObject.toString(JSON_INDENT)
            this.Ldebug(message)
            return
        }
        if (json.startsWith("[")) {
            val jsonArray = JSONArray(json)
            val message = jsonArray.toString(JSON_INDENT)
            this.Ldebug(message)
            return
        }
        this.Lerror("Invalid Json")
    } catch (e: JSONException) {
        this.Lerror("Invalid Json")
    }
}

inline fun Any.Lxml(mxml: () -> Any?) {
    val xml = mxml()?.toString()
    if (xml == null || xml.isBlank()) {
        this.Ldebug("Empty/Null xml content")
        return
    }
    try {
        val xmlInput = StreamSource(StringReader(xml))
        val xmlOutput = StreamResult(StringWriter())
        val transformer = TransformerFactory.newInstance().newTransformer()
        transformer.setOutputProperty(OutputKeys.INDENT, "yes")
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2")
        transformer.transform(xmlInput, xmlOutput)
        this.Ldebug(xmlOutput.writer.toString().replaceFirst(">".toRegex(), ">\n"))
    } catch (e: TransformerException) {
        this.Ldebug("Invalid xml")
    }
}

private inline fun log(priority: Int, thr: Throwable?, msg: String,
                       f: (priority: Int, thr: Throwable?, msg: String) -> Unit) {
    f(priority, thr, msg)
}

object LoggerPrinter {
    var outPut = true
    var logLevel = 1

    private val localTag = ThreadLocal<String>()
    private val androidLogger: PrettyFormatStrategy =PrettyFormatStrategy.newBuilder().build()

    @Synchronized
    fun log(priority: Int, tag: String?, msg: String?, throwable: Throwable?) {
        var message = msg
        if (throwable != null && message != null) {
            message += " : " + StringExtend.getStackTraceString(throwable)
        }
        if (throwable != null && message == null) {
            message = StringExtend.getStackTraceString(throwable)
        }
        if (StringExtend.isEmpty(message)) {
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
        if (priority < logLevel) {
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