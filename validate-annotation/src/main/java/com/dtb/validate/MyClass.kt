package com.dtb.validate

import com.dtb.validate.annotation.Validate
import com.dtb.validate.annotation.ValidateRule
import com.dtb.validate.enums.ValidateType

public class MyClass {

    class A {
        @Validate(rules = [ValidateRule(ruleType = ValidateType.EQ)])
        var A: String? = null

    }

    class ValidateF {
        fun validate(bean: A) {

        }
    }
}
