package com.ocr.francois.realestatemanager

import com.ocr.francois.realestatemanager.utils.Utils
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun dateFormat() {
        val today = "04/06/2020"
        assertEquals(today, Utils.getTodayDateStringFormat())
    }
}