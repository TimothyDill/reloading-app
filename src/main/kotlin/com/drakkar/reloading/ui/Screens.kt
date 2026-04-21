package com.drakkar.reloading.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.drakkar.reloading.core.data.CartridgeRepository
import com.drakkar.reloading.core.inventory.InventoryManager
import com.drakkar.reloading.core.model.*
import com.drakkar.reloading.core.service.SimulationService
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState


@Composable
fun MainMenuScreen(onNavigate: (Screen) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Reloading Inventory & Simulation", style = MaterialTheme.typography.h5)
        Spacer(Modifier.height(16.dp))

        Button({ onNavigate(Screen.AddBullet) }) { Text("Add Bullet") }
        Button({ onNavigate(Screen.AddPowder) }) { Text("Add Powder") }
        Button({ onNavigate(Screen.AddPrimer) }) { Text("Add Primer") }
        Button({ onNavigate(Screen.AddCase) }) { Text("Add Case") }
        Button({ onNavigate(Screen.BuildRound) }) { Text("Build Round") }
        Button({ onNavigate(Screen.ShowRounds) }) { Text("Show Rounds") }
        Button({ onNavigate(Screen.ShowComponents) }) { Text("Show Components") }
        Button({ onNavigate(Screen.ModifyDelete) }) { Text("Modify/Delete Component") }
        Button({ onNavigate(Screen.Simulation) }) { Text("Simulation") }
    }
}
@Composable
fun AddBulletScreen(inventoryManager: InventoryManager, onBack: () -> Unit) {
    var brand by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var diameter by remember { mutableStateOf("") }
    var cost by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    ScreenScaffold(title = "Add Bullet", onBack = onBack) {
        OutlinedTextField(brand, { brand = it }, label = { Text("Brand") })
        OutlinedTextField(name, { name = it }, label = { Text("Name") })
        OutlinedTextField(weight, { weight = it }, label = { Text("Weight (gr)") })
        OutlinedTextField(diameter, { diameter = it }, label = { Text("Diameter (in)") })
        OutlinedTextField(cost, { cost = it }, label = { Text("Cost per unit") })
        OutlinedTextField(quantity, { quantity = it }, label = { Text("Quantity") })

        Button(onClick = {
            val w = weight.toDoubleOrNull()
            val d = diameter.toDoubleOrNull()
            val c = cost.toDoubleOrNull()
            val q = quantity.toIntOrNull()

            if (brand.isBlank() || name.isBlank() || w == null || d == null || c == null || q == null) {
                message = "Invalid input"
            } else {
                inventoryManager.addBullet(
                    Bullet(
                        id = "",
                        brand = brand,
                        name = name,
                        weightGr = w,
                        diameterIn = d,
                        costPerUnit = c,
                        quantity = q
                    )
                )
                message = "Bullet added"
            }
        }) {
            Text("Save Bullet")
        }

        if (message.isNotBlank()) {
            Text(message, color = MaterialTheme.colors.primary)
        }
    }
}

@Composable
fun AddPowderScreen(inventoryManager: InventoryManager, onBack: () -> Unit) {
    var brand by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var cost by remember { mutableStateOf("") }
    var grainsPerLb by remember { mutableStateOf("") }
    var quantityLb by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    ScreenScaffold(title = "Add Powder", onBack = onBack) {
        OutlinedTextField(brand, { brand = it }, label = { Text("Brand") })
        OutlinedTextField(name, { name = it }, label = { Text("Name") })
        OutlinedTextField(cost, { cost = it }, label = { Text("Cost per lb") })
        OutlinedTextField(grainsPerLb, { grainsPerLb = it }, label = { Text("Grains per lb") })
        OutlinedTextField(quantityLb, { quantityLb = it }, label = { Text("Quantity (lb)") })

        Button(onClick = {
            val c = cost.toDoubleOrNull()
            val g = grainsPerLb.toIntOrNull()
            val q = quantityLb.toDoubleOrNull()

            if (brand.isBlank() || name.isBlank() || c == null || g == null || q == null) {
                message = "Invalid input"
            } else {
                inventoryManager.addPowder(
                    Powder(
                        id = "",
                        brand = brand,
                        name = name,
                        costPerLb = c,
                        grainsPerLb = g,
                        quantityLb = q
                    )
                )
                message = "Powder added"
            }
        }) {
            Text("Save Powder")
        }

        if (message.isNotBlank()) {
            Text(message, color = MaterialTheme.colors.primary)
        }
    }
}
@Composable
fun AddPrimerScreen(inventoryManager: InventoryManager, onBack: () -> Unit) {
    var brand by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var cost by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    ScreenScaffold(title = "Add Primer", onBack = onBack) {
        OutlinedTextField(brand, { brand = it }, label = { Text("Brand") })
        OutlinedTextField(type, { type = it }, label = { Text("Type (Small Rifle, etc)") })
        OutlinedTextField(cost, { cost = it }, label = { Text("Cost per 1000") })
        OutlinedTextField(quantity, { quantity = it }, label = { Text("Quantity") })

        Button(onClick = {
            val c = cost.toDoubleOrNull()
            val q = quantity.toIntOrNull()
            val primerType = try { PrimerSize.fromString(type) } catch (_: Exception) { null }

            if (brand.isBlank() || primerType == null || c == null || q == null) {
                message = "Invalid input"
            } else {
                inventoryManager.addPrimer(
                    Primer(
                        id = "",
                        brand = brand,
                        type = primerType,
                        costPer1000 = c,
                        quantity = q
                    )
                )
                message = "Primer added"
            }
        }) {
            Text("Save Primer")
        }

        if (message.isNotBlank()) {
            Text(message, color = MaterialTheme.colors.primary)
        }
    }
}

@Composable
fun AddCaseScreen(inventoryManager: InventoryManager, onBack: () -> Unit) {
    var brand by remember { mutableStateOf("") }
    var caliber by remember { mutableStateOf("") }
    var timesFired by remember { mutableStateOf("") }
    var cost by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    ScreenScaffold(title = "Add Case", onBack = onBack) {
        OutlinedTextField(brand, { brand = it }, label = { Text("Brand") })
        OutlinedTextField(caliber, { caliber = it }, label = { Text("Caliber") })
        OutlinedTextField(timesFired, { timesFired = it }, label = { Text("Times fired") })
        OutlinedTextField(cost, { cost = it }, label = { Text("Cost per unit") })
        OutlinedTextField(quantity, { quantity = it }, label = { Text("Quantity") })

        Button(onClick = {
            val tf = timesFired.toIntOrNull()
            val c = cost.toDoubleOrNull()
            val q = quantity.toIntOrNull()

            if (brand.isBlank() || caliber.isBlank() || tf == null || c == null || q == null) {
                message = "Invalid input"
            } else {
                inventoryManager.addCase(
                    Case(
                        id = "",
                        brand = brand,
                        caliber = caliber,
                        timesFired = tf,
                        costPerUnit = c,
                        quantity = q
                    )
                )
                message = "Case added"
            }
        }) {
            Text("Save Case")
        }

        if (message.isNotBlank()) {
            Text(message, color = MaterialTheme.colors.primary)
        }
    }
}
@Composable
fun BuildRoundScreen(inventoryManager: InventoryManager, onBack: () -> Unit) {
    var cartridgeName by remember { mutableStateOf("") }
    var charge by remember { mutableStateOf("") }
    var coal by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    var bulletId by remember { mutableStateOf<String?>(null) }
    var powderId by remember { mutableStateOf<String?>(null) }
    var primerId by remember { mutableStateOf<String?>(null) }
    var caseId by remember { mutableStateOf<String?>(null) }

    val bullets = inventoryManager.getBullets()
    val powders = inventoryManager.getPowders()
    val primers = inventoryManager.getPrimers()
    val cases = inventoryManager.getCases()

    ScreenScaffold(title = "Build Round", onBack = onBack) {

        OutlinedTextField(
            value = cartridgeName,
            onValueChange = { cartridgeName = it },
            label = { Text("Cartridge Name") }
        )

        // BULLET SELECTOR
        Text("Select Bullet:")
        Text(
            "ID            | Brand          | Name               | Qty",
            fontFamily = FontFamily.Monospace
        )
        Divider()
        DropdownSelector(
            items = bullets.map {
                it.id to String.format(
                    "%-14s | %-14s | %-20s | %-6d",
                    it.id, it.brand, it.name, it.quantity
                )
            },
            selectedId = bulletId,
            onSelected = { bulletId = it }
        )

        // POWDER SELECTOR
        Text("Select Powder:")
        Text(
            "ID            | Brand          | Name               | Qty(lb)",
            fontFamily = FontFamily.Monospace
        )
        Divider()
        DropdownSelector(
            items = powders.map {
                it.id to String.format(
                    "%-14s | %-14s | %-20s | %-6.2f",
                    it.id, it.brand, it.name, it.quantityLb
                )
            },
            selectedId = powderId,
            onSelected = { powderId = it }
        )

        // PRIMER SELECTOR
        Text("Select Primer:")
        Text(
            "ID            | Brand          | Type               | Qty",
            fontFamily = FontFamily.Monospace
        )
        Divider()
        DropdownSelector(
            items = primers.map {
                it.id to String.format(
                    "%-14s | %-14s | %-20s | %-6d",
                    it.id, it.brand, it.type.toString(), it.quantity
                )
            },
            selectedId = primerId,
            onSelected = { primerId = it }
        )

        // CASE SELECTOR
        Text("Select Case:")
        Text(
            "ID            | Brand          | Caliber            | Qty",
            fontFamily = FontFamily.Monospace
        )
        Divider()
        DropdownSelector(
            items = cases.map {
                it.id to String.format(
                    "%-14s | %-14s | %-20s | %-6d",
                    it.id, it.brand, it.caliber, it.quantity
                )
            },
            selectedId = caseId,
            onSelected = { caseId = it }
        )

        OutlinedTextField(charge, { charge = it }, label = { Text("Powder charge (gr)") })
        OutlinedTextField(coal, { coal = it }, label = { Text("COAL") })
        OutlinedTextField(quantity, { quantity = it }, label = { Text("Quantity to load") })

        Button(onClick = {
            val cartridge = CartridgeRepository.findByName(cartridgeName)
            val ch = charge.toDoubleOrNull()
            val cl = coal.toDoubleOrNull()
            val q = quantity.toIntOrNull()

            if (cartridge == null ||
                bulletId == null ||
                powderId == null ||
                primerId == null ||
                caseId == null ||
                ch == null ||
                cl == null ||
                q == null
            ) {
                message = "Invalid input or missing selection"
            } else {
                try {
                    inventoryManager.loadRounds(
                        cartridgeName = cartridge.name,
                        bulletId = bulletId!!,
                        powderId = powderId!!,
                        primerId = primerId!!,
                        caseId = caseId!!,
                        powderChargeGr = ch,
                        coalIn = cl,
                        quantity = q
                    )
                    message = "Round(s) loaded"
                } catch (e: Exception) {
                    message = "Error: ${e.message}"
                }
            }
        }) {
            Text("Build Round")
        }

        if (message.isNotBlank()) {
            Text(message, color = MaterialTheme.colors.primary)
        }
    }
}
@Composable
fun ShowRoundsScreen(inventoryManager: InventoryManager, onBack: () -> Unit) {
    val rounds = inventoryManager.getRounds()

    ScreenScaffold(title = "Show Rounds", onBack = onBack) {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(rounds) { round ->
                Text(inventoryManager.resolveRound(round))
                Divider()
            }
        }
    }
}

@Composable
fun ShowComponentsScreen(inventoryManager: InventoryManager, onBack: () -> Unit) {
    val bullets = inventoryManager.getBullets()
    val powders = inventoryManager.getPowders()
    val primers = inventoryManager.getPrimers()
    val cases = inventoryManager.getCases()

    ScreenScaffold(title = "Show Components", onBack = onBack) {

        // BULLETS
        Text("BULLETS", style = MaterialTheme.typography.h6)
        Text(
            "ID            | Brand          | Name               | Weight(gr) | Diameter(in) | Cost/Unit | Qty",
            fontFamily = FontFamily.Monospace,
            style = MaterialTheme.typography.subtitle2
        )
        Divider()
        LazyColumn(
            modifier = Modifier.heightIn(max = 200.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(bullets) {
                Text(
                    String.format(
                        "%-14s | %-14s | %-20s | %-12.2f | %-12.3f | %-10.2f | %-6d",
                        it.id, it.brand, it.name, it.weightGr, it.diameterIn, it.costPerUnit, it.quantity
                    ),
                    fontFamily = FontFamily.Monospace
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        // POWDERS
        Text("POWDERS", style = MaterialTheme.typography.h6)
        Text(
            "ID            | Brand          | Name               | Cost/Lb | Grains/Lb | Qty(Lb)",
            fontFamily = FontFamily.Monospace,
            style = MaterialTheme.typography.subtitle2
        )
        Divider()
        LazyColumn(
            modifier = Modifier.heightIn(max = 200.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(powders) {
                Text(
                    String.format(
                        "%-14s | %-14s | %-20s | %-8.2f | %-10d | %-6.2f",
                        it.id, it.brand, it.name, it.costPerLb, it.grainsPerLb, it.quantityLb
                    ),
                    fontFamily = FontFamily.Monospace
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        // PRIMERS
        Text("PRIMERS", style = MaterialTheme.typography.h6)
        Text(
            "ID            | Brand          | Type               | Cost/1000 | Qty",
            fontFamily = FontFamily.Monospace,
            style = MaterialTheme.typography.subtitle2
        )
        Divider()
        LazyColumn(
            modifier = Modifier.heightIn(max = 200.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(primers) {
                Text(
                    String.format(
                        "%-14s | %-14s | %-20s | %-12.2f | %-6d",
                        it.id, it.brand, it.type.toString(), it.costPer1000, it.quantity
                    ),
                    fontFamily = FontFamily.Monospace
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        // CASES
        Text("CASES", style = MaterialTheme.typography.h6)
        Text(
            "ID            | Brand          | Caliber            | Times Fired | Cost/Unit | Qty",
            fontFamily = FontFamily.Monospace,
            style = MaterialTheme.typography.subtitle2
        )
        Divider()
        LazyColumn(
            modifier = Modifier.heightIn(max = 200.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(cases) {
                Text(
                    String.format(
                        "%-14s | %-14s | %-20s | %-12d | %-10.2f | %-6d",
                        it.id, it.brand, it.caliber, it.timesFired, it.costPerUnit, it.quantity
                    ),
                    fontFamily = FontFamily.Monospace
                )
            }
        }
    }   // closes ScreenScaffold content

}   // closes ShowComponentsScreen
@Composable
fun ModifyDeleteScreen(
    inventoryManager: InventoryManager,
    onBack: () -> Unit
) {
    var typeChoice by remember { mutableStateOf("bullet") }
    var idInput by remember { mutableStateOf("") }
    var fieldInput by remember { mutableStateOf("") }
    var newValue by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    val bullets = inventoryManager.getBullets()
    val powders = inventoryManager.getPowders()
    val primers = inventoryManager.getPrimers()
    val cases = inventoryManager.getCases()

    val bulletFmt = "%-14s | %-14s | %-20s | %-12s | %-14s | %-12s | %-6s"
    val powderFmt = "%-14s | %-14s | %-20s | %-10s | %-12s | %-8s"
    val primerFmt = "%-14s | %-14s | %-20s | %-12s | %-6s"
    val caseFmt   = "%-14s | %-14s | %-20s | %-12s | %-10s | %-6s"

    val header = when (typeChoice) {
        "bullet" -> String.format(
            bulletFmt,
            "ID", "Brand", "Name", "Weight(gr)", "Diameter(in)", "Cost/Unit", "Qty"
        )
        "powder" -> String.format(
            powderFmt,
            "ID", "Brand", "Name", "Cost/Lb", "Grains/Lb", "Qty(Lb)"
        )
        "primer" -> String.format(
            primerFmt,
            "ID", "Brand", "Type", "Cost/1000", "Qty"
        )
        "case" -> String.format(
            caseFmt,
            "ID", "Brand", "Caliber", "Times Fired", "Cost/Unit", "Qty"
        )
        else -> ""
    }

    val rows = when (typeChoice) {
        "bullet" -> bullets.map {
            String.format(
                bulletFmt,
                it.id, it.brand, it.name, it.weightGr, it.diameterIn, it.costPerUnit, it.quantity
            )
        }
        "powder" -> powders.map {
            String.format(
                powderFmt,
                it.id, it.brand, it.name, it.costPerLb, it.grainsPerLb, it.quantityLb
            )
        }
        "primer" -> primers.map {
            String.format(
                primerFmt,
                it.id, it.brand, it.type.toString(), it.costPer1000, it.quantity
            )
        }
        "case" -> cases.map {
            String.format(
                caseFmt,
                it.id, it.brand, it.caliber, it.timesFired, it.costPerUnit, it.quantity
            )
        }
        else -> emptyList()
    }

    ScreenScaffold(title = "Modify/Delete Component", onBack = onBack) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Type:")
            Spacer(Modifier.width(12.dp))
            DropdownSelector(
                items = listOf(
                    "bullet" to "Bullet",
                    "powder" to "Powder",
                    "primer" to "Primer",
                    "case" to "Case"
                ),
                selectedId = typeChoice,
                onSelected = { typeChoice = it }
            )
        }

        Text(header, fontFamily = FontFamily.Monospace)
        Divider()

        LazyColumn(
            modifier = Modifier.heightIn(max = 260.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(rows) { line ->
                Text(line, fontFamily = FontFamily.Monospace)
            }
        }

        OutlinedTextField(idInput, { idInput = it }, label = { Text("ID") })

        Button(onClick = {
            val ok = inventoryManager.deleteComponent(typeChoice, idInput)
            message = if (ok) "Deleted." else "Delete failed."
        }) {
            Text("Delete")
        }

        Spacer(Modifier.height(16.dp))

        // FIELD NAME HELPER
        val fieldList = when (typeChoice) {
            "bullet" -> listOf("brand", "name", "weightGr", "diameterIn", "costPerUnit", "quantity")
            "powder" -> listOf("brand", "name", "costPerLb", "grainsPerLb", "quantityLb")
            "primer" -> listOf("brand", "type", "costPer1000", "quantity")
            "case"   -> listOf("brand", "caliber", "timesFired", "costPerUnit", "quantity")
            else -> emptyList()
        }

        Text("Available Fields:", style = MaterialTheme.typography.subtitle1)
        fieldList.forEach { field ->
            Text("- $field", fontFamily = FontFamily.Monospace)
        }

        Spacer(Modifier.height(12.dp))


        Text("Modify Field:")
        OutlinedTextField(fieldInput, { fieldInput = it }, label = { Text("Field name or number") })
        OutlinedTextField(newValue, { newValue = it }, label = { Text("New value") })

        Button(onClick = {
            val ok = inventoryManager.modifyComponent(typeChoice, idInput, fieldInput, newValue)
            message = if (ok) "Updated." else "Update failed."
        }) {
            Text("Apply Change")
        }

        if (message.isNotBlank()) {
            Text(message, color = MaterialTheme.colors.primary)
        }
    }
}

@Composable
fun SimulationScreen(onBack: () -> Unit) {
    var chargeInput by remember { mutableStateOf("75.0") }
    var resultText by remember { mutableStateOf("Enter a charge and run simulation") }

    ScreenScaffold(title = "Simulation", onBack = onBack) {

        Text("Powder Charge (grains):")

        OutlinedTextField(
            value = chargeInput,
            onValueChange = { chargeInput = it },
            modifier = Modifier.width(200.dp)
        )

        Button(onClick = {
            val charge = chargeInput.toDoubleOrNull()

            resultText = if (charge != null) {
                val result = SimulationService.runSimulation(charge)

                """
                --- COST BREAKDOWN ---
                Bullet: ${"%.3f".format(result.bulletCost)}
                Primer: ${"%.3f".format(result.primerCost)}
                Powder: ${"%.3f".format(result.powderCost)}
                Case: ${"%.3f".format(result.caseCost)}

                Total Cost per Round: ${"%.3f".format(result.cost)}

                --- PERFORMANCE ---
                Estimated FPS: ${"%.0f".format(result.fps)}
                Status: ${result.status}
                """.trimIndent()
            } else {
                "Invalid input"
            }
        }) {
            Text("Run Simulation")
        }

        Divider(Modifier.padding(vertical = 16.dp))

        Text(resultText, fontFamily = FontFamily.Monospace)
    }
}

@Composable
fun ScreenScaffold(
    title: String,
    onBack: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Button(onClick = onBack) { Text("Back") }
            Spacer(Modifier.width(12.dp))
            Text(title, style = MaterialTheme.typography.h5)
        }

        Spacer(Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),   // ← SCROLL FIX
            verticalArrangement = Arrangement.spacedBy(12.dp),
            content = content
        )
    }
}


@Composable
fun DropdownSelector(
    items: List<Pair<String, String>>,
    selectedId: String?,
    onSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    val selectedLabel = items.find { it.first == selectedId }?.second ?: "Select..."

    Column {
        Button(onClick = { expanded = true }) {
            Text(selectedLabel, fontFamily = FontFamily.Monospace)
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            items.forEach { (id, label) ->
                DropdownMenuItem(onClick = {
                    onSelected(id)
                    expanded = false
                }) {
                    Text(label, fontFamily = FontFamily.Monospace)
                }
            }
        }
    }
}
