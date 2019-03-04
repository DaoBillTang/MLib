package com.dtb.utils.sample;

import java.util.Arrays;

/**
 * project com.mcworle.ecentm.customer.model.api
 * Created by Bill on 2017/11/16.
 * emal: tangbakzi@daotangbill.uu.me
 *
 * @author Bill
 * @version 1.0
 * @description
 */

public class ErrorRsps {
    public String rc;
    public String[] msg;

    public ErrorRsps() {
    }

    public ErrorRsps(String msg) {
        this.msg = new String[]{msg};
    }

    public ErrorRsps(String[] msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ErrorRsps{" +
                "rc='" + rc + '\'' +
                ", msg=" + Arrays.toString(msg) +
                '}';
    }
}