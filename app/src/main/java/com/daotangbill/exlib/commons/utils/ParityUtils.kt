package com.daotangbill.exlib.commons.utils

import android.content.Context
import com.daotangbill.exlib.commons.toast.Terror
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
fun String?.parity(context: Context?, regular: String?, err: String = "校验错误"): Boolean =
        if (this == null) {
            context?.Terror(err)
            false
        } else {
            if (Pattern.matches(regular, this)) {
                true
            } else {
                context?.Terror(err)
                false
            }
        }


fun String?.parityAndEmpty(context: Context?, regular: String?, err: String = "校验错误"): Boolean =
        if (this == null) {
            context?.Terror(err)
            false
        } else {
            if (this.isBlank()) {
                context?.Terror(err)
                false
            } else {
                if (Pattern.matches(regular, this)) {
                    true
                } else {
                    context?.Terror(err)
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
fun String?.notParity(context: Context?, regular: String?,
                      err: String = "校验错误", emptyErr: String = "数值为空"): Boolean =
        if (this == null) {
            context?.Terror(emptyErr)
            false
        } else {
            if (this.isBlank()) {
                context?.Terror(emptyErr)
                false
            } else {
                if (Pattern.matches(regular, this)) {
                    context?.Terror(err)
                    false
                } else {
                    true
                }
            }
        }

fun String?.parityEmpty(context: Context?, err: String = "校验错误"): Boolean =
        if (this == null) {
            context?.Terror(err)
            false
        } else {
            if (this.isBlank()) {
                context?.Terror(err)
                false
            } else {
                true
            }
        }

fun String?.paritySize(context: Context?, min: Int, max: Int, err: String = "校验错误"): Boolean =
        if (this == null) {
            context?.Terror(err)
            false
        } else {
            if (this.length in min..max) {

                true
            } else {
                context?.Terror(err)
                false
            }
        }

fun String?.parityMinSize(context: Context?, min: Int, err: String = "校验错误"): Boolean =
        this?.paritySize(context, min, Int.MAX_VALUE, err) ?: false

fun String?.parityMaxSize(context: Context?, max: Int, err: String = "校验错误"): Boolean =
        this?.paritySize(context, 0, max, err) ?: false