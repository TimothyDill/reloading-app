package com.drakkar.reloading.core.inventory

import com.drakkar.reloading.core.model.*

interface InventoryRepository {

    fun addBullet(brand: String, name: String, weightGr: Double, diameterIn: Double, cost: Double, qty: Int)
    fun getBullets(): List<Bullet>

    fun addPowder(brand: String, name: String, costPerLb: Double, grainsPerLb: Int, qty: Double)
    fun getPowders(): List<Powder>

    fun addPrimer(brand: String, type: String, costPer1000: Double, qty: Int)
    fun getPrimers(): List<Primer>

    fun addCase(brand: String, caliber: String, timesFired: Int, cost: Double, qty: Int)
    fun getCases(): List<Case>

    fun addRound(
        caliber: String,
        bulletId: String,
        powderId: String,
        primerId: String,
        caseId: String,
        chargeGr: Double,
        coalIn: Double,
        qty: Int
    )

    fun getRounds(): List<LoadedRound>
}