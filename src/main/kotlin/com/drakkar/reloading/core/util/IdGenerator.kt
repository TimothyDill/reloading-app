package com.drakkar.reloading.core.util

import java.io.File

object IdGenerator {

    fun nextId(file: File, brand: String): String {

        val prefix = brand
            .uppercase()
            .filter { it.isLetter() }
            .take(3)
            .padEnd(3, 'X')

        if (!file.exists()) {
            file.parentFile?.mkdirs()
            return "$prefix-001"
        }

        val maxSeq = file.readLines()
            .drop(1) // skip header
            .mapNotNull { line ->
                val id = line.split(",").firstOrNull() ?: return@mapNotNull null
                val parts = id.split("-")
                if (parts.size != 2) return@mapNotNull null
                parts[1].toIntOrNull()
            }
            .maxOrNull() ?: 0

        val next = (maxSeq + 1).toString().padStart(3, '0')
        return "$prefix-$next"
    }
}