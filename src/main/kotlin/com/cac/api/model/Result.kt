package com.cac.api.model

import java.util.*


data class Result(var count: Int, var value: List<Build>) {
    companion object {
        val SUCCESS = "succeeded"
        val FAILED = "failed"
        val MINUTES = 60 * 1000
    }

    fun isFinishFailed(): Boolean {
        return value.first().result == FAILED
    }

    fun isEmpty(): Boolean {
        return count == 0 || value.isEmpty()
    }

    fun createCurrentBuild() = Build("", "", "", Date().toInstant().toString())
}