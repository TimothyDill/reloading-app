package com.drakkar.reloading.core.inventory

class InventoryService(
    private val repo: InventoryRepository
) {

    fun addBullet(b: String, n: String, w: Double, d: Double, c: Double, q: Int) =
        repo.addBullet(b, n, w, d, c, q)

    fun getBullets() = repo.getBullets()

    fun addPowder(b: String, n: String, c: Double, g: Int, q: Double) =
        repo.addPowder(b, n, c, g, q)

    fun getPowders() = repo.getPowders()

    fun addPrimer(b: String, t: String, c: Double, q: Int) =
        repo.addPrimer(b, t, c, q)

    fun getPrimers() = repo.getPrimers()

    fun addCase(b: String, cal: String, tf: Int, c: Double, q: Int) =
        repo.addCase(b, cal, tf, c, q)

    fun getCases() = repo.getCases()

    fun addRound(
        cal: String,
        bid: String,
        pid: String,
        prid: String,
        cid: String,
        cg: Double,
        ci: Double,
        q: Int
    ) = repo.addRound(cal, bid, pid, prid, cid, cg, ci, q)

    fun getRounds() = repo.getRounds()
}