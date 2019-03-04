package com.dtb.utils.commons.validate

/**
 * Project com.daotangbill.exlib.commons.validate
 * Created by DaoTangBill on 2018/4/23/023.
 * Email:tangbaozi@daotangbill.uu.me
 * @author bill
 * @version 1.0
 * @description
 *
 */
class ValidateResult {

    /**
     * 返回验证结果，默认为 true
     */
    var result: Boolean = true

    /**
     * 存储消息
     */
    val msg: MutableList<String> by lazy {
        mutableListOf<String>()
    }


}