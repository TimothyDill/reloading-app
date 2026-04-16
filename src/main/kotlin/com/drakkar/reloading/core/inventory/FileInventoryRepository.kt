package com.drakkar.reloading.storage

import com.drakkar.reloading.core.inventory.InventoryRepository
import com.drakkar.reloading.core.model.*
import com.drakkar.reloading.core.util.IdGenerator
import java.io.File

class FileInventoryRepository : InventoryRepository {

    private val baseDir = File(System.getProperty("user.home"), "InventoryAndReloadingHub/data").apply {
        mkdirs()
    }

    private val bullets = mutableListOf<Bullet>()
    private val powders = mutableListOf<Powder>()
    private val primers = mutableListOf<Primer>()
    private val cases = mutableListOf<Case>()
    private val rounds = mutableListOf<LoadedRound>()

    init {
        loadAll()
    }

    // ================= BULLETS =================

    override fun addBullet(
        brand: String,
        name: String,
        weightGr: Double,
        diameterIn: Double,
        cost: Double,
        qty: Int
    ) {
        val file = File(baseDir, "bullet.csv")
        val id = IdGenerator.nextId(file, brand)

        bullets.add(Bullet(id, brand, name, weightGr, diameterIn, cost, qty))
        saveBullets()
    }

    override fun getBullets() = bullets.toList()

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

        file.readLines().drop(1).forEach {
            val p = it.split(",")
            if (p.size < 7) return@forEach

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

    // ================= POWDER =================

    override fun addPowder(
        brand: String,
        name: String,
        costPerLb: Double,
        grainsPerLb: Int,
        qty: Double
    ) {
        val file = File(baseDir, "powder.csv")
        val id = IdGenerator.nextId(file, brand)

        powders.add(Powder(id, brand, name, costPerLb, grainsPerLb, qty))
        savePowders()
    }

    override fun getPowders() = powders.toList()

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

        file.readLines().drop(1).forEach {
            val p = it.split(",")
            if (p.size < 6) return@forEach

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

    // ================= PRIMERS =================

    override fun addPrimer(
        brand: String,
        type: String,
        costPer1000: Double,
        qty: Int
    ) {
        val file = File(baseDir, "primer.csv")
        val id = IdGenerator.nextId(file, brand)

        primers.add(Primer(id, brand, type, costPer1000, qty))
        savePrimers()
    }

    override fun getPrimers() = primers.toList()

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

        file.readLines().drop(1).forEach {
            val p = it.split(",")
            if (p.size < 5) return@forEach

            primers.add(
                Primer(p[0], p[1], p[2], p[3].toDouble(), p[4].toInt())
            )
        }
    }

    // ================= CASES =================

    override fun addCase(
        brand: String,
        caliber: String,
        timesFired: Int,
        cost: Double,
        qty: Int
    ) {
        val file = File(baseDir, "case.csv")
        val id = IdGenerator.nextId(file, brand)

        cases.add(Case(id, brand, caliber, timesFired, cost, qty))
        saveCases()
    }

    override fun getCases() = cases.toList()

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

        file.readLines().drop(1).forEach {
            val p = it.split(",")
            if (p.size < 6) return@forEach

            cases.add(
                Case(
                    p[0], p[1], p[2],
                    p[3].toInt(),
                    p[4].toDouble(),
                    p[5].toInt()
                )
            )
        }
    }

    // ================= ROUNDS =================

    override fun addRound(
        caliber: String,
        bulletId: String,
        powderId: String,
        primerId: String,
        caseId: String,
        chargeGr: Double,
        coalIn: Double,
        qty: Int
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
                chargeGr,
                coalIn,
                qty,
                System.currentTimeMillis()
            )
        )

        File(baseDir, "round.csv").writeText(
            "ID,Caliber,BulletID,PowderID,PrimerID,CaseID,ChargeGr,CoalIn,Quantity,Date\n" +
                    rounds.joinToString("\n") {
                        "${it.id},${it.caliber},${it.bulletId},${it.powderId},${it.primerId},${it.caseId},${it.powderChargeGr},${it.coalIn},${it.quantity},${it.dateEpoch}"
                    }
        )
    }

    override fun getRounds() = rounds.toList()

    private fun loadAll() {
        loadBullets()
        loadPowders()
        loadPrimers()
        loadCases()
        loadRounds()
    }

    private fun loadRounds() {
        val file = File(baseDir, "round.csv")
        if (!file.exists()) return

        file.readLines().drop(1).forEach {
            val p = it.split(",")
            if (p.size < 10) return@forEach

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