package com.daotangbill.exlib.commons.logger

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

/**
 *2..7
 */
fun Any.setLoggerLevel(int: Int) {
    LoggerPrinter.logLevel = int
}

fun DtLogger.verbose(msg: String) {
    log(VERBOSE, null, msg, { priority, thr, msg -> log(priority, thr, msg) })
}

fun Any.Lverbose(msg: String) {
    log(VERBOSE, null, msg, { priority, thr, msg -> log(priority, thr, msg) })
}

fun DtLogger.debug(msg: String) {
    log(DEBUG, null, msg, { priority, thr, msg -> log(priority, thr, msg) })
}

fun Any.Ldebug(msg: String) {
    log(DEBUG, null, msg, { priority, thr, msg -> log(priority, thr, msg) })
}

fun DtLogger.debug(thr: Throwable?, msg: String) {
    log(DEBUG, thr, msg, { priority, thr, msg -> log(priority, thr, msg) })
}

fun Any.Ldebug(thr: Throwable?, msg: String) {
    log(DEBUG, thr, msg, { priority, thr, msg -> log(priority, thr, msg) })
}

fun DtLogger.info(msg: String) {
    log(INFO, null, msg, { priority, thr, msg -> log(priority, thr, msg) })
}


fun Any.Linfo(msg: String) {
    log(INFO, null, msg, { priority, thr, msg -> log(priority, thr, msg) })
}

fun DtLogger.warn(msg: String) {
    log(WARN, null, msg, { priority, thr, msg -> log(priority, thr, msg) })
}

fun Any.Lwarn(msg: String) {
    log(WARN, null, msg, { priority, thr, msg -> log(priority, thr, msg) })
}

fun DtLogger.error(msg: String) {
    log(ERROR, null, msg, { priority, thr, msg -> log(priority, thr, msg) })
}

fun Any.Lerror(msg: String) {
    log(ERROR, null, msg, { priority, thr, msg -> log(priority, thr, msg) })
}

fun DtLogger.error(thr: Throwable?, msg: String) {
    log(ERROR, thr, msg, { priority, thr, msg -> log(priority, thr, msg) })
}

fun Any.Lerror(thr: Throwable?, msg: String) {
    log(ERROR, thr, msg, { priority, thr, msg -> log(priority, thr, msg) })
}

fun DtLogger.wtf(msg: String) {
    log(ASSERT, null, msg, { priority, thr, msg -> log(priority, thr, msg) })
}

fun Any.Lwtf(msg: String) {
    log(ASSERT, null, msg, { priority, thr, msg -> log(priority, thr, msg) })
}

fun DtLogger.json(mjson: String) {
    var json = mjson
    if (json.isBlank()) {
        this.debug("Empty/Null json content")
        return
    }
    try {
        json = json.trim { it <= ' ' }
        if (json.startsWith("{")) {
            val jsonObject = JSONObject(json)
            val message = jsonObject.toString(JSON_INDENT)
            this.debug(message)
            return
        }
        if (json.startsWith("[")) {
            val jsonArray = JSONArray(json)
            val message = jsonArray.toString(JSON_INDENT)
            this.debug(message)
            return
        }
        this.error("Invalid Json")
    } catch (e: JSONException) {
        this.error("Invalid Json")
    }
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

fun DtLogger.xml(xml: String?) {
    if (xml != null && xml.isBlank()) {
        this.debug("Empty/Null xml content")
        return
    }
    try {
        val xmlInput = StreamSource(StringReader(xml))
        val xmlOutput = StreamResult(StringWriter())
        val transformer = TransformerFactory.newInstance().newTransformer()
        transformer.setOutputProperty(OutputKeys.INDENT, "yes")
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2")
        transformer.transform(xmlInput, xmlOutput)
        this.debug(xmlOutput.writer.toString().replaceFirst(">".toRegex(), ">\n"))
    } catch (e: TransformerException) {
        this.debug("Invalid xml")
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

inline fun DtLogger.verbose(msg: () -> Any?) {
    log(VERBOSE, null, msg()?.toString() ?: "null")
}

inline fun Any.Lverbose(msg: () -> Any?) {
    log(VERBOSE, null, msg()?.toString() ?: "null")
}

inline fun DtLogger.debug(msg: () -> Any?) {
    log(DEBUG, null, msg()?.toString() ?: "null")
}

inline fun Any.Ldebug(msg: () -> Any?) {
    log(DEBUG, null, msg()?.toString() ?: "null")
}

inline fun DtLogger.info(msg: () -> Any?) {
    log(INFO, null, msg()?.toString() ?: "null")
}

inline fun Any.Linfo(msg: () -> Any?) {
    log(INFO, null, msg()?.toString() ?: "null")
}

inline fun DtLogger.warn(msg: () -> Any?) {
    log(WARN, null, msg()?.toString() ?: "null")
}

inline fun Any.Lwarn(msg: () -> Any?) {
    log(WARN, null, msg()?.toString() ?: "null")
}

inline fun DtLogger.error(msg: () -> Any?) {
    log(ERROR, null, msg()?.toString() ?: "null")
}

inline fun Any.Lerror(msg: () -> Any?) {
    log(ERROR, null, msg()?.toString() ?: "null")
}

inline fun DtLogger.wtf(msg: () -> Any?) {
    log(ASSERT, null, msg()?.toString() ?: "null")
}

inline fun Any.Lwtf(msg: () -> Any?) {
    log(ASSERT, null, msg()?.toString() ?: "null")
}

inline fun DtLogger.json(mjson: () -> Any?) {
    var json = mjson()?.toString()
    if (json == null || json.isBlank()) {
        this.debug("Empty/Null json content")
        return
    }
    try {
        json = json.trim { it <= ' ' }
        if (json.startsWith("{")) {
            val jsonObject = JSONObject(json)
            val message = jsonObject.toString(JSON_INDENT)
            this.debug(message)
            return
        }
        if (json.startsWith("[")) {
            val jsonArray = JSONArray(json)
            val message = jsonArray.toString(JSON_INDENT)
            this.debug(message)
            return
        }
        this.error("Invalid Json")
    } catch (e: JSONException) {
        this.error("Invalid Json")
    }
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

inline fun DtLogger.xml(mxml: () -> Any?) {
    val xml = mxml()?.toString()
    if (xml == null || xml.isBlank()) {
        this.debug("Empty/Null xml content")
        return
    }
    try {
        val xmlInput = StreamSource(StringReader(xml))
        val xmlOutput = StreamResult(StringWriter())
        val transformer = TransformerFactory.newInstance().newTransformer()
        transformer.setOutputProperty(OutputKeys.INDENT, "yes")
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2")
        transformer.transform(xmlInput, xmlOutput)
        this.debug(xmlOutput.writer.toString().replaceFirst(">".toRegex(), ">\n"))
    } catch (e: TransformerException) {
        this.debug("Invalid xml")
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
    private val androidLogger = AndroidLogAdapter()

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