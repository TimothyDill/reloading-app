package com.drakkar.reloading.core.inventory

import com.drakkar.reloading.core.model.*
import java.io.File

class FileInventoryRepository {

    private val baseDir = File(System.getProperty("user.dir"), "data")

    init {
        baseDir.mkdirs()
    }

    // =========================================================
    // PRIMER FIX (PrimerSize ENFORCED)
    // =========================================================

    fun parsePrimerSize(value: String): PrimerSize {
        return PrimerSize.valueOf(value.trim().uppercase())
    }

    // =========================================================
    // BULLET CSV FIX
    // =========================================================

    fun loadBullets(): List<Bullet> {
        val file = File(baseDir, "bullet.csv")
        if (!file.exists()) return emptyList()

        return file.readLines().drop(1).mapNotNull {
            val p = it.split(",")
            if (p.size != 7) return@mapNotNull null

            Bullet(
                p[0], p[1], p[2],
                p[3].toDouble(),
                p[4].toDouble(),
                p[5].toDouble(),
                p[6].toInt()
            )
        }
    }

    // =========================================================
    // PRIMER CSV FIX (IMPORTANT)
    // =========================================================

    fun loadPrimers(): List<Primer> {
        val file = File(baseDir, "primer.csv")
        if (!file.exists()) return emptyList()

        return file.readLines().drop(1).mapNotNull {
            val p = it.split(",")
            if (p.size != 5) return@mapNotNull null

            Primer(
                p[0],
                p[1],
                parsePrimerSize(p[2]),
                p[3].toDouble(),
                p[4].toInt()
            )
        }
    }

    // =========================================================
    // CASE / CALIBER FIX
    // =========================================================

    fun loadCases(): List<Case> {
        val file = File(baseDir, "case.csv")
        if (!file.exists()) return emptyList()

        return file.readLines().drop(1).mapNotNull {
            val p = it.split(",")
            if (p.size != 6) return@mapNotNull null

            Case(
                p[0],
                p[1],
                p[2],
                p[3].toInt(),
                p[4].toDouble(),
                p[5].toInt()
            )
        }
    }

    // =========================================================
    // ROUND FIX (cartridgeName ONLY — NO caliber)
    // =========================================================

    fun loadRounds(): List<LoadedRound> {
        val file = File(baseDir, "round.csv")
        if (!file.exists()) return emptyList()

        return file.readLines().drop(1).mapNotNull {
            val p = it.split(",")
            if (p.size != 10) return@mapNotNull null

            LoadedRound(
                p[0],
                p[1], // cartridgeName
                p[2],
                p[3],
                p[4],
                p[5],
                p[6].toDouble(),
                p[7].toDouble(),
                p[8].toInt(),
                p[9].toLong()
            )
        }
    }
}