package core.validation

import com.drakkar.reloading.core.model.LoadReference

object LoadValidator {

    enum class Status {
        BELOW_MIN, SAFE, ABOVE_MAX
    }

    fun validate(charge: Double, ref: LoadReference): Status {
        return when {
            charge < ref.minChargeGr -> Status.BELOW_MIN
            charge > ref.maxChargeGr -> Status.ABOVE_MAX
            else -> Status.SAFE
        }
    }
}