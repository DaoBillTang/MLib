package com.dtb.utils.commons.validate;

class ValidateDouble(parameter: Double?, def_func: (() -> Unit)?) :
        ValidateBase<Double>(parameter, def_func) {

    override fun isEmpty(block: Boolean, func: (() -> Unit)?): IValidate<Double>? {
        if (parameter == 0.0) {
            return this
        }
        return testFailed(block, func)
    }

    override fun isNotEmpty(block: Boolean, func: (() -> Unit)?): IValidate<Double>? {
        if (parameter != 0.0) {
            return this
        }
        return testFailed(block, func)
    }

    override fun isLarger(block: Boolean, other: Double, func: (() -> Unit)?): IValidate<Double>? {
        if (parameter!! > other) {
            return this
        }
        return testFailed(block, func)
    }

    override fun isLess(block: Boolean, other: Double, func: (() -> Unit)?): IValidate<Double>? {
        if (parameter!! < other) {
            return this
        }
        return testFailed(block, func)
    }
}



