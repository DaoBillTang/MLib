package com.dtb.validate.annotation;

import com.dtb.validate.enums.ValidateType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.dtb.validate.enums.ValidateType.NONE_RULE;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.CLASS)
public @interface ValidateRule {
    /**
     * 校验的 规则
     */
    ValidateType ruleType() default NONE_RULE;

    /**
     * 校验失败返回的信息
     */
    String errMsg() default "";

    /**
     * 其他条件
     */
    String other() default "";

    /**
     * 是否在没有校验通过的时候 中断剩下字段的校验
     */
    boolean abort() default false;

    /**
     * 是否在 校验没有通过的时候进行当前字段的剩下的校验
     */
    boolean toContinue() default false;

    /**
     * 为 这次校验分组，如果添加了分组，在校验的时候需要指定校验分组
     */
    String[] group() default {};
}