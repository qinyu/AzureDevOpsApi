package com.cac.api.model

data class Range(var failedBuild: Build?, var successBuild: Build?) {
    constructor() : this(null, null)
}