package com.cac.api.data

import com.cac.api.model.Result

class AzureBuildLogic(var result: Result) : BuildLogicSource {
    private val MINUTES = 60 * 1000
    override fun getDuration(): Long {
        var times = 0L
        result.value.forEach {
            times += it.getData(it.finishTime) - it.getData(it.startTime)
        }
        var total = result.value.size
        return (times / MINUTES) / total
    }

    override fun getTimes(): Int {
        return result.count
    }

    override fun getBuildSuccessRate(): Float {
        var successTime =
                result.value.filter {
                    Result.SUCCESS == it.result
                }.count()
        var total = result.value.size
        return successTime / total.toFloat()
    }


    override fun getRecoveryTime(): Long {
        return 0

    }

}