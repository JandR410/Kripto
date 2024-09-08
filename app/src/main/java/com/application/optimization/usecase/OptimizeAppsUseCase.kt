package com.application.optimization.usecase

import android.content.Context
import com.application.optimization.domain.AppInfo
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream
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

    fun exportAppsToExcel(context: Context, apps: List<AppInfo>) {
        val workbook = XSSFWorkbook()
        val sheet = workbook.createSheet("Aplicaciones")

        val headerRow = sheet.createRow(0)
        headerRow.createCell(0).setCellValue("Nombre")
        headerRow.createCell(1).setCellValue("Frecuencia de Uso")
        headerRow.createCell(2).setCellValue("Memoria Consumida (MB)")
        headerRow.createCell(3).setCellValue("Uso de CPU (%)")
        headerRow.createCell(4).setCellValue("Última Actualización")
        headerRow.createCell(5).setCellValue("Criticidad")
        headerRow.createCell(6).setCellValue("Usuarios Activos")
        headerRow.createCell(7).setCellValue("Almacenamiento (GB)")

        apps.forEachIndexed { index, app ->
            val row = sheet.createRow(index + 1)
            row.createCell(0).setCellValue(app.name)
            row.createCell(1).setCellValue(app.usageFrequency.toString())
            row.createCell(2).setCellValue(app.memoryConsumption)
            row.createCell(3).setCellValue(app.cpuUsage)
            row.createCell(4).setCellValue(app.lastUpdated)
            row.createCell(5).setCellValue(app.criticality.toString())
            row.createCell(6).setCellValue(app.userCount.toString())
            row.createCell(7).setCellValue(app.storageUsage)
        }

        val file = File(context.getExternalFilesDir(null), "aplicaciones.xlsx")
        FileOutputStream(file).use { outputStream ->
            workbook.write(outputStream)
        }
        workbook.close()
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
