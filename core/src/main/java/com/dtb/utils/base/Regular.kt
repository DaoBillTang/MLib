package com.dtb.utils.base

/**
 * 数据校验用正则表达式， 所有正则表达式都能匹配空字符串，如需非空校验需要另外校验
 *
 * @author penghy
 */
object Regular {
    /** ID用 正则表达式(32位 16进制小写编码)  */
    const val REGEX_ID = "^$|^[a-f0-9]{32}$"
    /** 手机用 正则 表达式(首位为1,共11位数字)  */
    const val REGEX_MOBILE = "^$|^1\\d{10}$"
    /** 手机或者座机 用 正则表达式(首位为1,共11位数字)  */
    const val REGEX_PHONE = "^$|^1\\d{10}$|^(\\d{3,5})-(\\d{6,9})(-(\\d{1,5}))?$"
    /** 座机 用 正则表达式(首位为1,共11位数字)  */
    const val REGEX_TEL = "^$|^((\\d{1,3})-)?(\\d{6,9})(-(\\d{1,5}))?$"
    /** 验证是否是Json数据 正则表达式(首尾是{})  */
    const val REGEX_JSON = "^$|^\\{.*\\}$"
    /** 验证预约日期时间 正则表达式(精确到半小时)  */
    const val REGEX_DATE_APPOINTMENT = "^$|^\\d{4}-[01]\\d-[0-3]\\d [0-2]\\d:(0|3)0$"
    /** 日期 正则表达式  */
    const val REGEX_DATE = "^$|^\\d{4}-[01]\\d-[0-3]\\d$"
    /** 6位数字  */
    const val REGEX_NUM6 = "^$|^\\d{6}$"
    /** 两位以内正整数或0  */
    const val REGEX_PINT2 = "^$|^[1-9]\\d{0,1}$|^0$"
    /** 三位以内正整数  */
    const val REGEX_PINT3 = "^$|^[1-9]\\d{0,2}$"
    /** 五位以内正整数  */
    const val REGEX_PINT5 = "^$|^[1-9]\\d{0,4}$"
    /** 五位以内正整数  */
    const val REGEX_PINT6 = "^$|^[1-9]\\d{0,5}$"
    /** 九位以内正整数  */
    const val REGEX_PINT9 = "^$|^[1-9]\\d{0,8}$"
    /** 十位以内正整或0  */
    const val REGEX_INT10 = "^$|^[1-9]\\d{0,9}$|^0$"
    /** 可有8位整数,2位小数  */
    const val REGEX_PFLOAT10_2 = "^$|^(\\d\\.\\d{1})|([1-9]\\d{0,7}(\\.\\d{1,2})?)$"
    /** 一位字符  */
    const val REGEX_COLOR = "^$|^[a-zA-Z]{1}$"
    /** 中文姓名  */
    const val REGEX_OWNERNAME = "^$|^[\u4E00-\u9FA5A-Za-z0-9]{0,50}$"
    /** email */
    const val Email = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+\$"
}