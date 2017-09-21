package com.daotangbill.dt_ext.exlib.commons.Utils

import com.daotangbill.dt_ext.exlib.Base.DtBaseApp
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
fun String?.parity(regular: String, err: String): Boolean =
        if (this == null) {
            DtBaseApp.act?.Terror(err)
            false
        } else {
            if (Pattern.matches(regular, this)) {
                DtBaseApp.act?.Terror(err)
                false
            } else {
                true
            }
        }
