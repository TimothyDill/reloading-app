package com.drakkar.reloading

import com.drakkar.reloading.core.data.CartridgeRepository
import com.drakkar.reloading.core.inventory.InventoryManager
import com.drakkar.reloading.core.model.*
import java.util.*

fun main() {

    val manager = InventoryManager()
    val scanner = Scanner(System.`in`)

    println("Reloading CLI (GUIDED BUILDER MODE)")

    while (true) {

        println(
            """
            
            =========================
            1 Add Bullet
            2 Add Powder
            3 Add Primer
            4 Add Case
            5 Build Round (GUIDED)
            6 Show Rounds
            7 Show Components
            8 Exit
            9 Modify/Delete Component
            =========================
            """.trimIndent()
        )

        print("> ")
        when (scanner.nextLine().trim()) {

            // ======================================================
            // BULLET
            // ======================================================
            "1" -> {
                println("Brand:")
                val brand = scanner.nextLine()

                println("Name:")
                val name = scanner.nextLine()

                println("Weight (gr):")
                val weight = scanner.nextLine().toDouble()

                println("Diameter (in):")
                val dia = scanner.nextLine().toDouble()

                println("Cost per unit:")
                val cost = scanner.nextLine().toDouble()

                println("Quantity:")
                val qty = scanner.nextLine().toInt()

                manager.addBullet(
                    Bullet(
                        id = "",
                        brand = brand,
                        name = name,
                        weightGr = weight,
                        diameterIn = dia,
                        costPerUnit = cost,
                        quantity = qty
                    )
                )
            }

            // ======================================================
            // POWDER
            // ======================================================
            "2" -> {
                println("Brand:")
                val brand = scanner.nextLine()

                println("Name:")
                val name = scanner.nextLine()

                println("Cost per lb:")
                val cost = scanner.nextLine().toDouble()

                println("Grains per lb:")
                val grains = scanner.nextLine().toInt()

                println("Quantity (lb):")
                val qty = scanner.nextLine().toDouble()

                manager.addPowder(
                    Powder(
                        id = "",
                        brand = brand,
                        name = name,
                        costPerLb = cost,
                        grainsPerLb = grains,
                        quantityLb = qty
                    )
                )
            }

            // ======================================================
            // PRIMER
            // ======================================================
            "3" -> {
                println("Brand:")
                val brand = scanner.nextLine()

                println("Type (Small Rifle, etc):")
                val type = scanner.nextLine()

                println("Cost per 1000:")
                val cost = scanner.nextLine().toDouble()

                println("Quantity:")
                val qty = scanner.nextLine().toInt()

                val primerType = PrimerSize.fromString(type)

                manager.addPrimer(
                    Primer(
                        id = "",
                        brand = brand,
                        type = primerType,
                        costPer1000 = cost,
                        quantity = qty
                    )
                )
            }

            // ======================================================
            // CASE
            // ======================================================
            "4" -> {
                println("Brand:")
                val brand = scanner.nextLine()

                println("Caliber:")
                val caliber = scanner.nextLine()

                println("Times fired:")
                val fired = scanner.nextLine().toInt()

                println("Cost per unit:")
                val cost = scanner.nextLine().toDouble()

                println("Quantity:")
                val qty = scanner.nextLine().toInt()

                manager.addCase(
                    Case(
                        id = "",
                        brand = brand,
                        caliber = caliber,
                        timesFired = fired,
                        costPerUnit = cost,
                        quantity = qty
                    )
                )
            }

            // ======================================================
            // GUIDED ROUND BUILDER
            // ======================================================
            "5" -> {

                println("Enter Cartridge Name:")
                val cartName = scanner.nextLine()

                val cartridge = CartridgeRepository.findByName(cartName)

                if (cartridge == null) {
                    println("Invalid cartridge")
                    continue
                }

                println("\n--- SELECT BULLET ---")
                val bullets = manager.getBullets()
                    .filter { it.diameterIn == cartridge.bulletDiameterIn }

                bullets.forEachIndexed { i, b ->
                    println("$i -> ${b.id} | ${b.brand} ${b.name} | Qty: ${b.quantity}")
                }

                val bullet = bullets[scanner.nextLine().toInt()]

                println("\n--- SELECT POWDER ---")
                val powders = manager.getPowders()
                powders.forEachIndexed { i, p ->
                    println("$i -> ${p.id} | ${p.brand} ${p.name} | Qty: ${p.quantityLb} lb")
                }

                val powder = powders[scanner.nextLine().toInt()]

                println("\n--- SELECT PRIMER ---")
                val primers = manager.getPrimers()
                    .filter { it.type == cartridge.primerType }

                primers.forEachIndexed { i, p ->
                    println("$i -> ${p.id} | ${p.brand} ${p.type} | Qty: ${p.quantity}")
                }

                val primer = primers[scanner.nextLine().toInt()]

                println("\n--- SELECT CASE ---")
                val cases = manager.getCases()
                    .filter { it.caliber.equals(cartridge.caseType, true) }

                cases.forEachIndexed { i, c ->
                    println("$i -> ${c.id} | ${c.brand} | Qty: ${c.quantity}")
                }

                val case = cases[scanner.nextLine().toInt()]

                println("Powder charge (gr):")
                val charge = scanner.nextLine().toDouble()

                println("COAL:")
                val coal = scanner.nextLine().toDouble()

                println("Quantity to load:")
                val qty = scanner.nextLine().toInt()

                manager.loadRounds(
                    cartridgeName = cartridge.name,
                    bulletId = bullet.id,
                    powderId = powder.id,
                    primerId = primer.id,
                    caseId = case.id,
                    powderChargeGr = charge,
                    coalIn = coal,
                    quantity = qty
                )
            }

            // ======================================================
            // SHOW ROUNDS
            // ======================================================
            "6" -> {
                manager.getRounds().forEach {
                    println(manager.resolveRound(it))
                    println("--------------------")
                }
            }

            // ======================================================
            // SHOW COMPONENTS
            // ======================================================
            "7" -> {
                println("\n=== BULLETS ===")
                manager.getBullets().forEach { println(it) }

                println("\n=== POWDERS ===")
                manager.getPowders().forEach { println(it) }

                println("\n=== PRIMERS ===")
                manager.getPrimers().forEach { println(it) }

                println("\n=== CASES ===")
                manager.getCases().forEach { println(it) }
            }

            // ======================================================
            // EXIT
            // ======================================================
            "8" -> return

            // ======================================================
            // MODIFY / DELETE COMPONENT
            // ======================================================
            "9" -> {
                println("Select component type:")
                println("1 Bullet")
                println("2 Powder")
                println("3 Primer")
                println("4 Case")
                val typeChoice = scanner.nextLine()

                val type = when (typeChoice) {
                    "1" -> "bullet"
                    "2" -> "powder"
                    "3" -> "primer"
                    "4" -> "case"
                    else -> {
                        println("Invalid type")
                        continue
                    }
                }

                // ======================================================
                // PRINT FULL CSV‑STYLE LIST FOR THE SELECTED TYPE
                // ======================================================
                println("\n=== AVAILABLE ${type.uppercase()}S ===")

                when (type) {
                    "bullet" -> manager.getBullets().forEach {
                        println("${it.id}, ${it.brand}, ${it.name}, ${it.weightGr}, ${it.diameterIn}, ${it.costPerUnit}, ${it.quantity}")
                    }

                    "powder" -> manager.getPowders().forEach {
                        println("${it.id}, ${it.brand}, ${it.name}, ${it.costPerLb}, ${it.grainsPerLb}, ${it.quantityLb}")
                    }

                    "primer" -> manager.getPrimers().forEach {
                        println("${it.id}, ${it.brand}, ${it.type}, ${it.costPer1000}, ${it.quantity}")
                    }

                    "case" -> manager.getCases().forEach {
                        println("${it.id}, ${it.brand}, ${it.caliber}, ${it.timesFired}, ${it.costPerUnit}, ${it.quantity}")
                    }
                }

                println("\nEnter ID:")
                val id = scanner.nextLine()

                val item = when (type) {
                    "bullet" -> manager.getBullets().find { it.id == id }
                    "powder" -> manager.getPowders().find { it.id == id }
                    "primer" -> manager.getPrimers().find { it.id == id }
                    "case" -> manager.getCases().find { it.id == id }
                    else -> null
                }

                if (item == null) {
                    println("ID not found")
                    continue
                }

                println("1 Modify")
                println("2 Delete")
                when (scanner.nextLine()) {

                    "2" -> {
                        if (manager.deleteComponent(type, id))
                            println("Deleted.")
                        else
                            println("Delete failed.")
                    }

                    "1" -> {
                        println("Select field to modify:")

                        val props = item::class.members
                            .filter { it.parameters.size == 1 }
                            .toList()

                        props.forEachIndexed { index, prop ->
                            val value = prop.call(item)
                            println("${index + 1} ${prop.name} ($value)")
                        }

                        val field = scanner.nextLine()

                        println("Enter new value:")
                        val newValue = scanner.nextLine()

                        if (manager.modifyComponent(type, id, field, newValue))
                            println("Updated.")
                        else
                            println("Update failed.")
                    }

                    else -> println("Invalid option")
                }
            }

            else -> println("Invalid option")
        }
    }
}
