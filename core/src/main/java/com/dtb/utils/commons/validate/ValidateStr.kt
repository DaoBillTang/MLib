package com.dtb.utils.commons.validate;

class ValidateStr(parameter: String?, def_func: (() -> Unit)?) :
        ValidateBase<String>(parameter, def_func) {

    override fun isEmpty(block: Boolean, func: (() -> Unit)?): IValidate<String>? {
        if (parameter.isNullOrEmpty()) {
            return this
        }
        return testFailed(block, func)
    }

    override fun isNotEmpty(block: Boolean, func: (() -> Unit)?): IValidate<String>? {
        if (!parameter.isNullOrEmpty()) {
            return this
        }
        return testFailed(block, func)
    }

    override fun isLarger(block: Boolean, other: String, func: (() -> Unit)?): IValidate<String>? {
        if (parameter!! > other) {
            return this
        }
        return testFailed(block, func)
    }

    override fun isLess(block: Boolean, other: String, func: (() -> Unit)?): IValidate<String>? {
        if (parameter!! < other) {
            return this
        }
        return testFailed(block, func)
    }
}
