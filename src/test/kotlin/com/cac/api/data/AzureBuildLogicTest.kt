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
    fun `should return 0 minutes when getDuration and builds list is empty`() {
        val builds = mutableListOf<Build>()
        val result = Result(builds.size, builds)
        val azureBuildLogic = AzureBuildLogic(result)
        val time = azureBuildLogic.getDuration()
        Assert.assertEquals(0, time)
    }


    @Test
    fun `should return 0 times when getTimes and builds list is empty`() {
        val builds = mutableListOf<Build>()
        val result = Result(builds.size, builds)
        val azureBuildLogic = AzureBuildLogic(result)
        val time = azureBuildLogic.getTimes()
        Assert.assertEquals(0, time)
    }


    @Test
    fun `should return 0 when getSuccessRate and builds list is empty`() {
        val builds = mutableListOf<Build>()
        val result = Result(builds.size, builds)
        val azureBuildLogic = AzureBuildLogic(result)
        val rate = azureBuildLogic.getBuildSuccessRate()
        Assert.assertEquals(0.0f, rate)
    }

    @Test
    fun `should return 0 when getRecoveryTime and builds list is empty`() {
        val builds = mutableListOf<Build>()
        val result = Result(builds.size, builds)
        val azureBuildLogic = AzureBuildLogic(result)
        val times = azureBuildLogic.getRecoveryTime()
        Assert.assertEquals(0, times)
    }


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

    @Test
    fun `should return 0 when getRecoveryTime and builds list is all success`() {
        val builds = mockAllSuccessBuilds()
        val result = Result(builds.size, builds)
        val azureBuildLogic = AzureBuildLogic(result)
        val times = azureBuildLogic.getRecoveryTime()
        Assert.assertEquals(0, times)
    }

    @Test
    fun `should return true when getRecoveryTime and has 3 series rang`() {
        val builds = mockBuilds()
        val result = Result(builds.size, builds)
        var expectedTimes = 0L
        expectedTimes += builds[6].getFinishTime() - builds[7].getFinishTime()
        expectedTimes += builds[2].getFinishTime() - builds[4].getFinishTime()
        expectedTimes += builds[0].getFinishTime() - builds[1].getFinishTime()
        var averageTimes = expectedTimes / Result.MINUTES / 3
        val azureBuildLogic = AzureBuildLogic(result)
        val resultTimes = azureBuildLogic.getRecoveryTime()
        Assert.assertTrue(averageTimes == resultTimes)
    }

    @Test
    fun `should return true when getRecoveryTime and finish build is failed`() {
        var builds = mockFinishFailedBuilds()
        val result = Result(builds.size, builds)
        var expectedTimes = 0L
        expectedTimes += builds[7].getFinishTime() - builds[8].getFinishTime()
        expectedTimes += builds[3].getFinishTime() - builds[5].getFinishTime()
        expectedTimes += builds[1].getFinishTime() - builds[2].getFinishTime()
        expectedTimes += result.createCurrentBuild().getFinishTime() - builds[0].getFinishTime()
        var averageTimes = expectedTimes / Result.MINUTES / 4
        val azureBuildLogic = AzureBuildLogic(result)
        val resultTimes = azureBuildLogic.getRecoveryTime()
        Assert.assertTrue(averageTimes == resultTimes)
    }


    private fun mockBuilds(): List<Build> {
        var builds = mutableListOf<Build>()
        repeat(8) { i ->
            when (i) {
                0 -> builds.add(Build(i.toString(), Result.SUCCESS, "2020-06-09T07:23:10.6767296Z", "2020-06-09T07:36:15.9806247Z"))
                1 -> builds.add(Build(i.toString(), Result.FAILED, "2020-06-08T07:23:11.6767296Z", "2020-06-08T07:36:15.9806247Z"))
                2 -> builds.add(Build(i.toString(), Result.SUCCESS, "2020-06-07T07:23:12.6767296Z", "2020-06-07T07:36:15.9806247Z"))
                3 -> builds.add(Build(i.toString(), Result.FAILED, "2020-06-06T07:23:13.6767296Z", "2020-06-06T07:40:15.9806247Z"))
                4 -> builds.add(Build(i.toString(), Result.FAILED, "2020-06-05T07:23:13.6767296Z", "2020-06-05T07:39:15.9806247Z"))
                5 -> builds.add(Build(i.toString(), Result.SUCCESS, "2020-06-04T07:23:13.6767296Z", "2020-06-04T07:38:15.9806247Z"))
                6 -> builds.add(Build(i.toString(), Result.SUCCESS, "2020-06-03T07:23:13.6767296Z", "2020-06-03T07:37:15.9806247Z"))
                7 -> builds.add(Build(i.toString(), Result.FAILED, "2020-06-02T07:23:13.6767296Z", "2020-06-02T07:36:15.9806247Z"))
            }
        }
        return builds
    }

    private fun mockAllSuccessBuilds(): List<Build> {
        var builds = mutableListOf<Build>()
        repeat(8) { i ->
            when (i) {
                0 -> builds.add(Build(i.toString(), Result.SUCCESS, "2020-06-02T07:23:13.6767296Z", "2020-06-02T07:36:15.9806247Z"))
                1 -> builds.add(Build(i.toString(), Result.SUCCESS, "2020-06-02T07:23:13.6767296Z", "2020-06-02T07:37:15.9806247Z"))
                2 -> builds.add(Build(i.toString(), Result.SUCCESS, "2020-06-02T07:23:13.6767296Z", "2020-06-02T07:38:15.9806247Z"))
                3 -> builds.add(Build(i.toString(), Result.SUCCESS, "2020-06-02T07:23:13.6767296Z", "2020-06-02T07:39:15.9806247Z"))
                4 -> builds.add(Build(i.toString(), Result.SUCCESS, "2020-06-02T07:23:13.6767296Z", "2020-06-02T07:40:15.9806247Z"))
                5 -> builds.add(Build(i.toString(), Result.SUCCESS, "2020-06-02T07:23:12.6767296Z", "2020-06-02T07:36:15.9806247Z"))
                6 -> builds.add(Build(i.toString(), Result.SUCCESS, "2020-06-02T07:23:11.6767296Z", "2020-06-02T07:36:15.9806247Z"))
                7 -> builds.add(Build(i.toString(), Result.SUCCESS, "2020-06-02T07:23:10.6767296Z", "2020-06-02T07:36:15.9806247Z"))
            }
        }
        return builds
    }

    private fun mockFinishFailedBuilds(): List<Build> {
        var builds = mutableListOf<Build>()
        repeat(9) { i ->
            when (i) {
                0 -> builds.add(Build(i.toString(), Result.FAILED, "2020-06-1T07:23:10.6767296Z", "2020-06-1T07:36:15.9806247Z"))
                1 -> builds.add(Build(i.toString(), Result.SUCCESS, "2020-06-09T07:23:10.6767296Z", "2020-06-09T07:36:15.9806247Z"))
                2 -> builds.add(Build(i.toString(), Result.FAILED, "2020-06-08T07:23:11.6767296Z", "2020-06-08T07:36:15.9806247Z"))
                3 -> builds.add(Build(i.toString(), Result.SUCCESS, "2020-06-07T07:23:12.6767296Z", "2020-06-07T07:36:15.9806247Z"))
                4 -> builds.add(Build(i.toString(), Result.FAILED, "2020-06-06T07:23:13.6767296Z", "2020-06-06T07:40:15.9806247Z"))
                5 -> builds.add(Build(i.toString(), Result.FAILED, "2020-06-05T07:23:13.6767296Z", "2020-06-05T07:39:15.9806247Z"))
                6 -> builds.add(Build(i.toString(), Result.SUCCESS, "2020-06-04T07:23:13.6767296Z", "2020-06-04T07:38:15.9806247Z"))
                7 -> builds.add(Build(i.toString(), Result.SUCCESS, "2020-06-03T07:23:13.6767296Z", "2020-06-03T07:37:15.9806247Z"))
                8 -> builds.add(Build(i.toString(), Result.FAILED, "2020-06-02T07:23:13.6767296Z", "2020-06-02T07:36:15.9806247Z"))
            }
        }
        return builds
    }
}
