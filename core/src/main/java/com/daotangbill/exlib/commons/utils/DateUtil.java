package com.daotangbill.exlib.commons.utils;

import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 时间转换工具类
 */
public class DateUtil {

    public static final SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    private static final long ONEDAY = 86400000;
    public static final int SHOW_TYPE_SIMPLE = 0;
    public static final int SHOW_TYPE_COMPLEX = 1;
    public static final int SHOW_TYPE_ALL = 2;
    public static final int SHOW_TYPE_CALL_LOG = 3;
    public static final int SHOW_TYPE_CALL_DETAIL = 4;

    /**
     * 获取当前当天日期的毫秒数 2012-03-21的毫秒数
     *
     * @return
     */
    public static long getCurrentDayTime() {
        Date d = new Date(System.currentTimeMillis());
        String formatDate = yearFormat.format(d);
        try {
            return (yearFormat.parse(formatDate)).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 返回当前系统时间
     */
    public static String getDataTime(String format) {
        SimpleDateFormat df = new SimpleDateFormat(format, Locale.getDefault());
        return df.format(new Date());
    }

    public static String formatDate(int year, int month, int day) {
        Date d = new Date(year - 1900, month, day);
        return yearFormat.format(d);
    }

    public static long getDateMills(int year, int month, int day) {
        //Date d = new Date(year, month, day);
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        calendar.setTimeZone(TimeZone.getDefault());
        return calendar.getTimeInMillis();
    }

    public static long getActiveTimelong(String result) {
        try {
            Date parse = yearFormat.parse(result);
            return parse.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return System.currentTimeMillis();
    }

    /**
     * 得到当前的年份
     * 返回格式:yyyy
     *
     * @return String
     */
    public static String getCurrentYear() {
        return new DateTime().toString("yyyy");
    }

    /**
     * 得到当前的月份
     * 返回格式:MM
     *
     * @return String
     */
    public static String getCurrentMonth() {
        return new DateTime().toString("MM");
    }

    /**
     * 得到当前的日期
     * 返回格式:dd
     *
     * @return String
     */
    public static String getCurrentDay() {
        return new DateTime().toString("dd");
    }

    /**
     * 得到当前的小时
     * 返回格式:hh
     *
     * @return String
     */
    public static String getCurrentHour() {
        return new DateTime().toString("HH");
    }

    /**
     * 得到当前的分钟
     * 返回格式:mm
     *
     * @return String
     */
    public static String getCurrentMinute() {
        return new DateTime().toString("mm");
    }

    /**
     * 得到当前的秒
     * 返回格式:ss
     *
     * @return String
     */
    public static String getCurrentSecond() {
        return new DateTime().toString("ss");
    }

    /**
     * 得到当前的时间，精确到毫秒,共14位
     * 返回格式:yyyy-MM-dd HH:mm:ss
     *
     * @return String
     */
    public static String getCurrentTime() {
        return new DateTime().toString("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到当前的日期,共10位
     * 返回格式：yyyy-MM-dd
     *
     * @return String
     */
    public static String getCurrentDate() {
        return new DateTime().toString("yyyy-MM-dd");
    }

    /**
     * parse date using default pattern yyyy-MM-dd
     *
     * @param strDate
     * @return
     */
    public static Date parseDate(String strDate) {
        Date date = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            date = dateFormat.parse(strDate);
            return date;
        } catch (Exception pe) {
            return null;
        }
    }

    /**
     * @param strDate
     * @param pattern
     * @return
     */
    public static final Date parseDate(String strDate, String pattern) {
        SimpleDateFormat df = null;
        Date date = null;
        df = new SimpleDateFormat(pattern);
        try {
            date = df.parse(strDate);
            return date;
        } catch (Exception pe) {
            return null;
        }
    }

    /**
     * @param date
     * @return formated date by yyyy-MM-dd
     */
    public static final String formatDate(Date date) {
        return format(date, "yyyy-MM-dd");
    }

    /**
     * @param date
     * @return formated date by yyyy-MM-dd HH:mm:ss
     */
    public static final String formatDateTime(Date date) {
        return format(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * @param date
     * @param pattern: Date format pattern
     * @return
     */
    public static final String format(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        return new DateTime(date).toString(pattern);
    }

    /**
     * @param original
     * @param days
     * @param hours
     * @param minutes
     * @param seconds
     * @return original+day+hour+minutes+seconds
     */
    public static final Date addTime(Date original, int days, int hours, int minutes, int seconds) {
        if (original == null) {
            return null;
        }

        return new DateTime(original).plusDays(days).plusHours(hours).plusMinutes(minutes).plusSeconds(seconds).toDate();
    }

    public static Date addYear(Date original, int years) {
        if (original == null) {
            return null;
        }

        return new DateTime(original).plusYears(years).toDate();
    }

    public static Date addMonth(Date original, int months) {
        if (original == null) {
            return null;
        }

        return new DateTime(original).plusMonths(months).toDate();
    }

    public static Date addWeek(Date original, int weeks) {
        if (original == null) {
            return null;
        }

        return new DateTime(original).plusWeeks(weeks).toDate();
    }

    public static final Date addDay(Date original, int days) {
        if (original == null) {
            return null;
        }

        return new DateTime(original).plusDays(days).toDate();
    }

    public static final Date addHour(Date original, int hours) {
        if (original == null) {
            return null;
        }

        return new DateTime(original).plusHours(hours).toDate();
    }

    public static final Date addMinute(Date original, int minutes) {
        if (original == null) {
            return null;
        }

        return new DateTime(original).plusMinutes(minutes).toDate();
    }

    public static final Date addSecond(Date original, int second) {
        if (original == null) {
            return null;
        }

        return new DateTime(original).plusSeconds(second).toDate();
    }

    public static boolean isTomorrow(Date date) {
        if (date == null) {
            return false;
        }

        return formatDate(addTime(new Date(), 1, 0, 0, 0)).equals(formatDate(date));
    }

    /**
     * 获取日期所在月份的第一天
     *
     * @param date
     * @return
     */
    public static Date getMonthFirstDay(Date date) {
        if (date == null) {
            return null;
        }

        String dateStr = format(date, "yyyy-MM") + "-01";
        return parseDate(dateStr);
    }

    /**
     * 获取日期所在月份的最后一天
     *
     * @param date
     * @return
     */
    public static Date getMonthLastDay(Date date) {
        if (date == null) {
            return null;
        }

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        String dateStr = format(date, "yyyy-MM") + "-" + c.getActualMaximum(Calendar.DAY_OF_MONTH);
        return parseDate(dateStr);
    }

    public static String getDateDesc(Date time) {
        if (time == null) {
            return "";
        }
        String timeContent;
        Long ss = System.currentTimeMillis() - time.getTime();
        Long minute = ss / 60000;
        if (minute < 1) {
            minute = 1L;
        }
        if (minute >= 60) {
            Long hour = minute / 60;
            if (hour >= 24) {
                if (hour > 720) {
                    timeContent = "1月前";
                } else if (hour > 168 && hour <= 720) {
                    timeContent = (hour / 168) + "周前";
                } else {
                    timeContent = (hour / 24) + "天前";
                }
            } else {
                timeContent = hour + "小时前";
            }
        } else {
            timeContent = minute + "分钟前";
        }
        return timeContent;
    }

    /**
     * 判断是否为闰年
     *
     * @param year
     * @return
     */
    public static boolean isLeapYear(int year) {
        if (year % 100 == 0) {
            return year % 400 == 0;
        }
        return year % 4 == 0;
    }

    /**
     * 日期字符串转换成Calendar
     *
     * @param strDate
     * @return
     */
    public static Calendar string2Calendar(String strDate) {
        return new DateTime(strDate).toCalendar(Locale.CHINA);
    }

    /**
     * 比较src 是否在 dest 之前,true 代表src 小于dest 日期
     *
     * @param src  源日期
     * @param dest 目标日期
     * @param unit 单位
     *             0：年
     *             1：月
     *             2：日
     *             3：时
     *             4：分
     *             5：秒
     *             如果比较分:包含以上值，是包含关系
     */
    public static boolean compareIsBefore(Date src, Date dest, int unit) {
        if (src == null || dest == null) {
            return false;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String srcStr = format.format(src);
        String destStr = format.format(dest);
        boolean result = false;
        switch (unit) {
            case 0:
                if (Long.parseLong(srcStr.substring(0, 4)) < Long.parseLong(destStr.substring(0, 4))) {
                    result = true;
                }
                break;
            case 1:
                if (Long.parseLong(srcStr.substring(0, 6)) < Long.parseLong(destStr.substring(0, 6))) {
                    result = true;
                }
                break;
            case 2:
                if (Long.parseLong(srcStr.substring(0, 8)) < Long.parseLong(destStr.substring(0, 8))) {
                    result = true;
                }
                break;
            case 3:
                if (Long.parseLong(srcStr.substring(0, 10)) < Long.parseLong(destStr.substring(0, 10))) {
                    result = true;
                }
                break;
            case 4:
                if (Long.parseLong(srcStr.substring(0, 12)) < Long.parseLong(destStr.substring(0, 12))) {
                    result = true;
                }
                break;
            case 5:
                if (Long.parseLong(srcStr.substring(0, 14)) < Long.parseLong(destStr.substring(0, 14))) {
                    result = true;
                }
                break;
            default:
                result = false;
        }

        return result;
    }

    private static boolean compareIsBefore(String date1, String date2) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
        try {
            Date dt1 = null;
            dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            return dt1.getTime() >= dt2.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    private static boolean compareIsAfter(String date1, String date2) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
        try {
            Date dt1 = null;
            dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            return dt1.getTime() <= dt2.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    /**
     * 返回某一天是星期几
     *
     * @param date
     * @return
     */
    public static String getWeek(Date date) {
        return new DateTime(date).dayOfWeek().getAsText();
    }

    /**
     * 将时间戳转换成日期字符串
     *
     * @param timestamp
     * @return
     */
    public static String timestamp2String(Long timestamp) {
        return DateUtil.formatDate(new Date(timestamp));
    }

    /**
     * 将时间戳转换成日期字符串
     *
     * @param timestamp
     * @param pattern
     * @return
     */
    public static String timestamp2String(Long timestamp, String pattern) {
        return DateUtil.format(new Date(timestamp), pattern);
    }
}
