package com.dtb.core.common.validate;

import com.dtb.core.common.validate.IValidate
import com.dtb.core.common.validate.ValidateBase
import com.dtb.core.common.validate.ValidateException

class ValidateList(parameter: List<*>?, def_func: (() -> Unit)?) :
        ValidateBase<List<*>>(parameter, def_func) {

    override fun isEmpty(block: Boolean, func: (() -> Unit)?): IValidate<List<*>>? {
        if (parameter != null && parameter.isEmpty()) {
            return this
        }
        return testFailed(block, func)
    }

    override fun isNotEmpty(block: Boolean, func: (() -> Unit)?): IValidate<List<*>>? {
        if (parameter != null && parameter.isNotEmpty()) {
            return this
        }
        return testFailed(block, func)
    }

    override fun isLarger(block: Boolean, other: List<*>, func: (() -> Unit)?): IValidate<List<*>>? {
        throw ValidateException("ValidateList 不能判断当前内容")
    }

    override fun isLess(block: Boolean, other: List<*>, func: (() -> Unit)?): IValidate<List<*>>? {
        throw ValidateException("ValidateList 不能判断当前内容")
    }
}