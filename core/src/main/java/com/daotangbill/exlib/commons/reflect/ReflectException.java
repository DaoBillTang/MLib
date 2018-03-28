package com.daotangbill.exlib.commons.reflect;

/**
 * Project com.daotangbill.exlib.commons.Reflect
 * Created by DaoTangBill on 2018/3/26/026.
 * Email:tangbaozi@daotangbill.uu.me
 *
 * @author bill
 * @version 1.0
 * @description
 */

public class ReflectException extends RuntimeException {

    private static final long serialVersionUID = -6654702552823551870L;

    public ReflectException(String message) {
        super(message);
    }

    public ReflectException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReflectException() {
        super();
    }

    public ReflectException(Throwable cause) {
        super(cause);
    }
}
