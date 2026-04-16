package com.drakkar.reloading.core.model

// ======================================================
// PRIMER SIZE (SAFE STRING SERIALIZATION)
// ======================================================

enum class PrimerSize {
    SMALL_PISTOL,
    LARGE_PISTOL,
    SMALL_RIFLE,
    LARGE_RIFLE,
    MAGNUM_RIFLE;

    override fun toString(): String {
        return when (this) {
            SMALL_PISTOL -> "Small Pistol"
            LARGE_PISTOL -> "Large Pistol"
            SMALL_RIFLE -> "Small Rifle"
            LARGE_RIFLE -> "Large Rifle"
            MAGNUM_RIFLE -> "Magnum Rifle"
        }
    }

    companion object {
        fun fromString(value: String): PrimerSize {
            return when (value.trim().lowercase()) {
                "small pistol" -> SMALL_PISTOL
                "large pistol" -> LARGE_PISTOL
                "small rifle" -> SMALL_RIFLE
                "large rifle" -> LARGE_RIFLE
                "magnum rifle" -> MAGNUM_RIFLE
                else -> throw IllegalArgumentException("Invalid PrimerSize: $value")
            }
        }
    }
}

// ======================================================
// MODELS
// ======================================================

data class Bullet(
    val id: String,
    val brand: String,
    val name: String,
    val weightGr: Double,
    val diameterIn: Double,
    val costPerUnit: Double,
    var quantity: Int
)

data class Powder(
    val id: String,
    val brand: String,
    val name: String,
    val costPerLb: Double,
    val grainsPerLb: Int,
    var quantityLb: Double
)

data class Primer(
    val id: String,
    val brand: String,
    val type: PrimerSize,
    val costPer1000: Double,
    var quantity: Int
)

data class Case(
    val id: String,
    val brand: String,
    val caliber: String,
    val timesFired: Int,
    val costPerUnit: Double,
    var quantity: Int
)

data class Cartridge(
    val id: String,
    val name: String,
    val bulletDiameterIn: Double,
    val caseType: String,
    val primerType: PrimerSize
)

data class LoadedRound(
    val id: String,
    val cartridgeName: String,
    val bulletId: String,
    val powderId: String,
    val primerId: String,
    val caseId: String,
    val powderChargeGr: Double,
    val coalIn: Double,
    val quantity: Int,
    val dateEpoch: Long
)