package com.dtb.utils.commons.validate;

class ValidateArray(parameter: Array<*>?, def_func: (() -> Unit)?) :
        ValidateBase<Array<*>>(parameter, def_func) {

    override fun isEmpty(block: Boolean, func: (() -> Unit)?): IValidate<Array<*>>? {
        if (parameter != null && parameter.isEmpty()) {
            return this
        }
        return testFailed(block, func)
    }

    override fun isNotEmpty(block: Boolean, func: (() -> Unit)?): IValidate<Array<*>>? {
        if (parameter != null && parameter.isNotEmpty()) {
            return this
        }
        return testFailed(block, func)
    }

    override fun isLarger(block: Boolean, other: Array<*>, func: (() -> Unit)?): IValidate<Array<*>>? {
        throw ValidateException("ValidateArray 不能判断当前内容")
    }

    override fun isLess(block: Boolean, other: Array<*>, func: (() -> Unit)?): IValidate<Array<*>>? {
        throw ValidateException("ValidateArray 不能判断当前内容")
    }
}