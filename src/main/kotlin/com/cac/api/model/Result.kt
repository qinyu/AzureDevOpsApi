package com.cac.api.model


data class Result(var count: Int, var value: List<Build>) {
    companion object {
        val SUCCESS = "succeeded"
        val FAILED = "failed"
    }

    fun getFirstFailedBeforeSuccess(list: List<Build>): Build {
        var firstFailedBuild = list.first()
        list.forEach {
            if (SUCCESS == it.result) {
                return@forEach
            }
            firstFailedBuild = it
        }
        return firstFailedBuild
    }

    fun getFirstSuccessBeforeFailed(list: List<Build>): Build {
        var firstSuccessBuild = list.first()
        list.forEach {
            if (FAILED == it.result) {
                return@forEach
            }
            firstSuccessBuild = it
        }
        return firstSuccessBuild
    }

    fun isNone(): Boolean {
        return count == 0
    }

    fun isAllSuccess(): Boolean {
        return value.filter {
            FAILED == it.result
        }.count() < 1
    }

    fun isFinishFailed(): Boolean {
        return value.first().result == FAILED
    }

    fun isFinishSuccess(): Boolean {
        return value.first().result == SUCCESS
    }
}