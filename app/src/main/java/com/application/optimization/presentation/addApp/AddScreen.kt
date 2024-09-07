package com.application.optimization.presentation.addApp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.application.optimization.presentation.home.HomeAction

@Composable
fun AddApp(
    modifier: Modifier,
    actionHandler: (HomeAction) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var usageFrequency by remember { mutableStateOf("") }
    var memoryConsumption by remember { mutableStateOf("") }
    var cpuUsage by remember { mutableStateOf("") }
    var lastUpdated by remember { mutableStateOf("") }
    var criticality by remember { mutableStateOf("") }
    var userCount by remember { mutableStateOf("") }
    var storageUsage by remember { mutableStateOf("") }

    LazyColumn(modifier = modifier.padding(16.dp)) {
        item {
            Box(modifier = Modifier.padding(vertical = 12.dp)) {
                Text(text = "Formulario de App")
            }
        }

        item {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre de la App") },
                modifier = Modifier.fillMaxWidth()
            )
        }
        item {
            OutlinedTextField(
                value = usageFrequency,
                onValueChange = { usageFrequency = it },
                label = { Text("Frecuencia de Uso (sesiones/semana)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                )
            )
        }
        item {
            OutlinedTextField(
                value = memoryConsumption,
                onValueChange = { memoryConsumption = it },
                label = { Text("Memoria Consumida (MB)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                )
            )
        }
        item {
            OutlinedTextField(
                value = cpuUsage,
                onValueChange = { cpuUsage = it },
                label = { Text("Uso de CPU (%)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                )
            )
        }
        item {
            OutlinedTextField(
                value = lastUpdated,
                onValueChange = { lastUpdated = it },
                label = { Text("Última Actualización") },
                modifier = Modifier.fillMaxWidth()
            )
        }
        item {
            OutlinedTextField(
                value = criticality,
                onValueChange = { criticality = it },
                label = { Text("Criticidad (1 a 10)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                )
            )
        }
        item {
            OutlinedTextField(
                value = userCount,
                onValueChange = { userCount = it },
                label = { Text("Usuarios Activos") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                )
            )
        }
        item {
            OutlinedTextField(
                value = storageUsage,
                onValueChange = { storageUsage = it },
                label = { Text("Almacenamiento (GB)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                )
            )
        }

        item {
            Button(onClick = {
                actionHandler(
                    HomeAction.Add(
                        listOf(
                            name,
                            usageFrequency,
                            memoryConsumption,
                            cpuUsage,
                            lastUpdated,
                            criticality,
                            userCount,
                            storageUsage
                        )
                    )
                )

                name = ""
                usageFrequency = ""
                memoryConsumption = ""
                cpuUsage = ""
                lastUpdated = ""
                criticality = ""
                userCount = ""
                storageUsage = ""
            }) {
                Text("Agregar Aplicación")
            }
        }
    }
}