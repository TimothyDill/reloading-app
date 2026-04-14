package com.drakkar.reloading.core.calculation

import com.drakkar.reloading.core.model.*

object CostCalculator {

    fun costPerRound(
        bullet: Bullet,
        primer: Primer,
        powder: Powder,
        powderChargeGr: Double,
        case: Case
    ): Double {

        val bulletCost = bullet.costPerUnit
        val primerCost = primer.costPer1000 / 1000.0
        val powderCostPerGr = powder.costPerLb / powder.grainsPerLb.toDouble()
        val caseCost = case.costPerUnit

        return bulletCost +
                primerCost +
                (powderCostPerGr * powderChargeGr) +
                caseCost
    }

    fun batchCost(costPerRound: Double, quantity: Int): Double {
        return costPerRound * quantity
    }
}