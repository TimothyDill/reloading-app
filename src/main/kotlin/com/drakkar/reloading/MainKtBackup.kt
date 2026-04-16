/*

package com.drakkar.reloading

import com.drakkar.reloading.core.inventory.InventoryManager

fun safeRead(prompt: String): String {
    print(prompt)
    System.out.flush()
    return readLine()?.trim().orEmpty()
}

fun safeInput(): String {
    return try {
        readLine()?.trim().orEmpty()
    } catch (e: Exception) {
        ""
    }
}

fun main() {

    val manager = InventoryManager()

    val menu = """
        1 Add Bullet
        2 Add Powder
        3 Add Primer
        4 Add Case
        5 Add Round
        6 Show All Rounds
        0 Exit
    """.trimIndent()

    println("Reloading CLI started")

    while (true) {

        println()
        println(menu)
        print("> ")
        System.out.flush()

        // val input = safeInput()

        val input = try {
            readLine()?.trim().orEmpty()
        } catch (e: Exception) {
            return
        }

        if (input.isNullOrEmpty()) {
            Thread.sleep(200)
            continue
        }

        when (input) {

            "1" -> {
                val brand = safeRead("Brand: ")
                val name = safeRead("Name: ")
                val weight = safeRead("Weight Gr: ").toDoubleOrNull() ?: 0.0
                val dia = safeRead("Diameter In: ").toDoubleOrNull() ?: 0.0
                val cost = safeRead("Cost: ").toDoubleOrNull() ?: 0.0
                val qty = safeRead("Quantity: ").toIntOrNull() ?: 0

                manager.addBullet(brand, name, weight, dia, cost, qty)
                println("Bullet added")
            }

            "2" -> {
                val brand = safeRead("Brand: ")
                val name = safeRead("Name: ")
                val cost = safeRead("Cost per lb: ").toDoubleOrNull() ?: 0.0
                val grains = safeRead("Grains per lb: ").toIntOrNull() ?: 7000
                val qty = safeRead("Quantity lb: ").toDoubleOrNull() ?: 0.0

                manager.addPowder(brand, name, cost, grains, qty)
                println("Powder added")
            }

            "3" -> {
                val brand = safeRead("Brand: ")
                val type = safeRead("Type: ")
                val cost = safeRead("Cost per 1000: ").toDoubleOrNull() ?: 0.0
                val qty = safeRead("Quantity: ").toIntOrNull() ?: 0

                manager.addPrimer(brand, type, cost, qty)
                println("Primer added")
            }

            "4" -> {
                val brand = safeRead("Brand: ")
                val cal = safeRead("Caliber: ")
                val fired = safeRead("Times Fired: ").toIntOrNull() ?: 0
                val cost = safeRead("Cost per unit: ").toDoubleOrNull() ?: 0.0
                val qty = safeRead("Quantity: ").toIntOrNull() ?: 0

                manager.addCase(brand, cal, fired, cost, qty)
                println("Case added")
            }

            "5" -> {
                val bullets = manager.getBullets()
                val powders = manager.getPowders()
                val primers = manager.getPrimers()
                val cases = manager.getCases()

                if (bullets.isEmpty() || powders.isEmpty() || primers.isEmpty() || cases.isEmpty()) {
                    println("Missing inventory data")
                    continue
                }

                val b = bullets.first()
                val p = powders.first()
                val pr = primers.first()
                val c = cases.first()

                val charge = safeRead("Charge Gr: ").toDoubleOrNull() ?: 25.0
                val coal = safeRead("COAL: ").toDoubleOrNull() ?: 2.2
                val qty = safeRead("Quantity: ").toIntOrNull() ?: 1

                manager.addRound(
                    caliber = c.caliber,
                    bulletId = b.id,
                    powderId = p.id,
                    primerId = pr.id,
                    caseId = c.id,
                    powderChargeGr = charge,
                    coalIn = coal,
                    quantity = qty
                )

                println("Round added")
            }

            "6" -> {
                val rounds = manager.getRounds()
                if (rounds.isEmpty()) {
                    println("No rounds found")
                } else {
                    rounds.forEach { println(it) }
                }
            }

            "0" -> {
                println("Exiting...")
                return
            }

            else -> println("Invalid option: $input")
        }
    }
}

 */