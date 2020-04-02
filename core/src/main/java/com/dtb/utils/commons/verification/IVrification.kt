"""
用于数据校验的 类
"""

class IVerification<T>(var value?: T, var def_func?: () -> T) {
    """
        基础的数据 校验方法
        常见的数据要求为：
            null, empty,正则，
            大于，小于，中间，两边，
            变形校验
    """
    fun isEmpty(block = true, var func?: () -> T): VerificationData<T>?
    fun isNone(block = true, var func?: () -> T): VerificationData<T>?
    fun isMatch(block = true, var func?: () -> T): VerificationData<T>?
    fun isLarger(block = true, var func?: () -> T): VerificationData<T>?
    fun isLess(block = true, var func?: () -> T):VerificationData<T>?
    fun isBetween(block = true, var func?: () -> T):VerificationData<T>?
    fun isOutSide(block = true, var func?: () -> T):VerificationData<T>?
    fun transfromCheck(transformer: () -> T, block = true, var func?:()->T): VerificationData<T>?
}


fun Any?.CreateVerification(def_func?: () -> T):IVerification<>{

}

