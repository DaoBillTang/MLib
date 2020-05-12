package com.dtb.validate.enums;

public enum ValidateType {
    NONE_RULE,


    EMPTY,
    NOT_EMPTY,
    NONE,
    NOT_NONE,
    MATCH,
    NOT_MATCH,
    EQ,
    NOT_EQ,
    LARGER,
    LARGER_EQ,
    LESS,
    LESS_EQ,
    BETWEEN,
    OUTSIDE,
    ;

    private ValidateType() {
    }
}