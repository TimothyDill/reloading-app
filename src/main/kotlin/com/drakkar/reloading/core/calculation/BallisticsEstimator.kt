// src/main/kotlin/com/drakkar/reloading/core/calculation/BallisticsEstimator.kt

package com.drakkar.reloading.core.calculation

import com.drakkar.reloading.core.model.LoadReference

object BallisticsEstimator {

    fun estimateFps(charge: Double, ref: LoadReference): Double {
        val minCharge = ref.minCharge
        val maxCharge = ref.maxCharge
        val minFps = ref.minFps
        val maxFps = ref.maxFps

        if (maxCharge == minCharge) return minFps

        val ratio = (charge - minCharge) / (maxCharge - minCharge)
        return minFps + ratio * (maxFps - minFps)
    }
}