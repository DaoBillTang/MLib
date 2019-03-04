package com.dtb.utils.commons.utils

import android.content.Context
import com.dtb.utils.commons.toast.Terror
import java.util.regex.Pattern

/**
 * project com.daotangbill.dt_ext.exlib.commons.Utils
 * Created by Bill on 2017/9/21.
 * emal: tangbakzi@daotangbill.uu.me
 * @author: Bill
 * @version: 1.0
 * @description:校验 信息方法
 */

/**
 * 对 字符串 进行校验
 * @param regular 校验条件
 * @param err 错误提示
 */
fun CharSequence?.parity(context: Context?, regular: String?, err: String = "校验错误")
        : Boolean = this.parity(regular, { context?.Terror(err) })

fun CharSequence?.parity(regular: String?, err: (() -> Unit)): Boolean =
        if (this == null) {
            err.invoke()
            false
        } else {
            if (Pattern.matches(regular, this)) {
                true
            } else {
                err.invoke()
                false
            }
        }

fun CharSequence?.parityAndEmpty(context: Context?, regular: String?, err: String = "校验错误")
        : Boolean = this.parityAndEmpty(regular, { context?.Terror(err) })

fun CharSequence?.parityAndEmpty(regular: String?, err: (() -> Unit)): Boolean =
        if (this == null) {
            err.invoke()
            false
        } else {
            if (this.isBlank()) {
                err.invoke()
                false
            } else {
                if (Pattern.matches(regular, this)) {
                    true
                } else {
                    err.invoke()
                    false
                }
            }
        }

/**
 *@author bill
 *@description
 * @param err:校验出不符合需求的内容的提示
 * @param emptyErr:空值错误提示
 */
fun CharSequence?.notParity(context: Context?, regular: String?,
                            err: String = "校验错误")
        : Boolean = this.notParity(regular, { context?.Terror(err) })

fun CharSequence?.notParity(regular: String?, err: (() -> Unit)): Boolean =
        if (this == null) {
            err.invoke()
            false
        } else {
            if (this.isBlank()) {
                err.invoke()
                false
            } else {
                if (Pattern.matches(regular, this)) {
                    err.invoke()
                    false
                } else {
                    true
                }
            }
        }


fun CharSequence?.parityEmpty(context: Context?, err: String = "校验错误")
        : Boolean = this.parityEmpty({ context?.Terror(err) })

fun CharSequence?.parityEmpty(err: (() -> Unit)): Boolean =
        if (this == null) {
            err.invoke()
            false
        } else {
            if (this.isBlank()) {
                err.invoke()
                false
            } else {
                true
            }
        }


fun CharSequence?.paritySize(context: Context?, min: Int, max: Int, err: String = "校验错误")
        : Boolean = this.paritySize(min, max, { context?.Terror(err) })

fun CharSequence?.paritySize(min: Int, max: Int, err: (() -> Unit)): Boolean =
        if (this == null) {
            err.invoke()
            false
        } else {
            if (this.length in min..max) {
                true
            } else {
                err.invoke()
                false
            }
        }

fun CharSequence?.parityMinSize(context: Context?, min: Int, err: String = "校验错误"): Boolean =
        this.paritySize(min, Int.MAX_VALUE, { context?.Terror(err) })

fun CharSequence?.parityMinSize(min: Int, err: (() -> Unit)): Boolean =
        this.paritySize(min, Int.MAX_VALUE, err)

fun CharSequence?.parityMaxSize(context: Context?, max: Int, err: String = "校验错误"): Boolean =
        this.paritySize(0, max, { context?.Terror(err) })

fun CharSequence?.parityMaxSize(max: Int, err: (() -> Unit)): Boolean =
        this.paritySize(0, max, err)

fun CharSequence?.parityEquals(context: Context?, equals: CharSequence?, err: String = "校验错误")
        : Boolean {
    return this.parityEquals(equals, { context?.Terror(err) })
}

fun CharSequence?.parityEquals(equals: CharSequence?, err: (() -> Unit))
        : Boolean {
    return if (this == equals) {
        true
    } else {
        err.invoke()
        false
    }
}

/**
 * 判断是否 不同，不同返回 true,并进行错误操作
 */
fun CharSequence?.parityNotEquals(context: Context?, equals: CharSequence?, err: String = "校验错误")
        : Boolean = this.parityNotEquals(equals, { context?.Terror(err) })

fun CharSequence?.parityNotEquals(equals: CharSequence?, err: (() -> Unit))
        : Boolean {
    return if (this == equals) {
        err.invoke()
        false
    } else {
        true
    }
}

/**
 *@author bill
 *created at 2018/1/15 11:04
 *@description  对比两个 int 是否相等
 */
object IntParityStat {
    const val LESS = -2
    const val LESS_AND_EQUAL = -1
    const val EQUAL = 0
    const val LARGER_AND_EQUAL = 1
    const val LARGER = 2
}

fun Int?.parity(other: Int, stats: Int = 0, err: (() -> Unit)): Boolean {
    val b = when (stats) {
        IntParityStat.LESS -> {
            this?.parityLess(other, err)
        }
        IntParityStat.LESS_AND_EQUAL -> {
            this?.parityLessAndEqual(other, err)
        }
        IntParityStat.EQUAL -> {
            this?.parityEqual(other, err)
        }
        IntParityStat.LARGER_AND_EQUAL -> {
            this?.parityLargerAndEqual(other, err)
        }
        IntParityStat.LARGER -> {
            this?.parityLarger(other, err)
        }
        else -> {
            err.invoke()
            false
        }
    }

    return b ?: false
}

fun Int?.parityEqual(other: Int, err: (() -> Unit)): Boolean {
    val b = other == this
    if (!b) {
        err.invoke()
    }
    return b
}

fun Int?.parityLarger(other: Int, err: (() -> Unit)): Boolean {
    val b = (this != null && this > other)

    if (!b) {
        err.invoke()
    }

    return b
}

fun Int?.parityLargerAndEqual(other: Int, err: (() -> Unit)): Boolean {
    val b = (this != null && this >= other)

    if (!b) {
        err.invoke()
    }

    return b
}

fun Int?.parityLess(other: Int, err: (() -> Unit)): Boolean {
    val b = (this != null && this < other)
    if (!b) {
        err.invoke()
    }
    return b
}

fun Int?.parityLessAndEqual(other: Int, err: (() -> Unit)): Boolean {
    val b = (this != null && this <= other)
    if (!b) {
        err.invoke()
    }
    return b
}