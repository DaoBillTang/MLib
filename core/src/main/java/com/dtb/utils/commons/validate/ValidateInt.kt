package com.dtb.utils.commons.validate;

class ValidateInt(parameter: Int?, def_func: (() -> Unit)?) :
        ValidateBase<Int>(parameter, def_func) {

    override fun isEmpty(block: Boolean, func: (() -> Unit)?): IValidate<Int>? {
        if (parameter == 0) {
            return this
        }
        return testFailed(block, func)
    }

    override fun isNotEmpty(block: Boolean, func: (() -> Unit)?): IValidate<Int>? {
        if (parameter != 0) {
            return this
        }
        return testFailed(block, func)
    }

    override fun isLarger(block: Boolean, other: Int, func: (() -> Unit)?): IValidate<Int>? {
        if (parameter!! > other) {
            return this
        }
        return testFailed(block, func)
    }

    override fun isLess(block: Boolean, other: Int, func: (() -> Unit)?): IValidate<Int>? {
        if (parameter!! < other) {
            return this
        }
        return testFailed(block, func)
    }
}



