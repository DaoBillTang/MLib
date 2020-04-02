package com.dtb.utils.commons.validate;

class ValidateNone(parameter: Any?, def_func: (() -> Unit)?) :
        ValidateBase<Any>(parameter, def_func) {

    override fun isEmpty(block: Boolean, func: (() -> Unit)?): IValidate<Any>? {
        return this
    }

    override fun isNotEmpty(block: Boolean, func: (() -> Unit)?): IValidate<Any>? {
        return testFailed(block, func)
    }

    override fun isLarger(block: Boolean, other: Any, func: (() -> Unit)?): IValidate<Any>? {
        return testFailed(block, func)
    }

    override fun isLess(block: Boolean, other: Any, func: (() -> Unit)?): IValidate<Any>? {
        return testFailed(block, func)
    }
}
