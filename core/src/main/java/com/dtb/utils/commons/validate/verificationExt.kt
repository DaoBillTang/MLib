package com.dtb.utils.commons.validate;

fun createVerification(parameter: Any?, def_func: (() -> Unit)?): IValidate<out Any?> {
    return when (parameter) {
        is Int -> ValidateInt(parameter, def_func)
        is Double -> ValidateDouble(parameter, def_func)
        is String -> ValidateStr(parameter, def_func)
        is List<*> -> ValidateList(parameter, def_func)
        is Array<*> -> ValidateArray(parameter, def_func)
        else -> ValidateNone(parameter, def_func)
    }
}


