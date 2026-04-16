package com.drakkar.reloading.core.inventory

import com.drakkar.reloading.core.model.*
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class InventoryManagementTest {

    @Test
    fun testAddBullet() {
        val manager = InventoryManager()

        manager.addBullet(
            brand = "Hornady",
            name = "ELD",
            weightGr = 140.0,
            diameterIn = 0.264,
            costPerUnit = 0.45,
            quantity = 100
        )

        assertEquals(1, manager.getBullets().size)
    }

    @Test
    fun testAddPowder() {
        val manager = InventoryManager()

        manager.addPowder(
            brand = "Hodgdon",
            name = "Varget",
            costPerLb = 65.0,
            grainsPerLb = 7000,
            quantityLb = 1.0
        )

        assertEquals(1, manager.getPowders().size)
    }

    @Test
    fun testAddPrimer() {
        val manager = InventoryManager()

        manager.addPrimer(
            brand = "CCI",
            type = "LRP",
            costPer1000 = 80.0,
            quantity = 1000
        )

        assertEquals(1, manager.getPrimers().size)
    }

    @Test
    fun testAddCase() {
        val manager = InventoryManager()

        manager.addCase(
            brand = "Lapua",
            caliber = "308",
            timesFired = 0,
            costPerUnit = 0.60,
            quantity = 100
        )

        assertEquals(1, manager.getCases().size)
    }

    @Test
    fun testRounds() {
        val manager = InventoryManager()

        manager.addBullet("Hornady", "ELD", 140.0, 0.264, 0.45, 100)
        manager.addPowder("Hodgdon", "Varget", 65.0, 7000, 1.0)
        manager.addPrimer("CCI", "LRP", 80.0, 1000)
        manager.addCase("Lapua", "308", 0, 0.60, 100)

        val b = manager.getBullets().first()
        val p = manager.getPowders().first()
        val pr = manager.getPrimers().first()
        val c = manager.getCases().first()

        manager.addRound(
            caliber = c.caliber,
            bulletId = b.id,
            powderId = p.id,
            primerId = pr.id,
            caseId = c.id,
            powderChargeGr = 45.0,
            coalIn = 2.8,
            quantity = 10
        )

        assertEquals(1, manager.getRounds().size)
    }
}