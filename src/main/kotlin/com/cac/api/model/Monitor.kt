package com.cac.api.model

data class Monitor(
        var organization: String,
        var project: String,
        var repo: String?,
        var branch: String?,
        var result: String,
        var duration: Long,
        var times: Int,
        var rate: Float,
        var recoveryTime: Long
)