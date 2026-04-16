package com.drakkar.reloading.core.config

import java.io.File

object AppPaths {

    val root: File = File(System.getProperty("user.dir"), "data")

    val bulletFile = File(root, "bullet.csv")
    val powderFile = File(root, "powder.csv")
    val primerFile = File(root, "primer.csv")
    val caseFile = File(root, "case.csv")
    val cartridgeFile = File(root, "cartridge.csv")
    val roundFile = File(root, "round.csv")

    fun init() {
        root.mkdirs()
    }
}