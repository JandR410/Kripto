package com.application.optimization.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.application.optimization.domain.AppInfo

@Dao
interface AppInfoDao {
    @Query("SELECT * FROM app_info")
    suspend fun getAllApps(): List<AppInfo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertApp(appInfo: AppInfo)

    @Delete
    suspend fun deleteApp(appInfo: AppInfo)
}