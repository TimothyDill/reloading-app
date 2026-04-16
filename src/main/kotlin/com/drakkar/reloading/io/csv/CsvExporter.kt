package com.drakkar.reloading.io.csv

import com.drakkar.reloading.core.model.LoadedRound

object CsvExporter {

    fun exportRounds(rounds: List<LoadedRound>): String {
        val header = "ID,Cartridge,BulletID,PowderID,PrimerID,CaseID,Charge,COAL,Qty,Date"

        val body = rounds.joinToString("\n") {
            "${it.id},${it.cartridgeName},${it.bulletId},${it.powderId},${it.primerId},${it.caseId},${it.powderChargeGr},${it.coalIn},${it.quantity},${it.dateEpoch}"
        }

        return "$header\n$body"
    }
}