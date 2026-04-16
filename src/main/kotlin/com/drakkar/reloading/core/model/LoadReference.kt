package com.drakkar.reloading.core.model

data class LoadReference(
    val id: String,
    val caliber: String,
    val bulletWeightGr: Double,
    val powder: String,
    val minChargeGr: Double,
    val maxChargeGr: Double,
    val minFps: Double,
    val maxFps: Double,
    val source: String
)