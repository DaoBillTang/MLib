package com.dtb.utils.sample;

import com.dtb.utils.commons.utils.DateUtil;

import org.junit.Test;

import java.util.Date;

import static junit.framework.Assert.assertEquals;

/**
 * Project com.daotangbill.exlib.sample
 * Created by DaoTangBill on 2018/3/27/027.
 * Email:tangbaozi@daotangbill.uu.me
 *
 * @author bill
 * @version 1.0
 * @description
 */

public class JavaTest {

    @Test
    public void testDate() {
        Date date = DateUtil.addDay(new Date(), 15);

        String time = DateUtil.formatDateTime(date);

        System.out.print(time);

//        assertEquals(date, new Date());

    }

}
