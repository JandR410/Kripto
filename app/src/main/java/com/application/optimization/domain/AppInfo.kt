package com.application.optimization.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "app_info")
data class AppInfo(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val usageFrequency: Int,
    val memoryConsumption: Double,
    val cpuUsage: Double,
    val lastUpdated: String,
    val criticality: Int,
    val userCount: Int,
    val storageUsage: Double,
)