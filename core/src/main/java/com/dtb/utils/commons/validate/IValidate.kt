package com.dtb.utils.commons.validate;

import java.util.regex.Pattern


/**
 * 用于数据校验的 类
 *基础的数据 校验方法
 *         常见的数据要求为：
 *             null, empty,正则，
 *             大于，小于，中间，两边，
 *             变形校验
 * @param <T>
 */
interface IValidate<T> {

    fun isEmpty(block: Boolean = true, func: (() -> Unit)?): IValidate<T>?
    fun isNotEmpty(block: Boolean = true, func: (() -> Unit)?): IValidate<T>?

    fun isNone(block: Boolean = true, func: (() -> Unit)?): IValidate<T>?
    fun isNotNone(block: Boolean = true, func: (() -> Unit)?): IValidate<T>?

    fun isMatch(block: Boolean = true, regular: String, func: (() -> Unit)?): IValidate<T>?
    fun isNotMatch(block: Boolean = true, regular: String, func: (() -> Unit)?): IValidate<T>?

    fun isEq(block: Boolean = true, other: T, func: (() -> Unit)?): IValidate<T>?
    fun isNotEq(block: Boolean = true, other: T, func: (() -> Unit)?): IValidate<T>?

    fun isLarger(block: Boolean = true, other: T, func: (() -> Unit)?): IValidate<T>?
    fun isLess(block: Boolean = true, other: T, func: (() -> Unit)?): IValidate<T>?

    fun isBetween(block: Boolean = true, less: T, larger: T, func: (() -> Unit)?): IValidate<T>?
    fun isOutSide(block: Boolean = true, less: T, larger: T, func: (() -> Unit)?): IValidate<T>?

    fun transformVerification(transformer: () -> Any?): IValidate<out Any?>?
    fun transformCheck(transformer: () -> Boolean, block: Boolean = true, func: (() -> Unit)?): IValidate<T>?
}


abstract class ValidateBase<T>(val parameter: T?, val def_func: (() -> Unit)?) : IValidate<T> {

    fun testFailed(block: Boolean, func: (() -> Unit)?): IValidate<T>? {
        if (def_func == null) {
            func?.invoke()
        } else {
            def_func.invoke()
        }
        return if (block) {
            null
        } else {
            this
        }
    }

    override fun isNone(block: Boolean, func: (() -> Unit)?): IValidate<T>? {
        if (parameter == null) {
            return this
        }
        return testFailed(block, func)
    }

    override fun isNotNone(block: Boolean, func: (() -> Unit)?): IValidate<T>? {
        if (parameter != null) {
            return this
        }
        return testFailed(block, func)
    }

    override fun isMatch(block: Boolean, regular: String, func: (() -> Unit)?): IValidate<T>? {
        val p = parameter?.toString()
        return if (p != null && Pattern.matches(regular, p)) {
            this
        } else {
            testFailed(block, func)
        }
    }

    override fun isNotMatch(block: Boolean, regular: String, func: (() -> Unit)?): IValidate<T>? {
        val p = parameter?.toString()
        return if (p != null && !Pattern.matches(regular, p)) {
            this
        } else {
            testFailed(block, func)
        }
    }


    override fun isEq(block: Boolean, other: T, func: (() -> Unit)?): IValidate<T>? {
        if (parameter == other) {
            return this
        }
        return testFailed(block, func)
    }

    override fun isNotEq(block: Boolean, other: T, func: (() -> Unit)?): IValidate<T>? {
        if (parameter != other) {
            return this
        }
        return testFailed(block, func)
    }

    override fun isBetween(block: Boolean, less: T, larger: T, func: (() -> Unit)?): IValidate<T>? {
        return isLarger(block, less, func)?.isLess(block, larger, func)
    }

    override fun isOutSide(block: Boolean, less: T, larger: T, func: (() -> Unit)?): IValidate<T>? {
        return isLarger(block, larger, func)?.isLess(block, less, func)
    }

    override fun transformVerification(transformer: () -> Any?): IValidate<out Any?>? {
        val p = transformer.invoke()
        return createVerification(p, def_func)
    }

    /**
     *@param transformer 返回一个判断结果
     */
    override fun transformCheck(transformer: () -> Boolean, block: Boolean, func: (() -> Unit)?): IValidate<T>? {
        val p = transformer.invoke()
        if (!p) {
            return testFailed(block, func)
        }
        return this
    }
}


class ValidateException(message: String?) : Exception(message) {
}