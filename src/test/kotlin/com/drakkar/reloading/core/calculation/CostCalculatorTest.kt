package com.drakkar.reloading.core.calculation

import com.drakkar.reloading.core.model.Bullet
import com.drakkar.reloading.core.model.Case
import com.drakkar.reloading.core.model.Powder
import com.drakkar.reloading.core.model.Primer
import org.junit.jupiter.api.Test
import java.util.UUID
import kotlin.test.assertTrue

class CostCalculatorTest {

    @Test
    fun `cost per round is positive`() {

        val bullet = Bullet(UUID.randomUUID(), "test", 150.0, 0.308, 0.50, 100)
        val powder = Powder(UUID.randomUUID(), "test", 60.0, 7000, 1.0)
        val primer = Primer(UUID.randomUUID(), "test", 80.0, 1000)
        val case = Case(UUID.randomUUID(), "300", 1, 0.60, 100)

        val cost = CostCalculator.costPerRound(
            bullet, primer, powder, 75.0, case
        )

        assertTrue(cost > 0)
    }
}