package com.daotangbill.dt_ext.exlib.commons.Utils

import android.content.Context
import com.daotangbill.dt_ext.exlib.commons.toast.Terror
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