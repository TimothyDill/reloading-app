// src/main/kotlin/com/drakkar/reloading/core/model/LoadReference.kt

package com.drakkar.reloading.core.model

data class LoadReference(
    val caliber: String,
    val bulletWeight: Double,
    val powder: String,
    val minCharge: Double,
    val maxCharge: Double,
    val minFps: Double,
    val maxFps: Double,
    val source: String
)