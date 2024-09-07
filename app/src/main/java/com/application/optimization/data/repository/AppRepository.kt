package com.application.optimization.data.repository

import com.application.optimization.domain.AppInfo

interface AppRepository {
    suspend fun getApps(): List<AppInfo>
    suspend fun insertApp(appInfo: AppInfo)
    suspend fun deleteApp(appInfo: AppInfo)
}
