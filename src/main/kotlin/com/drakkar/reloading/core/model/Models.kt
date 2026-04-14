package com.drakkar.reloading.core.model

import java.util.UUID

data class Bullet(
    val id: UUID,
    val name: String,
    val weightGr: Double,
    val diameterIn: Double,
    val costPerUnit: Double,
    val quantity: Int
)

data class Powder(
    val id: UUID,
    val name: String,
    val costPerLb: Double,
    val grainsPerLb: Int = 7000,
    val quantityLb: Double
)

data class Primer(
    val id: UUID,
    val type: String,
    val costPer1000: Double,
    val quantity: Int
)

data class Case(
    val id: UUID,
    val caliber: String,
    val timesFired: Int,
    val costPerUnit: Double,
    val quantity: Int
)

data class LoadedRound(
    val id: UUID,
    val caliber: String,
    val bulletId: UUID,
    val powderId: UUID,
    val primerId: UUID,
    val caseId: UUID,
    val powderChargeGr: Double,
    val coalIn: Double,
    val quantity: Int,
    val dateEpoch: Long
)

data class LoadReference(
    val id: UUID,
    val caliber: String,
    val bulletWeightGr: Double,
    val powder: String,
    val minChargeGr: Double,
    val maxChargeGr: Double,
    val minFps: Double,
    val maxFps: Double,
    val source: String
)