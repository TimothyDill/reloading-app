// src/main/kotlin/com/drakkar/reloading/core/service/SimulationService.kt

package com.drakkar.reloading.core.service

import com.drakkar.reloading.core.calculation.BallisticsEstimator
import com.drakkar.reloading.core.calculation.CostCalculator
import com.drakkar.reloading.core.model.*
import com.drakkar.reloading.core.validation.LoadValidator

data class SimulationResult(
    val cost: Double,
    val fps: Double,
    val status: LoadValidator.Status,
    val bulletCost: Double,
    val primerCost: Double,
    val powderCost: Double,
    val caseCost: Double
)

object SimulationService {

    fun runSimulation(charge: Double): SimulationResult {

        val bullet = Bullet(
            id = "bullet-1",
            brand = "Test",
            name = "230gr",
            weightGr = 230.0,
            diameterIn = 0.308,
            costPerUnit = 0.50,
            quantity = 100
        )

        val powder = Powder(
            id = "powder-1",
            brand = "Hodgdon",
            name = "H1000",
            costPerLb = 60.0,
            grainsPerLb = 7000,
            quantityLb = 1.0
        )

        val primer = Primer(
            id = "primer-1",
            brand = "CCI",
            type = PrimerSize.LARGE_RIFLE,
            costPer1000 = 80.0,
            quantity = 1000
        )

        val case = Case(
            id = "case-1",
            brand = "Lapua",
            caliber = "300WM",
            timesFired = 1,
            costPerUnit = 0.60,
            quantity = 100
        )

        val ref = LoadReference(
            caliber = "300WM",
            bulletWeight = 230.0,
            powder = "H1000",
            minCharge = 70.0,
            maxCharge = 80.0,
            minFps = 2600.0,
            maxFps = 3000.0,
            source = "manual"
        )

        val bulletCost = bullet.costPerUnit
        val primerCost = primer.costPer1000 / 1000.0
        val powderCost = (charge / powder.grainsPerLb) * powder.costPerLb
        val caseCost = case.costPerUnit

        val totalCost = CostCalculator.costPerRound(bullet, primer, powder, charge, case)
        val fps = BallisticsEstimator.estimateFps(charge, ref)
        val status = LoadValidator.validate(charge, ref)

        return SimulationResult(
            cost = totalCost,
            fps = fps,
            status = status,
            bulletCost = bulletCost,
            primerCost = primerCost,
            powderCost = powderCost,
            caseCost = caseCost
        )
    }
}