package com.drakkar.reloading.core.inventory

import com.drakkar.reloading.core.config.AppPaths
import com.drakkar.reloading.core.model.*
import java.io.File

class InventoryManager {

    private val bullets = mutableListOf<Bullet>()
    private val powders = mutableListOf<Powder>()
    private val primers = mutableListOf<Primer>()
    private val cases = mutableListOf<Case>()
    private val rounds = mutableListOf<LoadedRound>()

    init {
        AppPaths.dataDir().mkdirs()
        bootstrap()
        loadAll()
        println("InventoryManager READY -> ${AppPaths.dataDir().absolutePath}")
    }

    // =========================
    // ID GENERATOR
    // =========================

    private fun generateId(existing: List<String>, brand: String): String {
        val prefix = brand.trim()
            .uppercase()
            .filter { it.isLetter() }
            .take(3)
            .padEnd(3, 'X')

        val maxSeq = existing
            .mapNotNull { it.split("-").getOrNull(1)?.toIntOrNull() }
            .maxOrNull() ?: 0

        val next = (maxSeq + 1).toString().padStart(3, '0')
        return "$prefix-$next"
    }

    // =========================
    // BOOTSTRAP
    // =========================

    private fun bootstrap() {
        header(AppPaths.bulletFile(), "ID,Brand,Name,WeightGr,DiameterIn,CostPerUnit,Quantity")
        header(AppPaths.powderFile(), "ID,Brand,Name,CostPerLb,GrainsPerLb,QuantityLb")
        header(AppPaths.primerFile(), "ID,Brand,Type,CostPer1000,Quantity")
        header(AppPaths.caseFile(), "ID,Brand,Caliber,TimesFired,CostPerUnit,Quantity")
        header(AppPaths.roundFile(), "ID,Caliber,BulletID,PowderID,PrimerID,CaseID,ChargeGr,CoalIn,Quantity,Date")
    }

    private fun header(file: File, content: String) {
        if (!file.exists()) {
            file.parentFile?.mkdirs()
            file.writeText(content + "\n")
        }
    }

    // =========================
    // BULLETS
    // =========================

    fun addBullet(
        brand: String,
        name: String,
        weightGr: Double,
        diameterIn: Double,
        costPerUnit: Double,
        quantity: Int
    ) {
        val id = generateId(bullets.map { it.id }, brand)

        bullets.add(Bullet(id, brand, name, weightGr, diameterIn, costPerUnit, quantity))
        saveBullets()

        println("Saved Bullet -> $id (${AppPaths.bulletFile().absolutePath})")
    }

    fun getBullets() = bullets.toList()

    private fun saveBullets() {
        AppPaths.bulletFile().writeText(
            "ID,Brand,Name,WeightGr,DiameterIn,CostPerUnit,Quantity\n" +
                    bullets.joinToString("\n") {
                        "${it.id},${it.brand},${it.name},${it.weightGr},${it.diameterIn},${it.costPerUnit},${it.quantity}"
                    }
        )
    }

    private fun loadBullets() {
        val f = AppPaths.bulletFile()
        if (!f.exists()) return
        bullets.clear()

        f.readLines().drop(1).forEach {
            val p = it.split(",")
            if (p.size != 7) return@forEach

            bullets.add(
                Bullet(p[0], p[1], p[2],
                    p[3].toDouble(),
                    p[4].toDouble(),
                    p[5].toDouble(),
                    p[6].toInt()
                )
            )
        }
    }

    // =========================
    // POWDER
    // =========================

    fun addPowder(
        brand: String,
        name: String,
        costPerLb: Double,
        grainsPerLb: Int,
        quantityLb: Double
    ) {
        val id = generateId(powders.map { it.id }, brand)
        powders.add(Powder(id, brand, name, costPerLb, grainsPerLb, quantityLb))
        savePowders()
    }

    fun getPowders() = powders.toList()

    private fun savePowders() {
        AppPaths.powderFile().writeText(
            "ID,Brand,Name,CostPerLb,GrainsPerLb,QuantityLb\n" +
                    powders.joinToString("\n") {
                        "${it.id},${it.brand},${it.name},${it.costPerLb},${it.grainsPerLb},${it.quantityLb}"
                    }
        )
    }

    private fun loadPowders() {
        val f = AppPaths.powderFile()
        if (!f.exists()) return
        powders.clear()

        f.readLines().drop(1).forEach {
            val p = it.split(",")
            if (p.size != 6) return@forEach

            powders.add(
                Powder(p[0], p[1], p[2],
                    p[3].toDouble(),
                    p[4].toInt(),
                    p[5].toDouble()
                )
            )
        }
    }

    // =========================
    // PRIMERS
    // =========================

    fun addPrimer(
        brand: String,
        type: String,
        costPer1000: Double,
        quantity: Int
    ) {
        val id = generateId(primers.map { it.id }, brand)
        primers.add(Primer(id, brand, type, costPer1000, quantity))
        savePrimers()
    }

    fun getPrimers() = primers.toList()

    private fun savePrimers() {
        AppPaths.primerFile().writeText(
            "ID,Brand,Type,CostPer1000,Quantity\n" +
                    primers.joinToString("\n") {
                        "${it.id},${it.brand},${it.type},${it.costPer1000},${it.quantity}"
                    }
        )
    }

    private fun loadPrimers() {
        val f = AppPaths.primerFile()
        if (!f.exists()) return
        primers.clear()

        f.readLines().drop(1).forEach {
            val p = it.split(",")
            if (p.size != 5) return@forEach

            primers.add(
                Primer(p[0], p[1], p[2], p[3].toDouble(), p[4].toInt())
            )
        }
    }

    // =========================
    // CASES
    // =========================

    fun addCase(
        brand: String,
        caliber: String,
        timesFired: Int,
        costPerUnit: Double,
        quantity: Int
    ) {
        val id = generateId(cases.map { it.id }, brand)
        cases.add(Case(id, brand, caliber, timesFired, costPerUnit, quantity))
        saveCases()
    }

    fun getCases() = cases.toList()

    private fun saveCases() {
        AppPaths.caseFile().writeText(
            "ID,Brand,Caliber,TimesFired,CostPerUnit,Quantity\n" +
                    cases.joinToString("\n") {
                        "${it.id},${it.brand},${it.caliber},${it.timesFired},${it.costPerUnit},${it.quantity}"
                    }
        )
    }

    private fun loadCases() {
        val f = AppPaths.caseFile()
        if (!f.exists()) return
        cases.clear()

        f.readLines().drop(1).forEach {
            val p = it.split(",")
            if (p.size != 6) return@forEach

            cases.add(
                Case(p[0], p[1], p[2],
                    p[3].toInt(),
                    p[4].toDouble(),
                    p[5].toInt()
                )
            )
        }
    }

    // =========================
    // ROUNDS
    // =========================

    fun addRound(
        caliber: String,
        bulletId: String,
        powderId: String,
        primerId: String,
        caseId: String,
        powderChargeGr: Double,
        coalIn: Double,
        quantity: Int
    ) {
        val id = "RND-${System.currentTimeMillis()}"

        rounds.add(
            LoadedRound(
                id,
                caliber,
                bulletId,
                powderId,
                primerId,
                caseId,
                powderChargeGr,
                coalIn,
                quantity,
                System.currentTimeMillis()
            )
        )

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
            Caliber: ${round.caliber}
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
        AppPaths.roundFile().writeText(
            "ID,Caliber,BulletID,PowderID,PrimerID,CaseID,ChargeGr,CoalIn,Quantity,Date\n" +
                    rounds.joinToString("\n") {
                        "${it.id},${it.caliber},${it.bulletId},${it.powderId},${it.primerId},${it.caseId},${it.powderChargeGr},${it.coalIn},${it.quantity},${it.dateEpoch}"
                    }
        )
    }

    private fun loadAll() {
        loadBullets()
        loadPowders()
        loadPrimers()
        loadCases()
        loadRounds()
    }

    private fun loadRounds() {
        val f = AppPaths.roundFile()
        if (!f.exists()) return
        rounds.clear()

        f.readLines().drop(1).forEach {
            val p = it.split(",")
            if (p.size != 10) return@forEach

            rounds.add(
                LoadedRound(
                    p[0], p[1], p[2], p[3], p[4], p[5],
                    p[6].toDouble(),
                    p[7].toDouble(),
                    p[8].toInt(),
                    p[9].toLong()
                )
            )
        }
    }
}