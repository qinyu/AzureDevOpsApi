package com.cac.api.data

interface BuildLogicSource {
    fun getDuration(): Long
    fun getTimes(): Int
    fun getBuildSuccessRate(): Float

    /**
     * 平均恢复时长计算(F=failed S=success)
     * 找出所有失败和成功的区间，然后取平均区间值
     * 区间：找出第一个F，和接下来的第一个S 组成一个区间

     * NONE    0
     * S1-S2-S3-S4-S5-S6- S7-S8   0
     * F1-F2-F3-F4-F5-F6-F7-F8     CURRENT-F1
     * F1-S1-F2-S2-F3-S3-F4-S4    （S4-F4+S3-F3+S2-F2+S1-F1)/4
     */
    fun getRecoveryTime(): Long
}