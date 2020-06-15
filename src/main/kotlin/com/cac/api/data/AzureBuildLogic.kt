package com.cac.api.data

import com.cac.api.model.Range
import com.cac.api.model.Result
import java.text.NumberFormat


class AzureBuildLogic(var result: Result) : BuildLogicSource {

    init {
        filterInProgressBuild()
    }

    private fun filterInProgressBuild() {
        result.value = result.value.filter { it.isBuildFinish() }
        result.count = result.value.size
    }

    override fun getDuration(): Long {
        if (result.isEmpty()) {
            return 0
        }
        var times = 0L
        result.value.forEach {
            times += it.getFinishTime() - it.getStartTime()
        }
        var total = result.value.size
        return (times / Result.MINUTES) / total
    }

    override fun getTimes(): Int {
        return result.count
    }

    override fun getBuildSuccessRate(): Float {
        if (result.isEmpty()) {
            return 0.0f
        }
        var successTime =
                result.value.filter {
                    Result.SUCCESS == it.result
                }.count()
        var total = result.value.size
        var result = String.format("%.2f", successTime / total.toFloat())
        return result.toFloat()
    }


    override fun getRecoveryTime(): Long {
        if (result.isEmpty()) {
            return 0
        }
        var rangList = mutableListOf<Range>()
        composeRange(rangList)
//        fillRangForFailedBuild(rangList)
        var times = 0L
        rangList.forEach {
            times += it.successBuild!!.getFinishTime() - it.failedBuild!!.getFinishTime()
        }
        var total = rangList.size
        if (total == 0) {
            return 0
        }
        return (times / Result.MINUTES) / total
    }

    private fun composeRange(rangList: MutableList<Range>) {
        var currentRange = Range()
        result.value.asReversed().forEach {
            if (Result.SUCCESS != it.result) {
                if (currentRange.failedBuild == null) {
                    currentRange.failedBuild = it
                }
            } else if (Result.SUCCESS == it.result) {
                if (currentRange.failedBuild != null) {
                    currentRange.successBuild = it
                    rangList.add(currentRange)
                    currentRange = Range()
                }
            }
        }
    }

    private fun fillRangForFailedBuild(rangList: MutableList<Range>) {
        if (result.isFinishFailed()) {
            var range = Range()
            range.failedBuild = result.value.first()
            range.successBuild = result.createCurrentBuild()
            rangList.add(range)
        }
    }


}