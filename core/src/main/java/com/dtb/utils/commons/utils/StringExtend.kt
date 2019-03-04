package com.dtb.utils.commons.utils

import android.text.Editable
import java.io.PrintWriter
import java.io.StringWriter
import java.net.UnknownHostException
import java.util.regex.Pattern
import java.util.regex.PatternSyntaxException


/**
 * Project hschefu-android
 * Created by BILL on 2017/5/19.
 * email:tangbaozi@daotangbill.uu.me
 *
 * @author BILL
 * @version 1.0
 * 一些 基础的string 的工具
 */
object StringExtend {
    @JvmStatic
    fun isEmpty(str: CharSequence?): Boolean = str == null || str.isEmpty()

    @JvmStatic
    fun equals(a: CharSequence?, b: CharSequence?): Boolean {
        if (a === b) return true
        if (a != null && b != null) {
            val length = a.length
            if (length == b.length) {
                return if (a is String && b is String) {
                    a == b
                } else {
                    (0 until length).none { a[it] != b[it] }
                }
            }
        }
        return false
    }

    @JvmStatic
    fun getStackTraceString(tr: Throwable?): String {
        if (tr == null) {
            return ""
        }

        var t = tr
        while (t != null) {
            if (t is UnknownHostException) {
                return ""
            }
            t = t.cause
        }

        val sw = StringWriter()
        val pw = PrintWriter(sw)
        tr.printStackTrace(pw)
        pw.flush()
        return sw.toString()
    }
}


/**
 *对于 输入小数的时候 ，保留两位小数，并且判断小数点等等
 * 已知bug:小数点数量过多
 */
fun String.formatTime(): String = this.substring(0, 7).
        replaceFirst("-", "年").
        replaceFirst("-", "月")

fun Editable?.judgeNumber(intNum: Int, decimalNum: Int): String? {
    if (this == null) return null
    val temp = this.toString()

    if (this.startsWith(".")) {
        return null
    }

    if (this.startsWith("0")) {
        if (!this.startsWith("0.")) {
            return "0"
        }
    }

    val posDot = temp.indexOf(".")//返回指定字符在此字符串中第一次出现处的索引
    if (posDot <= 0) {//不包含小数点
        if (temp.length <= intNum) {
            //小于五位数直接返回
            return this.toString()
        } else {
            this.delete(intNum, intNum + 1)//大于五位数就删掉第六位（只会保留五位）
            return this.toString()
        }
    }
    val otherDot = temp.indexOf(".", posDot + 1)
    if (otherDot >= 0) {
        this.delete(otherDot, otherDot + 1)
        return this.toString()
    }
    if (temp.length - posDot - 1 > decimalNum) {   //如果包含小数点
        this.delete(posDot + decimalNum + 1, posDot + decimalNum + 2)//删除小数点后的第三位
        return this.toString()
    }
    return this.toString()
}

fun CharSequence?.judgeNumber(intNum: Int, decimalNum: Int): String? =
        (this as Editable).judgeNumber(intNum, decimalNum)

@Throws(PatternSyntaxException::class)
fun String?.stringFilter(regEx: String): String? {
    // 仅仅同意字母、数字和汉字
    val p = Pattern.compile(regEx)
    val m = p.matcher(this)
    return m?.replaceAll("")?.trim()
}