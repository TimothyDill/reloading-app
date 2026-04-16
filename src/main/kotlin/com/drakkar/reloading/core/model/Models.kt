package com.drakkar.reloading.core.model

data class Bullet(
    val id: String,
    val brand: String,
    val name: String,
    val weightGr: Double,
    val diameterIn: Double,
    val costPerUnit: Double,
    val quantity: Int
)

data class Powder(
    val id: String,
    val brand: String,
    val name: String,
    val costPerLb: Double,
    val grainsPerLb: Int,
    val quantityLb: Double
)

data class Primer(
    val id: String,
    val brand: String,
    val type: String,
    val costPer1000: Double,
    val quantity: Int
)

data class Case(
    val id: String,
    val brand: String,
    val caliber: String,
    val timesFired: Int,
    val costPerUnit: Double,
    val quantity: Int
)

data class LoadedRound(
    val id: String,
    val caliber: String,
    val bulletId: String,
    val powderId: String,
    val primerId: String,
    val caseId: String,
    val powderChargeGr: Double,
    val coalIn: Double,
    val quantity: Int,
    val dateEpoch: Long
)