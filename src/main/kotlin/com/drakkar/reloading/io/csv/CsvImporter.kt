package io.csv

import com.drakkar.reloading.core.model.LoadedRound

object CsvImporter {

    fun importRounds(csv: String): List<LoadedRound> {
        return csv.split("\n")
            .drop(1)
            .mapNotNull { line ->
                val c = line.split(",")
                if (c.size < 10) return@mapNotNull null

                LoadedRound(
                    id = c[0],
                    caliber = c[1],
                    bulletId = c[2],
                    powderId = c[3],
                    primerId = c[4],
                    caseId = c[5],
                    powderChargeGr = c[6].toDouble(),
                    coalIn = c[7].toDouble(),
                    quantity = c[8].toInt(),
                    dateEpoch = c[9].toLong()
                )
            }
    }
}