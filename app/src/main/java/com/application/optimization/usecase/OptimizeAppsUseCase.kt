package com.application.optimization.usecase

import com.application.optimization.domain.AppInfo
import com.application.optimization.usecase.OptimizationColor
import javax.inject.Inject

class OptimizeAppsUseCase @Inject constructor() {

    fun execute(apps: List<AppInfo>, criteria: String): List<OptimizationRecommendation> {
        return when (criteria) {
            "Obsoletas" -> findObsoleteApps(apps)
            "Subutilizadas" -> findUnderutilizedApps(apps)
            "Redistribuir Recursos" -> redistributeResources(apps)
            "Aplicaciones Críticas" -> findCriticalAppsToEliminate(apps)
            "Criterios Combinados" -> applyWeightedCriteria(apps)
            else -> apps.map { OptimizationRecommendation(it, "No se requiere acción", OptimizationColor.GREEN) }
        }
    }

    private fun findObsoleteApps(apps: List<AppInfo>): List<OptimizationRecommendation> {
        return apps.map { app ->
            when {
                app.usageFrequency == 0 -> OptimizationRecommendation(app, "Eliminar: aplicación obsoleta (nunca usada)", OptimizationColor.RED)
                app.usageFrequency in 1..3 -> OptimizationRecommendation(app, "Revisar: aplicación obsoleta (uso bajo)", OptimizationColor.YELLOW)
                else -> OptimizationRecommendation(app, "Mantener: aplicación con uso moderado", OptimizationColor.GREEN)
            }
        }
    }

    private fun findUnderutilizedApps(apps: List<AppInfo>): List<OptimizationRecommendation> {
        return apps.map { app ->
            when {
                app.cpuUsage < 10.0 && app.memoryConsumption < 50.0 -> OptimizationRecommendation(app, "Eliminar: aplicación subutilizada", OptimizationColor.RED)
                app.cpuUsage in 10.0..20.0 -> OptimizationRecommendation(app, "Reasignar: aplicación subutilizada", OptimizationColor.YELLOW)
                else -> OptimizationRecommendation(app, "Mantener: aplicación con uso adecuado", OptimizationColor.GREEN)
            }
        }
    }

    private fun findCriticalAppsToEliminate(apps: List<AppInfo>): List<OptimizationRecommendation> {
        return apps.map { app ->
            when {
                app.criticality >= 9 && app.cpuUsage > 85.0 -> OptimizationRecommendation(app, "Eliminar: aplicación crítica con alto consumo", OptimizationColor.RED)
                app.criticality in 6..8 -> OptimizationRecommendation(app, "Monitorear: aplicación crítica con consumo moderado", OptimizationColor.YELLOW)
                else -> OptimizationRecommendation(app, "Mantener: aplicación con consumo controlado", OptimizationColor.GREEN)
            }
        }
    }

    private fun redistributeResources(apps: List<AppInfo>): List<OptimizationRecommendation> {
        return apps.map { app ->
            when {
                app.usageFrequency > 15 && app.cpuUsage > 80.0 -> OptimizationRecommendation(app, "Incrementar recursos: alta demanda", OptimizationColor.GREEN)
                app.usageFrequency in 5..15 && app.cpuUsage in 30.0..80.0 -> OptimizationRecommendation(app, "Monitorear recursos: demanda moderada", OptimizationColor.YELLOW)
                else -> OptimizationRecommendation(app, "Reducir recursos: baja demanda", OptimizationColor.RED)
            }
        }
    }

    private fun applyWeightedCriteria(apps: List<AppInfo>): List<OptimizationRecommendation> {
        return apps.map { app ->
            val score = (app.cpuUsage * 0.4) + (app.memoryConsumption * 0.3) + (app.criticality * 0.3)
            val (action, color) = when {
                score > 80 -> "Priorizar: alto uso de recursos" to OptimizationColor.RED
                score in 50.0..80.0 -> "Monitorear: uso moderado de recursos" to OptimizationColor.YELLOW
                else -> "Reasignar: uso bajo de recursos" to OptimizationColor.GREEN
            }
            OptimizationRecommendation(app, action, color)
        }
    }
}

data class OptimizationRecommendation(
    val appInfo: AppInfo,
    val recommendation: String,
    val color: OptimizationColor
)

enum class OptimizationColor {
    RED, YELLOW, GREEN
}
