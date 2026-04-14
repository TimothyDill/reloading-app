package com.drakkar.reloading.core.calculation

import com.drakkar.reloading.core.model.LoadReference

object BallisticsEstimator {

    fun estimateFps(charge: Double, ref: LoadReference): Double {
        val slope = (ref.maxFps - ref.minFps) /
                (ref.maxChargeGr - ref.minChargeGr)

        return ref.minFps + slope * (charge - ref.minChargeGr)
    }
}