package com.application.optimization.data.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.application.optimization.data.room.AppInfoDao
import com.application.optimization.domain.AppInfo

@Database(entities = [AppInfo::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appInfoDao(): AppInfoDao
}
