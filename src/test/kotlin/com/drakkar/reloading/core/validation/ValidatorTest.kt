package com.drakkar.reloading.core.validation

import com.drakkar.reloading.core.model.*
import core.validation.LoadValidator
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import java.util.UUID

class LoadValidatorTest {

    @Test
    fun `detects unsafe charge`() {

        val ref = LoadReference(
            UUID.randomUUID(),
            "300",
            230.0,
            "H1000",
            70.0,
            80.0,
            2600.0,
            3000.0,
            "manual"
        )

        val result = LoadValidator.validate(85.0, ref)

        assertEquals(LoadValidator.Status.ABOVE_MAX, result)
    }
}
