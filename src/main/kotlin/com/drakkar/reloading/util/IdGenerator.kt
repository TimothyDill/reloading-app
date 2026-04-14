package com.yourname.reloading.util

import java.util.UUID

object IdGenerator {
    fun newId(): UUID = UUID.randomUUID()
}