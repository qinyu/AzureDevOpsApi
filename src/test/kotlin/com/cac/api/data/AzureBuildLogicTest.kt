package com.cac.api.data


import com.cac.api.model.Build
import com.cac.api.model.Result
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class AzureBuildLogicTest {


    @Test
    fun `should return 14 minutes when getDuration and builds average duration is 14 minutes`() {
        val builds = mockBuilds()
        val result = Result(builds.size, builds)
        val azureBuildLogic = AzureBuildLogic(result)
        val time = azureBuildLogic.getDuration()
        Assert.assertEquals(14, time)

    }

    @Test
    fun `should return 8 times when getTimes and builds average times is 8`() {
        val builds = mockBuilds()
        val result = Result(builds.size, builds)
        val azureBuildLogic = AzureBuildLogic(result)
        val time = azureBuildLogic.getTimes()
        Assert.assertEquals(8, time)
    }


    @Test
    fun `should return half rate when getSuccessRate and builds list success rate is half`() {
        val builds = mockBuilds()
        val result = Result(builds.size, builds)
        val azureBuildLogic = AzureBuildLogic(result)
        val rate = azureBuildLogic.getBuildSuccessRate()
        Assert.assertEquals(0.5f, rate)
    }


    fun mockBuilds(): List<Build> {
        var builds = mutableListOf<Build>()
        repeat(8) { i ->
            when (i) {
                0 -> builds.add(Build("failed", "2020-06-02T07:23:13.6767296Z", "2020-06-02T07:36:15.9806247Z"))
                1 -> builds.add(Build("succeeded", "2020-06-02T07:23:13.6767296Z", "2020-06-02T07:37:15.9806247Z"))
                2 -> builds.add(Build("succeeded", "2020-06-02T07:23:13.6767296Z", "2020-06-02T07:38:15.9806247Z"))
                3 -> builds.add(Build("failed", "2020-06-02T07:23:13.6767296Z", "2020-06-02T07:39:15.9806247Z"))
                4 -> builds.add(Build("failed", "2020-06-02T07:23:13.6767296Z", "2020-06-02T07:40:15.9806247Z"))
                5 -> builds.add(Build("succeeded", "2020-06-02T07:23:12.6767296Z", "2020-06-02T07:36:15.9806247Z"))
                6 -> builds.add(Build("failed", "2020-06-02T07:23:11.6767296Z", "2020-06-02T07:36:15.9806247Z"))
                7 -> builds.add(Build("succeeded", "2020-06-02T07:23:10.6767296Z", "2020-06-02T07:36:15.9806247Z"))
            }
        }
        return builds
    }
}
