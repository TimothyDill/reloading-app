package com.drakkar.reloading.core.data

import com.drakkar.reloading.core.model.Cartridge
import com.drakkar.reloading.core.model.PrimerSize

object CartridgeRepository {

    private val cartridges = listOf(
        Cartridge(
            id = "CART-556",
            name = "5.56 NATO",
            bulletDiameterIn = 0.224,
            caseType = "5.56 NATO",
            primerType = PrimerSize.SMALL_RIFLE
        ),
        Cartridge(
            id = "CART-308",
            name = ".308 Winchester",
            bulletDiameterIn = 0.308,
            caseType = ".308 Win",
            primerType = PrimerSize.LARGE_RIFLE
        ),
        Cartridge(
            id = "CART-9MM",
            name = "9mm Luger",
            bulletDiameterIn = 0.355,
            caseType = "9mm Luger",
            primerType = PrimerSize.SMALL_PISTOL
        )
    )

    fun findByName(name: String): Cartridge? {
        return cartridges.find { it.name.equals(name, ignoreCase = true) }
    }

    fun all(): List<Cartridge> = cartridges
}