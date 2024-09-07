package com.application.optimization.data.repositoryImpl

import com.application.optimization.data.repository.AppRepository
import com.application.optimization.data.room.AppInfoDao
import com.application.optimization.domain.AppInfo

class AppRepositoryImpl(
    private val appInfoDao: AppInfoDao
) : AppRepository {

    override suspend fun getApps(): List<AppInfo> {
        return appInfoDao.getAllApps()
    }

    override suspend fun insertApp(appInfo: AppInfo) {
        appInfoDao.insertApp(appInfo)
    }

    override suspend fun deleteApp(appInfo: AppInfo) {
        appInfoDao.deleteApp(appInfo)
    }
}