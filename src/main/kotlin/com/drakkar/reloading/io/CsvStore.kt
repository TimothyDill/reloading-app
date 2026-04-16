package com.drakkar.reloading.core.io

import java.io.File

object CsvStore {

    fun write(file: File, header: String, rows: List<String>) {
        file.parentFile?.mkdirs()
        file.writeText(
            buildString {
                appendLine(header)
                rows.forEach { appendLine(it) }
            }
        )
    }

    fun read(file: File): List<String> {
        if (!file.exists()) return emptyList()
        return file.readLines().drop(1).filter { it.isNotBlank() }
    }
}