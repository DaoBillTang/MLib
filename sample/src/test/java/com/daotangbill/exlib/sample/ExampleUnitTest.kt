package com.daotangbill.exlib.sample

import com.daotangbill.exlib.commons.utils.DateUtil
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }


    @Test
    fun DateTest() {

        val s = DateUtil.addDay(Date(), 15)

        assertEquals(s, Date())


    }
}
