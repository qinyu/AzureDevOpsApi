package com.cac.api

import com.cac.api.model.Build
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TimeTest {

    @Test
    fun testTimeConvert() {
        val startTime = "2020-06-02T07:23:13.6767296Z"
        val endTime = "2020-06-02T07:36:15.9806247Z"
        val build = Build("", "", startTime, endTime)
        val result = build.getFinishTime() - build.getStartTime()
        Assert.assertEquals(13, result / 1000 / 60)
    }
}