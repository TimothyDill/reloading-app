package com.drakkar.reloading.core.validation

import com.drakkar.reloading.core.model.LoadReference

object LoadValidator {

    fun validate(chargeGr: Double, ref: LoadReference): String {
        return when {
            chargeGr < ref.minChargeGr -> "UNDER MIN"
            chargeGr > ref.maxChargeGr -> "OVER MAX"
            else -> "OK"
        }
    }
}