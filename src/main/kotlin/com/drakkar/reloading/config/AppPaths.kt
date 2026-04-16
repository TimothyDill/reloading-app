package com.drakkar.reloading.core.config

import java.io.File

object AppPaths {

    /**
     * DEV MODE:
     * Writes directly into project folder so IntelliJ can show files easily.
     */
    private const val DEV_MODE = true

    fun dataDir(): File {
        return if (DEV_MODE) {
            File("data").absoluteFile
        } else {
            File(System.getProperty("user.home"), "InventoryAndReloadingHub/data")
        }.apply {
            mkdirs()
        }
    }

    fun bulletFile() = File(dataDir(), "bullet.csv")
    fun powderFile() = File(dataDir(), "powder.csv")
    fun primerFile() = File(dataDir(), "primer.csv")
    fun caseFile() = File(dataDir(), "case.csv")
    fun roundFile() = File(dataDir(), "round.csv")
}