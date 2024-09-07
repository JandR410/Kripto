package com.application.optimization.presentation.graph

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.application.optimization.domain.AppInfo
import com.application.optimization.presentation.component.DropdownComponent
import com.application.optimization.presentation.home.HomeAction
import com.application.optimization.usecase.OptimizationColor
import com.application.optimization.usecase.OptimizationRecommendation

@Composable
fun OptimizeAppGraphs(
    modifier: Modifier = Modifier,
    listAppInfo: List<AppInfo>,
    listOptimization: List<OptimizationRecommendation>,
    action: (HomeAction) -> Unit
) {
    var selectedCriteria by remember { mutableStateOf("") }
    var optimizedApps by remember { mutableStateOf(listOptimization) }
    var showOptimos by remember { mutableStateOf(false) } // Controlar si mostramos los óptimos

    LaunchedEffect(listOptimization) {
        optimizedApps = listOptimization
    }

    Column(modifier = modifier.padding(16.dp)) {
        Text(
            text = "Optimización de Aplicaciones (Gráficos)",
            fontSize = 20.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Dropdown para seleccionar el criterio de optimización
        val criteriaOptions = listOf(
            "Obsoletas",
            "Subutilizadas",
            "Redistribuir Recursos",
            "Aplicaciones Críticas",
            "Criterios Combinados"
        )

        DropdownComponent(
            modifier = Modifier.fillMaxWidth(),
            selectedOption = selectedCriteria,
            options = criteriaOptions,
            onOptionSelected = {
                selectedCriteria = it
                action(
                    HomeAction.OptimizeAppUseCase(
                        appInfo = listAppInfo,
                        selectedCriteria = selectedCriteria
                    )
                )
            },
            labelText = "Selecciona un criterio"
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para alternar la visualización de los óptimos (verdes)
        TextButton(onClick = {
            showOptimos = !showOptimos
            action(HomeAction.ToggleOptimos)
        }) {
            Text(text = if (showOptimos) "Ocultar Óptimos" else "Mostrar Óptimos")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Gráfico de Optimización ($selectedCriteria)",
            fontSize = 18.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar gráfico
        BarGraph(
            modifier = Modifier,
            apps = optimizedApps,
            criteria = selectedCriteria
        )
    }
}

@Composable
fun BarGraph(
    modifier: Modifier,
    apps: List<OptimizationRecommendation>,
    criteria: String
) {
    // Ordenar las aplicaciones según el valor de `criteria` de mayor a menor
    val sortedApps = apps.sortedByDescending { app ->
        when (criteria) {
            "Obsoletas" -> app.appInfo.memoryConsumption
            "Subutilizadas" -> app.appInfo.cpuUsage
            "Redistribuir Recursos" -> app.appInfo.userCount.toDouble()
            "Aplicaciones Críticas" -> app.appInfo.criticality.toDouble()
            "Criterios Combinados" -> app.appInfo.storageUsage
            else -> 0.0
        }
    }

    val barColor: (AppInfo) -> Color = { app ->
        when {
            app.criticality >= 8 -> Color.Red
            app.criticality in 5..7 -> Color.Yellow
            else -> Color.Green
        }
    }

    Column(modifier = modifier.fillMaxWidth()) {
        sortedApps.forEach { app ->
            val value = when (criteria) {
                "Obsoletas" -> app.appInfo.memoryConsumption
                "Subutilizadas" -> app.appInfo.cpuUsage
                "Redistribuir Recursos" -> app.appInfo.userCount.toDouble()
                "Aplicaciones Críticas" -> app.appInfo.criticality.toDouble()
                "Criterios Combinados" -> app.appInfo.storageUsage
                else -> 0.0
            }

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = app.appInfo.name,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Canvas(
                    modifier = Modifier
                        .weight(1f)
                        .height(30.dp)
                ) {
                    val maxValue = sortedApps.maxOf { recommendation ->
                        when (criteria) {
                            "Obsoletas" -> recommendation.appInfo.memoryConsumption
                            "Subutilizadas" -> recommendation.appInfo.cpuUsage
                            "Redistribuir Recursos" -> recommendation.appInfo.userCount.toDouble()
                            "Aplicaciones Críticas" -> recommendation.appInfo.criticality.toDouble()
                            "Criterios Combinados" -> recommendation.appInfo.storageUsage
                            else -> 1.0
                        }
                    }

                    val barWidth = (value / maxValue) * size.width

                    drawRoundRect(
                        color = barColor(app.appInfo),
                        size = Size(barWidth.toFloat(), size.height),
                        cornerRadius = CornerRadius(8f, 8f)
                    )
                }
            }
        }
    }
}
