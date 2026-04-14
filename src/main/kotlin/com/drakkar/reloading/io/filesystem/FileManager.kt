package io.filesystem

import java.io.File

object FileManager {

    fun baseDir(): File {
        return File("reloading")
    }

    fun inventoryDir() = File(baseDir(), "inventory").apply { mkdirs() }
    fun roundsDir() = File(baseDir(), "rounds").apply { mkdirs() }
    fun referenceDir() = File(baseDir(), "reference").apply { mkdirs() }
    fun backupDir() = File(baseDir(), "backups").apply { mkdirs() }
}