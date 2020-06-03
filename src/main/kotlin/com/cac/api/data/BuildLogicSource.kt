package com.cac.api.data

interface BuildLogicSource {
    fun getDuration(): Long
    fun getTimes(): Int
    fun getBuildSuccessRate(): Float
    fun getRecoveryTime(): Long
}