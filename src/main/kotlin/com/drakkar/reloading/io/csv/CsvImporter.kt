package io.csv

import com.drakkar.reloading.core.model.LoadedRound
import java.util.UUID

object CsvImporter {

    fun importRounds(csv: String): List<LoadedRound> {
        val lines = csv.split("\n").drop(1)

        return lines.mapNotNull { line ->
            val c = line.split(",")
            if (c.size < 10) return@mapNotNull null

            LoadedRound(
                id = UUID.fromString(c[0]),
                caliber = c[1],
                bulletId = UUID.fromString(c[2]),
                powderId = UUID.fromString(c[3]),
                primerId = UUID.fromString(c[4]),
                caseId = UUID.fromString(c[5]),
                powderChargeGr = c[6].toDouble(),
                coalIn = c[7].toDouble(),
                quantity = c[8].toInt(),
                dateEpoch = c[9].toLong()
            )
        }
    }
}