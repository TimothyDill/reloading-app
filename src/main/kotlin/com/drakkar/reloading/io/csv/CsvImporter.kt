package com.drakkar.reloading.io.csv

import com.drakkar.reloading.core.model.LoadedRound

object CsvImporter {

    fun importRounds(lines: List<String>): List<LoadedRound> {
        return lines.drop(1).mapNotNull {
            val p = it.split(",")
            if (p.size != 10) return@mapNotNull null

            LoadedRound(
                p[0],
                p[1], // cartridgeName (NOT caliber)
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