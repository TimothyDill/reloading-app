package io.csv

import com.drakkar.reloading.core.model.LoadedRound

object CsvExporter {

    fun exportRounds(rounds: List<LoadedRound>): String {
        val header = "id,caliber,bulletId,powderId,primerId,caseId,powderChargeGr,coalIn,quantity,dateEpoch"

        val rows = rounds.joinToString("\n") {
            "${it.id},${it.caliber},${it.bulletId},${it.powderId},${it.primerId},${it.caseId},${it.powderChargeGr},${it.coalIn},${it.quantity},${it.dateEpoch}"
        }

        return "$header\n$rows"
    }
}