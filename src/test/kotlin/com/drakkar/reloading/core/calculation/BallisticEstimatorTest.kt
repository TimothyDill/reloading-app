package com.drakkar.reloading.core.calculation

import com.drakkar.reloading.core.model.LoadReference
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class BallisticEstimatorTest {

    @Test
    fun estimate_fps() {

        val ref = LoadReference(
            id = "HOD-001",
            caliber = "5.56 NATO",
            bulletWeightGr = 55.0,
            powder = "H335",
            minChargeGr = 23.0,
            maxChargeGr = 25.5,
            minFps = 2900.0,
            maxFps = 3150.0,
            source = "manual"
        )

        val result = BallisticsEstimator.estimateFps(24.0, ref)

        assertTrue(result > 0)
    }
}