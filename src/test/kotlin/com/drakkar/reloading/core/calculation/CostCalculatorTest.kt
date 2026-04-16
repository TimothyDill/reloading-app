package com.drakkar.reloading.core.calculation

import com.drakkar.reloading.core.model.*
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class CostCalculatorTest {

    @Test
    fun testCostCalculation_basic() {

        val bullet = Bullet(
            id = "TEST-BULLET-001",
            brand = "TEST",
            name = "Test Bullet",
            weightGr = 150.0,
            diameterIn = 0.308,
            costPerUnit = 0.45,
            quantity = 100
        )

        val powder = Powder(
            id = "TEST-POWDER-001",
            brand = "TEST",
            name = "Test Powder",
            costPerLb = 60.0,
            grainsPerLb = 7000,
            quantityLb = 1.0
        )

        val primer = Primer(
            id = "TEST-PRIMER-001",
            brand = "TEST",
            type = "LRP",
            costPer1000 = 80.0,
            quantity = 1000
        )

        val case = Case(
            id = "TEST-CASE-001",
            brand = "TEST",
            caliber = "308",
            timesFired = 0,
            costPerUnit = 0.60,
            quantity = 100
        )

        val charge = 45.0

        val cost = CostCalculator.costPerRound(
            bullet,
            primer,
            powder,
            charge,
            case
        )

        assertTrue(cost > 0.0)
    }
}