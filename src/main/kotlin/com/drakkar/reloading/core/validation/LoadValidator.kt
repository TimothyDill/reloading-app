// src/main/kotlin/com/drakkar/reloading/core/validation/LoadValidator.kt

package com.drakkar.reloading.core.validation

import com.drakkar.reloading.core.model.LoadReference

object LoadValidator {

    enum class Status {
        BELOW_MIN,
        SAFE,
        ABOVE_MAX
    }

    fun validate(charge: Double, ref: LoadReference): Status {
        return when {
            charge < ref.minCharge -> Status.BELOW_MIN
            charge > ref.maxCharge -> Status.ABOVE_MAX
            else -> Status.SAFE
        }
    }
}