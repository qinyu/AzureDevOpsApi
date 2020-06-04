package com.cac.api.model

import java.text.SimpleDateFormat
import java.util.*


data class Build(var buildNumber: String,
                 var status: String,
                 var result: String,
                 var queueTime: String,
                 var startTime: String,
                 var finishTime: String?,
                 var url: String,
                 var repository: Repository?) {

    constructor(buildNumber: String, result: String, startTime: String,
                finishTime: String) : this(buildNumber, "", result, "", startTime, finishTime, "", null)

    private fun getDataFromUTC(time: String?): Long {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val date: Date = format.parse(time) //注意是空格+UTC
        return date.time
    }

    fun isBuildFinish(): Boolean {
        return finishTime != null
    }

    fun getStartTime(): Long {
        return getDataFromUTC(startTime)
    }

    fun getFinishTime(): Long {
        return getDataFromUTC(finishTime)
    }
}
