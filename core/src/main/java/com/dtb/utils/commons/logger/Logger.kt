package com.dtb.utils.commons.logger

import android.util.Log
import com.dtb.utils.commons.basetype.StringExtend
import com.dtb.utils.commons.logger.LoggerPrinter.log
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
fun setLogOutPut(b: Boolean) {
    LoggerPrinter.outPut = b
}

/**
 *2..7
 */
fun setLoggerLevel(int: Int) {
    LoggerPrinter.logLevel = int
}

fun Lverbose(msg: String) {
    log(Log.VERBOSE, null, msg, { priority, thr, msg -> log(priority, thr, msg) })
}

fun Ldebug(msg: String) {
    log(Log.DEBUG, null, msg, { priority, thr, msg -> log(priority, thr, msg) })
}

fun Ldebug(thr: Throwable?, msg: String) {
    log(Log.DEBUG, thr, msg, { priority, thr, msg -> log(priority, thr, msg) })
}

fun Linfo(msg: String) {
    log(Log.INFO, null, msg, { priority, thr, msg -> log(priority, thr, msg) })
}

fun Lwarn(msg: String) {
    log(Log.WARN, null, msg, { priority, thr, msg -> log(priority, thr, msg) })
}

fun Lerror(msg: String, thr: Throwable? = null) {
    log(Log.ERROR, thr, msg, { priority, thr, msg -> log(priority, thr, msg) })
}


fun Lwtf(msg: String) {
    log(Log.ASSERT, null, msg, { priority, thr, msg -> log(priority, thr, msg) })
}

fun Ljson(mjson: String) {
    var json = mjson
    if (json.isBlank()) {
        Ldebug("Empty/Null json content")
        return
    }
    try {
        json = json.trim { it <= ' ' }
        if (json.startsWith("{")) {
            val jsonObject = JSONObject(json)
            val message = jsonObject.toString(JSON_INDENT)
            Ldebug(message)
            return
        }
        if (json.startsWith("[")) {
            val jsonArray = JSONArray(json)
            val message = jsonArray.toString(JSON_INDENT)
            Ldebug(message)
            return
        }
        Lerror("Invalid Json")
    } catch (e: JSONException) {
        Lerror("Invalid Json")
    }
}

fun Lxml(xml: String?) {
    if (xml != null && xml.isBlank()) {
        Ldebug("Empty/Null xml content")
        return
    }
    try {
        val xmlInput = StreamSource(StringReader(xml))
        val xmlOutput = StreamResult(StringWriter())
        val transformer = TransformerFactory.newInstance().newTransformer()
        transformer.setOutputProperty(OutputKeys.INDENT, "yes")
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2")
        transformer.transform(xmlInput, xmlOutput)
        Ldebug(xmlOutput.writer.toString().replaceFirst(">".toRegex(), ">\n"))
    } catch (e: TransformerException) {
        Ldebug("Invalid xml")
    }
}

inline fun Lverbose(msg: () -> Any?) {
    log(Log.VERBOSE, null, msg()?.toString() ?: "null")
}

inline fun Ldebug(msg: () -> Any?) {
    log(Log.DEBUG, null, msg()?.toString() ?: "null")
}

inline fun Linfo(msg: () -> Any?) {
    log(Log.INFO, null, msg()?.toString() ?: "null")
}

inline fun Lwarn(msg: () -> Any?) {
    log(Log.WARN, null, msg()?.toString() ?: "null")
}

inline fun Lerror(msg: () -> Any?) {
    log(Log.ERROR, null, msg()?.toString() ?: "null")
}

inline fun Lwtf(msg: () -> Any?) {
    log(Log.ASSERT, null, msg()?.toString() ?: "null")
}

inline fun Ljson(mjson: () -> Any?) {
    var json = mjson()?.toString()
    if (json == null || json.isBlank()) {
        Ldebug("Empty/Null json content")
        return
    }
    try {
        json = json.trim { it <= ' ' }
        if (json.startsWith("{")) {
            val jsonObject = JSONObject(json)
            val message = jsonObject.toString(JSON_INDENT)
            Ldebug(message)
            return
        }
        if (json.startsWith("[")) {
            val jsonArray = JSONArray(json)
            val message = jsonArray.toString(JSON_INDENT)
            Ldebug(message)
            return
        }
        Lerror("Invalid Json")
    } catch (e: JSONException) {
        Lerror("Invalid Json")
    }
}

inline fun Lxml(mxml: () -> Any?) {
    val xml = mxml()?.toString()
    if (xml == null || xml.isBlank()) {
        Ldebug("Empty/Null xml content")
        return
    }
    try {
        val xmlInput = StreamSource(StringReader(xml))
        val xmlOutput = StreamResult(StringWriter())
        val transformer = TransformerFactory.newInstance().newTransformer()
        transformer.setOutputProperty(OutputKeys.INDENT, "yes")
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2")
        transformer.transform(xmlInput, xmlOutput)
        Ldebug(xmlOutput.writer.toString().replaceFirst(">".toRegex(), ">\n"))
    } catch (e: TransformerException) {
        Ldebug("Invalid xml")
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
    private val androidLogger: PrettyFormatStrategy = PrettyFormatStrategy.newBuilder().build()

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