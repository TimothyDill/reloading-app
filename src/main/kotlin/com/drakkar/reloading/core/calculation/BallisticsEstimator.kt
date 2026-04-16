package com.drakkar.reloading.core.calculation

import com.drakkar.reloading.core.model.LoadReference

object BallisticsEstimator {

    fun estimateFps(chargeGr: Double, ref: LoadReference): Double {
        val ratio = chargeGr / ((ref.minChargeGr + ref.maxChargeGr) / 2.0)
        return ((ref.minFps + ref.maxFps) / 2.0) * ratio
    }
}