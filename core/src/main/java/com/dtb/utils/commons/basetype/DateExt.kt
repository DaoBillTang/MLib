package com.dtb.utils.commons.basetype

import org.joda.time.DateTime
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


/**
 * @author: dtb
 * @email: wendaoyuqin1@gmail.com
 * @createTime: 19-3-4下午4:47
 */

val defaultFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())


/**
 * 获取当前当天日期的毫秒数 2012-03-21的毫秒数
 *
 * @return
 */
fun getCurrentDayTime(format: SimpleDateFormat = defaultFormat): Long {
    val d = Date(System.currentTimeMillis())
    val formatDate = format.format(d)
    try {
        return format.parse(formatDate).time
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return 0
}

/**
 * 返回当前系统时间
 */
fun getDataTime(format: String): String {
    val df = SimpleDateFormat(format, Locale.getDefault())
    return df.format(Date())
}

fun formatDate(year: Int, month: Int, day: Int, format: SimpleDateFormat = defaultFormat): String {
    val d = Date(year - 1900, month, day)
    return format.format(d)
}

fun getDateMills(year: Int, month: Int, day: Int): Long {
    //Date d = new Date(year, month, day);
    val calendar = Calendar.getInstance()
    calendar.set(year, month, day)
    calendar.timeZone = TimeZone.getDefault()
    return calendar.timeInMillis
}

fun getActiveTimelong(result: String, format: SimpleDateFormat = defaultFormat): Long {
    try {
        val parse = format.parse(result)
        return parse.time
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    return System.currentTimeMillis()
}

/**
 * 得到当前的年份
 * 返回格式:yyyy
 *
 * @return String
 */
fun getCurrentYear(): String {
    return DateTime().toString("yyyy")
}

/**
 * 得到当前的月份
 * 返回格式:MM
 *
 * @return String
 */
fun getCurrentMonth(): String {
    return DateTime().toString("MM")
}

/**
 * 得到当前的日期
 * 返回格式:dd
 *
 * @return String
 */
fun getCurrentDay(): String {
    return DateTime().toString("dd")
}

/**
 * 得到当前的小时
 * 返回格式:hh
 *
 * @return String
 */
fun getCurrentHour(): String {
    return DateTime().toString("HH")
}

/**
 * 得到当前的分钟
 * 返回格式:mm
 *
 * @return String
 */
fun getCurrentMinute(): String {
    return DateTime().toString("mm")
}

/**
 * 得到当前的秒
 * 返回格式:ss
 *
 * @return String
 */
fun getCurrentSecond(): String {
    return DateTime().toString("ss")
}

/**
 * 得到当前的时间，精确到毫秒,共14位
 * 返回格式:yyyy-MM-dd HH:mm:ss
 *
 * @return String
 */
fun getCurrentTime(): String {
    return DateTime().toString("yyyy-MM-dd HH:mm:ss")
}

/**
 * 得到当前的日期,共10位
 * 返回格式：yyyy-MM-dd
 *
 * @return String
 */
fun getCurrentDate(): String {
    return DateTime().toString("yyyy-MM-dd")
}

/**
 * parse date using default pattern yyyy-MM-dd
 *
 * @param strDate
 * @return
 */
fun parseDate(strDate: String): Date? {
    var date: Date? = null
    try {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        date = dateFormat.parse(strDate)
        return date
    } catch (pe: Exception) {
        return null
    }

}

/**
 * @param strDate
 * @param pattern
 * @return
 */
fun parseDate(strDate: String, pattern: String): Date? {
    val df = SimpleDateFormat(pattern, Locale.getDefault())
    return try {
        df.parse(strDate)
    } catch (pe: Exception) {
        null
    }
}

/**
 * @param date
 * @return formated date by yyyy-MM-dd
 */
fun formatDate(date: Date?): String? {
    return format(date, "yyyy-MM-dd")
}

/**
 * @param date
 * @return formated date by yyyy-MM-dd HH:mm:ss
 */
fun Date?.formatDateTime(pattern: String? = null): String? {
    return if (this == null) {
        null
    } else {
        if (pattern == null) {
            DateTime(this).toString("yyyy-MM-dd HH:mm:ss")
        } else {
            DateTime(this).toString(pattern)
        }
    }
}

/**
 * @param date
 * @param pattern: Date format pattern
 * @return
 */
fun format(date: Date?, pattern: String): String? {
    return if (date == null) {
        null
    } else DateTime(date).toString(pattern)
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

fun isTomorrow(date: Date?): Boolean {
    return if (date == null) {
        false
    } else formatDate(addTime(Date(), 1, 0, 0, 0)) == formatDate(date)

}

/**
 * 获取日期所在月份的第一天
 *
 * @param date
 * @return
 */
fun getMonthFirstDay(date: Date?): Date? {
    if (date == null) {
        return null
    }

    val dateStr = format(date, "yyyy-MM")!! + "-01"
    return parseDate(dateStr)
}

/**
 * 获取日期所在月份的最后一天
 *
 * @param date
 * @return
 */
fun getMonthLastDay(date: Date?): Date? {
    if (date == null) {
        return null
    }

    val c = Calendar.getInstance()
    c.time = date
    val dateStr = format(date, "yyyy-MM") + "-" + c.getActualMaximum(Calendar.DAY_OF_MONTH)
    return parseDate(dateStr)
}

fun getDateDesc(time: Date?): String {
    if (time == null) {
        return ""
    }
    val timeContent: String
    val ss = System.currentTimeMillis() - time.time
    var minute: Long = ss / 60000
    if (minute < 1) {
        minute = 1L
    }
    if (minute >= 60) {
        val hour = minute / 60
        if (hour >= 24) {
            if (hour > 720) {
                timeContent = "1月前"
            } else if (hour > 168 && hour <= 720) {
                timeContent = (hour / 168).toString() + "周前"
            } else {
                timeContent = (hour / 24).toString() + "天前"
            }
        } else {
            timeContent = hour.toString() + "小时前"
        }
    } else {
        timeContent = minute.toString() + "分钟前"
    }
    return timeContent
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

/**
 * 比较src 是否在 dest 之前,true 代表src 小于dest 日期
 *
 * @param src  源日期
 * @param dest 目标日期
 * @param unit 单位
 * 0：年
 * 1：月
 * 2：日
 * 3：时
 * 4：分
 * 5：秒
 * 如果比较分:包含以上值，是包含关系
 */
fun compareIsBefore(src: Date?, dest: Date?, unit: Int): Boolean {
    if (src == null || dest == null) {
        return false
    }
    val format = SimpleDateFormat("yyyyMMddHHmmss")
    val srcStr = format.format(src)
    val destStr = format.format(dest)
    var result = false
    when (unit) {
        0 -> if (java.lang.Long.parseLong(srcStr.substring(0, 4)) < java.lang.Long.parseLong(destStr.substring(0, 4))) {
            result = true
        }
        1 -> if (java.lang.Long.parseLong(srcStr.substring(0, 6)) < java.lang.Long.parseLong(destStr.substring(0, 6))) {
            result = true
        }
        2 -> if (java.lang.Long.parseLong(srcStr.substring(0, 8)) < java.lang.Long.parseLong(destStr.substring(0, 8))) {
            result = true
        }
        3 -> if (java.lang.Long.parseLong(srcStr.substring(0, 10)) < java.lang.Long.parseLong(destStr.substring(0, 10))) {
            result = true
        }
        4 -> if (java.lang.Long.parseLong(srcStr.substring(0, 12)) < java.lang.Long.parseLong(destStr.substring(0, 12))) {
            result = true
        }
        5 -> if (java.lang.Long.parseLong(srcStr.substring(0, 14)) < java.lang.Long.parseLong(destStr.substring(0, 14))) {
            result = true
        }
        else -> result = false
    }

    return result
}

private fun compareIsBefore(date1: String, date2: String): Boolean {
    val df = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault())
    try {
        var dt1: Date? = null
        dt1 = df.parse(date1)
        val dt2 = df.parse(date2)
        return dt1!!.time >= dt2.time
    } catch (e: ParseException) {
        e.printStackTrace()
    } catch (exception: Exception) {
        exception.printStackTrace()
    }

    return false
}

private fun compareIsAfter(date1: String, date2: String): Boolean {
    val df = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault())
    try {
        var dt1: Date? = null
        dt1 = df.parse(date1)
        val dt2 = df.parse(date2)
        return dt1!!.time <= dt2.time
    } catch (e: ParseException) {
        e.printStackTrace()
    } catch (exception: Exception) {
        exception.printStackTrace()
    }

    return false
}

/**
 * 返回某一天是星期几
 *
 * @param date
 * @return
 */
fun getWeek(date: Date): String {
    return DateTime(date).dayOfWeek().asText
}

/**
 * 将时间戳转换成日期字符串
 *
 * @param timestamp
 * @return
 */
fun Long?.timestamp2String(pattern: String? = null): String? {
    if (this == null) {
        return null
    }
    return Date(this).formatDateTime(pattern)
}