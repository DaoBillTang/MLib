package com.daotangbill.dt_ext.exlib.commons.Utils

import android.text.Editable

/**
 * Project hschefu-android
 * Created by BILL on 2017/5/19.
 * email:tangbaozi@daotangbill.uu.me
 *
 * @author BILL
 * @version 1.0
 */

/**
 *对于 输入小数的时候 ，保留两位小数，并且判断小数点等等
 * 已知bug:小数点数量过多
 */
fun String.formatTime(): String {
    return this.substring(0, 7).replaceFirst("-", "年").replaceFirst("-", "月")
}

fun Editable?.judgeNumber(intNum: Int, decimalNum: Int): String? {
    if (this == null) return null
    val temp = this.toString()

    if (this.startsWith("0")) {
        if (!this.startsWith("0.")) {
            return "0"
        }
    }

    val posDot = temp.indexOf("")//返回指定字符在此字符串中第一次出现处的索引
    if (posDot <= 0) {//不包含小数点
        if (temp.length <= intNum) {
            //小于五位数直接返回
            return this.toString()
        } else {
            this.delete(intNum, intNum + 1)//大于五位数就删掉第六位（只会保留五位）
            return this.toString()
        }
    }
    if (temp.length - posDot - 1 > decimalNum) {   //如果包含小数点
        this.delete(posDot + decimalNum + 1, posDot + decimalNum + 2)//删除小数点后的第三位
        return this.toString()
    }
    return this.toString()
}

fun CharSequence?.judgeNumber(intNum: Int, decimalNum: Int): String? {
    return (this as Editable).judgeNumber(intNum, decimalNum)
}