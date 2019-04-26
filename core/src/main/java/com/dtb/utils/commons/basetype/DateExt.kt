package com.dtb.utils.commons.basetype

import org.joda.time.DateTime
import java.util.*

/**
 * @author: dtb
 * @email: wendaoyuqin1@gmail.com
 * @createTime: 19-3-4下午4:47
 * @ver 0.2: 去除了部分关于 原始 Date 的相关方法
 *
 */
fun now(format: String = "yyyy-MM-dd HH:mm:ss"): String {
    return DateTime.now().toString(format)
}

/**
 * @param original
 * @param days
 * @param hours
 * @param minutes
 * @param seconds
 * @return original+day+hour+minutes+seconds
 */
fun addTime(original: Date?, days: Int, hours: Int, minutes: Int, seconds: Int): Date? {
    return if (original == null) {
        null
    } else DateTime(original).plusDays(days).plusHours(hours).plusMinutes(minutes).plusSeconds(seconds).toDate()
}

fun addYear(original: Date?, years: Int): Date? {
    return if (original == null) {
        null
    } else DateTime(original).plusYears(years).toDate()

}

fun addMonth(original: Date?, months: Int): Date? {
    return if (original == null) {
        null
    } else DateTime(original).plusMonths(months).toDate()

}

fun addWeek(original: Date?, weeks: Int): Date? {
    return if (original == null) {
        null
    } else DateTime(original).plusWeeks(weeks).toDate()

}

fun addDay(original: Date?, days: Int): Date? {
    return if (original == null) {
        null
    } else DateTime(original).plusDays(days).toDate()
}

fun addHour(original: Date?, hours: Int): Date? {
    return if (original == null) {
        null
    } else DateTime(original).plusHours(hours).toDate()

}

fun addMinute(original: Date?, minutes: Int): Date? {
    return if (original == null) {
        null
    } else DateTime(original).plusMinutes(minutes).toDate()

}

fun addSecond(original: Date?, second: Int): Date? {
    return if (original == null) {
        null
    } else DateTime(original).plusSeconds(second).toDate()

}

/**
 * 判断是否为闰年
 *
 * @param year
 * @return
 */
fun isLeapYear(year: Int): Boolean {
    return if (year % 100 == 0) {
        year % 400 == 0
    } else year % 4 == 0
}

/**
 * 日期字符串转换成Calendar
 *
 * @param strDate
 * @return
 */
fun string2Calendar(strDate: String): Calendar {
    return DateTime(strDate).toCalendar(Locale.CHINA)
}
