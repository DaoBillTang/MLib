package com.daotangbill.exlib.sample;

/**
 * project com.ecentm.customer.model.api
 * Created by Bill on 2017/11/9.
 * emal: tangbakzi@daotangbill.uu.me
 *
 * @author Bill
 * @version 1.0
 * @description
 */

public class BaseRsps<T> {
    public String rc;
    public String[] msg;
    public T data;
}