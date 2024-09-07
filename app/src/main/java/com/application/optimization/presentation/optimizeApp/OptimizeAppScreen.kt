package com.application.optimization.presentation.optimizeApp

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.application.optimization.domain.AppInfo
import com.application.optimization.presentation.component.DropdownComponent
import com.application.optimization.presentation.home.HomeAction
import com.application.optimization.ui.theme.White
import com.application.optimization.usecase.OptimizationColor
import com.application.optimization.usecase.OptimizationRecommendation

@Composable
fun OptimizeAppScreen(
    modifier: Modifier = Modifier,
    listAppInfo: List<AppInfo>,
    listOptimization: List<OptimizationRecommendation>,
    action: (HomeAction) -> Unit
) {
    var selectedCriteria by remember { mutableStateOf("") }
    var optimizedApps by remember { mutableStateOf(listOptimization) }
    var selectedApp by remember { mutableStateOf<AppInfo?>(null) }

    // Filtrar y ordenar las aplicaciones optimizadas cada vez que se cambia el criterio seleccionado
    LaunchedEffect(selectedCriteria, listOptimization) {
        optimizedApps = listOptimization
            .filter { recommendation ->
                when (selectedCriteria) {
                    "Obsoletas" -> recommendation.recommendation.contains("Obsoleta", ignoreCase = true)
                    "Subutilizadas" -> recommendation.recommendation.contains("Subutilizada", ignoreCase = true)
                    "Redistribuir Recursos" -> recommendation.recommendation.contains("Redistribuir", ignoreCase = true)
                    "Aplicaciones Críticas" -> recommendation.recommendation.contains("Crítica", ignoreCase = true)
                    "Criterios Combinados" -> recommendation.recommendation.contains("Criterio", ignoreCase = true)
                    else -> true // Mostrar todas si no se selecciona ningún criterio
                }
            }
            // Ordenar primero por criticidad (rojo > amarillo > verde)
            .sortedWith(compareByDescending<OptimizationRecommendation> {
                when (it.color) {
                    OptimizationColor.RED -> 3
                    OptimizationColor.YELLOW -> 2
                    OptimizationColor.GREEN -> 1
                }
            }.thenByDescending {
                // Dentro de cada color, ordenar por la criticidad
                it.appInfo.criticality
            })
    }

    Column(modifier = modifier.padding(16.dp)) {
        Text(
            text = "Optimización de Aplicaciones",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        val criteriaOptions = listOf(
            "Obsoletas",
            "Subutilizadas",
            "Redistribuir Recursos",
            "Aplicaciones Críticas",
            "Criterios Combinados"
        )

        // Dropdown para seleccionar el criterio de optimización
        DropdownComponent(
            modifier = Modifier.fillMaxWidth(),
            selectedOption = selectedCriteria,
            options = criteriaOptions,
            onOptionSelected = {
                selectedCriteria = it
                action(HomeAction.OptimizeAppUseCase(listAppInfo, selectedCriteria)) // Ejecutar la acción según el criterio
            },
            labelText = "Selecciona un criterio"
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Resultado de la Optimización",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar las aplicaciones optimizadas filtradas
        LazyColumn(modifier = Modifier.background(White)) {
            items(optimizedApps) { recommendation ->
                val app = recommendation.appInfo
                val borderColor = when (recommendation.color) {
                    OptimizationColor.RED -> Color.Red
                    OptimizationColor.YELLOW -> Color(0xFFFFC107)
                    OptimizationColor.GREEN -> Color.Green
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .border(2.dp, borderColor, RectangleShape)
                        .clickable { selectedApp = app },
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .background(White)
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = app.name,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )

                        Text(
                            text = recommendation.recommendation,
                            color = borderColor,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Mostrar detalles de la aplicación seleccionada
        selectedApp?.let { app ->
            AlertDialog(
                onDismissRequest = { selectedApp = null },
                confirmButton = {
                    TextButton(onClick = { selectedApp = null }) {
                        Text("Cerrar")
                    }
                },
                title = {
                    Text(
                        text = app.name.uppercase(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                },
                text = {
                    Column {
                        Text("Memoria: ${app.memoryConsumption} MB")
                        Text("CPU: ${app.cpuUsage} %")
                        Text("Usuarios Activos: ${app.userCount}")
                        Text("Criticidad: ${app.criticality}/10")
                        Text("Almacenamiento: ${app.storageUsage} GB")
                    }
                }
            )
        }
    }
}
