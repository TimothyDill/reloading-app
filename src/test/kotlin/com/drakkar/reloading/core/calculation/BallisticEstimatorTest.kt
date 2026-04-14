package com.drakkar.reloading.core.calculation

import com.drakkar.reloading.core.model.LoadReference
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue
import java.util.UUID

class BallisticsEstimatorTest {

    @Test
    fun `fps increases with charge`() {

        val ref = LoadReference(
            UUID.randomUUID(),
            "300",
            230.0,
            "H1000",
            70.0,
            80.0,
            2600.0,
            3000.0,
            "manual"
        )

        val low = BallisticsEstimator.estimateFps(72.0, ref)
        val high = BallisticsEstimator.estimateFps(78.0, ref)

        assertTrue(high > low)
    }
}