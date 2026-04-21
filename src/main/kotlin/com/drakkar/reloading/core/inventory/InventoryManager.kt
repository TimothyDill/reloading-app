package com.drakkar.reloading.core.inventory

import com.drakkar.reloading.core.data.CartridgeRepository
import com.drakkar.reloading.core.model.*
import java.io.File

class InventoryManager {

    private val baseDir = File(System.getProperty("user.dir"), "data")

    private val bullets = mutableListOf<Bullet>()
    private val powders = mutableListOf<Powder>()
    private val primers = mutableListOf<Primer>()
    private val cases = mutableListOf<Case>()
    private val rounds = mutableListOf<LoadedRound>()

    init {
        baseDir.mkdirs()
        loadAll()
        println("InventoryManager READY -> ${baseDir.absolutePath}")
    }

    // ======================================================
    // ID GENERATORS
    // ======================================================

    private fun nextBulletId(brand: String): String {
        val prefix = brand.take(3).uppercase()
        val existing = bullets
            .filter { it.brand.equals(brand, ignoreCase = true) }
            .mapNotNull { it.id.substringAfter("$prefix-").toIntOrNull() }

        val next = if (existing.isEmpty()) 1 else existing.max() + 1
        return "%s-%03d".format(prefix, next)
    }

    private fun nextPowderId(brand: String): String {
        val prefix = brand.take(3).uppercase()
        val existing = powders
            .filter { it.brand.equals(brand, ignoreCase = true) }
            .mapNotNull { it.id.substringAfter("$prefix-").toIntOrNull() }

        val next = if (existing.isEmpty()) 1 else existing.max() + 1
        return "%s-%03d".format(prefix, next)
    }

    private fun nextPrimerId(brand: String): String {
        val prefix = brand.take(3).uppercase()
        val existing = primers
            .filter { it.brand.equals(brand, ignoreCase = true) }
            .mapNotNull { it.id.substringAfter("$prefix-").toIntOrNull() }

        val next = if (existing.isEmpty()) 1 else existing.max() + 1
        return "%s-%03d".format(prefix, next)
    }

    private fun nextCaseId(brand: String): String {
        val prefix = brand.take(3).uppercase()
        val existing = cases
            .filter { it.brand.equals(brand, ignoreCase = true) }
            .mapNotNull { it.id.substringAfter("$prefix-").toIntOrNull() }

        val next = if (existing.isEmpty()) 1 else existing.max() + 1
        return "%s-%03d".format(prefix, next)
    }

    // ======================================================
    // BULLETS
    // ======================================================

    fun addBullet(bullet: Bullet) {
        val assigned = bullet.copy(id = nextBulletId(bullet.brand))
        bullets.add(assigned)
        saveBullets()
    }

    fun getBullets() = bullets.toList()

    fun updateBullet(id: String, update: Bullet.() -> Bullet) {
        val index = bullets.indexOfFirst { it.id == id }
        if (index >= 0) {
            bullets[index] = bullets[index].update()
            saveBullets()
        }
    }

    fun deleteBullet(id: String) {
        bullets.removeIf { it.id == id }
        saveBullets()
    }

    private fun saveBullets() {
        File(baseDir, "bullet.csv").writeText(
            "ID,Brand,Name,WeightGr,DiameterIn,CostPerUnit,Quantity\n" +
                    bullets.joinToString("\n") {
                        "${it.id},${it.brand},${it.name},${it.weightGr},${it.diameterIn},${it.costPerUnit},${it.quantity}"
                    }
        )
    }

    private fun loadBullets() {
        val file = File(baseDir, "bullet.csv")
        if (!file.exists()) return
        bullets.clear()

        file.readLines().drop(1).forEach {
            val p = it.split(",")
            if (p.size != 7) return@forEach

            bullets.add(
                Bullet(
                    p[0], p[1], p[2],
                    p[3].toDouble(),
                    p[4].toDouble(),
                    p[5].toDouble(),
                    p[6].toInt()
                )
            )
        }
    }

    // ======================================================
    // POWDER
    // ======================================================

    fun addPowder(powder: Powder) {
        val assigned = powder.copy(id = nextPowderId(powder.brand))
        powders.add(assigned)
        savePowders()
    }

    fun getPowders() = powders.toList()

    fun updatePowder(id: String, update: Powder.() -> Powder) {
        val index = powders.indexOfFirst { it.id == id }
        if (index >= 0) {
            powders[index] = powders[index].update()
            savePowders()
        }
    }

    fun deletePowder(id: String) {
        powders.removeIf { it.id == id }
        savePowders()
    }

    private fun savePowders() {
        File(baseDir, "powder.csv").writeText(
            "ID,Brand,Name,CostPerLb,GrainsPerLb,QuantityLb\n" +
                    powders.joinToString("\n") {
                        "${it.id},${it.brand},${it.name},${it.costPerLb},${it.grainsPerLb},${it.quantityLb}"
                    }
        )
    }

    private fun loadPowders() {
        val file = File(baseDir, "powder.csv")
        if (!file.exists()) return
        powders.clear()

        file.readLines().drop(1).forEach {
            val p = it.split(",")
            if (p.size != 6) return@forEach

            powders.add(
                Powder(
                    p[0], p[1], p[2],
                    p[3].toDouble(),
                    p[4].toInt(),
                    p[5].toDouble()
                )
            )
        }
    }

    // ======================================================
    // PRIMERS
    // ======================================================

    fun addPrimer(primer: Primer) {
        val assigned = primer.copy(id = nextPrimerId(primer.brand))
        primers.add(assigned)
        savePrimers()
    }

    fun getPrimers() = primers.toList()

    fun updatePrimer(id: String, update: Primer.() -> Primer) {
        val index = primers.indexOfFirst { it.id == id }
        if (index >= 0) {
            primers[index] = primers[index].update()
            savePrimers()
        }
    }

    fun deletePrimer(id: String) {
        primers.removeIf { it.id == id }
        savePrimers()
    }

    private fun savePrimers() {
        File(baseDir, "primer.csv").writeText(
            "ID,Brand,Type,CostPer1000,Quantity\n" +
                    primers.joinToString("\n") {
                        "${it.id},${it.brand},${it.type},${it.costPer1000},${it.quantity}"
                    }
        )
    }

    private fun loadPrimers() {
        val file = File(baseDir, "primer.csv")
        if (!file.exists()) return
        primers.clear()

        file.readLines().drop(1).forEach {
            val p = it.split(",")
            if (p.size != 5) return@forEach

            primers.add(
                Primer(
                    p[0],
                    p[1],
                    PrimerSize.fromString(p[2]),
                    p[3].toDouble(),
                    p[4].toInt()
                )
            )
        }
    }

    // ======================================================
    // CASES
    // ======================================================

    fun addCase(case: Case) {
        val assigned = case.copy(id = nextCaseId(case.brand))
        cases.add(assigned)
        saveCases()
    }

    fun getCases() = cases.toList()

    fun updateCase(id: String, update: Case.() -> Case) {
        val index = cases.indexOfFirst { it.id == id }
        if (index >= 0) {
            cases[index] = cases[index].update()
            saveCases()
        }
    }

    fun deleteCase(id: String) {
        cases.removeIf { it.id == id }
        saveCases()
    }

    private fun saveCases() {
        File(baseDir, "case.csv").writeText(
            "ID,Brand,Caliber,TimesFired,CostPerUnit,Quantity\n" +
                    cases.joinToString("\n") {
                        "${it.id},${it.brand},${it.caliber},${it.timesFired},${it.costPerUnit},${it.quantity}"
                    }
        )
    }

    private fun loadCases() {
        val file = File(baseDir, "case.csv")
        if (!file.exists()) return
        cases.clear()

        file.readLines().drop(1).forEach {
            val p = it.split(",")
            if (p.size != 6) return@forEach

            cases.add(
                Case(
                    p[0],
                    p[1],
                    p[2],
                    p[3].toInt(),
                    p[4].toDouble(),
                    p[5].toInt()
                )
            )
        }
    }

    // ======================================================
    // ROUNDS
    // ======================================================

    fun loadRounds(
        cartridgeName: String,
        bulletId: String,
        powderId: String,
        primerId: String,
        caseId: String,
        powderChargeGr: Double,
        coalIn: Double,
        quantity: Int
    ) {
        val cartridge = CartridgeRepository.findByName(cartridgeName)
            ?: error("Invalid cartridge: $cartridgeName")

        val bullet = bullets.find { it.id == bulletId } ?: error("Bullet not found")
        val powder = powders.find { it.id == powderId } ?: error("Powder not found")
        val primer = primers.find { it.id == primerId } ?: error("Primer not found")
        val case = cases.find { it.id == caseId } ?: error("Case not found")

        require(bullet.quantity >= quantity)
        require(primer.quantity >= quantity)
        require(case.quantity >= quantity)

        bullet.quantity -= quantity
        primer.quantity -= quantity
        case.quantity -= quantity

        val powderUsedLb = (powderChargeGr * quantity) / 7000.0
        powder.quantityLb -= powderUsedLb

        val round = LoadedRound(
            id = "RND-${System.currentTimeMillis()}",
            cartridgeName = cartridge.name,
            bulletId = bulletId,
            powderId = powderId,
            primerId = primerId,
            caseId = caseId,
            powderChargeGr = powderChargeGr,
            coalIn = coalIn,
            quantity = quantity,
            dateEpoch = System.currentTimeMillis()
        )

        rounds.add(round)

        saveBullets()
        savePowders()
        savePrimers()
        saveCases()
        saveRounds()
    }

    fun getRounds() = rounds.toList()

    fun resolveRound(round: LoadedRound): String {
        val b = bullets.find { it.id == round.bulletId }?.name ?: "UNKNOWN"
        val p = powders.find { it.id == round.powderId }?.name ?: "UNKNOWN"
        val pr = primers.find { it.id == round.primerId }?.type ?: "UNKNOWN"
        val c = cases.find { it.id == round.caseId }?.caliber ?: "UNKNOWN"

        return """
            Round ${round.id}
            Cartridge: ${round.cartridgeName}
            Bullet: $b
            Powder: $p
            Primer: $pr
            Case: $c
            Charge: ${round.powderChargeGr}
            COAL: ${round.coalIn}
            Qty: ${round.quantity}
            Date: ${round.dateEpoch}
        """.trimIndent()
    }

    private fun saveRounds() {
        File(baseDir, "round.csv").writeText(
            "ID,Cartridge,BulletID,PowderID,PrimerID,CaseID,Charge,COAL,Qty,Date\n" +
                    rounds.joinToString("\n") {
                        "${it.id},${it.cartridgeName},${it.bulletId},${it.powderId},${it.primerId},${it.caseId},${it.powderChargeGr},${it.coalIn},${it.quantity},${it.dateEpoch}"
                    }
        )
    }

    private fun loadRounds() {
        val file = File(baseDir, "round.csv")
        if (!file.exists()) return
        rounds.clear()

        file.readLines().drop(1).forEach {
            val p = it.split(",")
            if (p.size != 10) return@forEach

            rounds.add(
                LoadedRound(
                    p[0],
                    p[1],
                    p[2],
                    p[3],
                    p[4],
                    p[5],
                    p[6].toDouble(),
                    p[7].toDouble(),
                    p[8].toInt(),
                    p[9].toLong()
                )
            )
        }
    }

    private fun loadAll() {
        loadBullets()
        loadPowders()
        loadPrimers()
        loadCases()
        loadRounds()
    }

    fun deleteComponent(type: String, id: String): Boolean {
        return when (type.lowercase()) {
            "bullet" -> bullets.removeIf { it.id == id }.also { if (it) saveBullets() }
            "powder" -> powders.removeIf { it.id == id }.also { if (it) savePowders() }
            "primer" -> primers.removeIf { it.id == id }.also { if (it) savePrimers() }
            "case" -> cases.removeIf { it.id == id }.also { if (it) saveCases() }
            else -> false
        }
    }

    fun modifyComponent(type: String, id: String, field: String, newValue: String): Boolean {
        when (type.lowercase()) {

            "bullet" -> {
                val item = bullets.find { it.id == id } ?: return false
                val updated = when (field.lowercase()) {
                    "id", "1" -> item.copy(id = newValue)
                    "brand", "2" -> item.copy(brand = newValue)
                    "name", "3" -> item.copy(name = newValue)
                    "weightgr", "4" -> item.copy(weightGr = newValue.toDouble())
                    "diameterin", "5" -> item.copy(diameterIn = newValue.toDouble())
                    "costperunit", "6" -> item.copy(costPerUnit = newValue.toDouble())
                    "quantity", "7" -> item.copy(quantity = newValue.toInt())
                    else -> return false
                }
                bullets[bullets.indexOf(item)] = updated
                saveBullets()
                return true
            }

            "powder" -> {
                val item = powders.find { it.id == id } ?: return false
                val updated = when (field.lowercase()) {
                    "id", "1" -> item.copy(id = newValue)
                    "brand", "2" -> item.copy(brand = newValue)
                    "name", "3" -> item.copy(name = newValue)
                    "costperlb", "4" -> item.copy(costPerLb = newValue.toDouble())
                    "grainsperlb", "5" -> item.copy(grainsPerLb = newValue.toInt())
                    "quantitylb", "6" -> item.copy(quantityLb = newValue.toDouble())
                    else -> return false
                }
                powders[powders.indexOf(item)] = updated
                savePowders()
                return true
            }

            "primer" -> {
                val item = primers.find { it.id == id } ?: return false
                val updated = when (field.lowercase()) {
                    "id", "1" -> item.copy(id = newValue)
                    "brand", "2" -> item.copy(brand = newValue)
                    "type", "3" -> item.copy(type = PrimerSize.fromString(newValue))
                    "costper1000", "4" -> item.copy(costPer1000 = newValue.toDouble())
                    "quantity", "5" -> item.copy(quantity = newValue.toInt())
                    else -> return false
                }
                primers[primers.indexOf(item)] = updated
                savePrimers()
                return true
            }

            "case" -> {
                val item = cases.find { it.id == id } ?: return false
                val updated = when (field.lowercase()) {
                    "id", "1" -> item.copy(id = newValue)
                    "brand", "2" -> item.copy(brand = newValue)
                    "caliber", "3" -> item.copy(caliber = newValue)
                    "timesfired", "4" -> item.copy(timesFired = newValue.toInt())
                    "costperunit", "5" -> item.copy(costPerUnit = newValue.toDouble())
                    "quantity", "6" -> item.copy(quantity = newValue.toInt())
                    else -> return false
                }
                cases[cases.indexOf(item)] = updated
                saveCases()
                return true
            }
        }
        return false
    }

    private fun generateIdForBrand(brand: String, existingIds: List<String>): String {
        // Normalize brand prefix to 3 characters
        val prefix = brand
            .uppercase()
            .take(3)
            .padEnd(3, '_')

        // Find all IDs that start with this prefix
        val numbers = existingIds
            .filter { it.startsWith(prefix) }
            .mapNotNull {
                it.substringAfter("-").toIntOrNull()
            }

        // Determine next number
        val nextNumber = if (numbers.isEmpty()) 1 else (numbers.max() + 1)

        // Format as PREFIX-###
        return "%s-%03d".format(prefix, nextNumber)
    }

}