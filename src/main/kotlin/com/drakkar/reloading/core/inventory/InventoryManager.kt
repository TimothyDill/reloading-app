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
    // BULLETS
    // ======================================================

    fun addBullet(bullet: Bullet) {
        bullets.add(bullet)
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
        powders.add(powder)
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
        primers.add(primer)
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
        cases.add(case)
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
    // ROUND BUILDING (FULL FLOW FIXED)
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

        val bullet = bullets.find { it.id == bulletId }
            ?: error("Bullet not found")

        val powder = powders.find { it.id == powderId }
            ?: error("Powder not found")

        val primer = primers.find { it.id == primerId }
            ?: error("Primer not found")

        val case = cases.find { it.id == caseId }
            ?: error("Case not found")

        require(bullet.diameterIn == cartridge.bulletDiameterIn) {
            "Bullet incompatible"
        }

        require(primer.type == cartridge.primerType) {
            "Primer incompatible"
        }

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
}