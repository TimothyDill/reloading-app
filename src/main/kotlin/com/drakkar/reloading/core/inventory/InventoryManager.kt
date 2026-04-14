package com.drakkar.reloading.core.inventory

import com.drakkar.reloading.core.model.Bullet
import com.drakkar.reloading.core.model.Case
import com.drakkar.reloading.core.model.LoadedRound
import com.drakkar.reloading.core.model.Powder
import com.drakkar.reloading.core.model.Primer
import java.util.UUID

class InventoryManager {

    private val bullets = mutableListOf<Bullet>()
    private val powders = mutableListOf<Powder>()
    private val primers = mutableListOf<Primer>()
    private val cases = mutableListOf<Case>()
    private val rounds = mutableListOf<LoadedRound>()

    fun addBullet(b: Bullet) = bullets.add(b)
    fun addPowder(p: Powder) = powders.add(p)
    fun addPrimer(p: Primer) = primers.add(p)
    fun addCase(c: Case) = cases.add(c)
    fun addRound(r: LoadedRound) = rounds.add(r)

    fun getBullets() = bullets.toList()
    fun getPowders() = powders.toList()
    fun getPrimers() = primers.toList()
    fun getCases() = cases.toList()
    fun getRounds() = rounds.toList()

    fun consumeCase(caseId: UUID, qty: Int) {
        val case = cases.find { it.id == caseId } ?: return
        cases.remove(case)
        cases.add(case.copy(quantity = case.quantity - qty))
    }
}