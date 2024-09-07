package com.application.optimization.di

import android.content.Context
import androidx.room.Room
import com.application.optimization.data.repository.AppRepository
import com.application.optimization.data.repositoryImpl.AppRepositoryImpl
import com.application.optimization.data.room.AppInfoDao
import com.application.optimization.data.room.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(appContext, AppDatabase::class.java, "app_database").build()
    }

    @Provides
    fun provideAppInfoDao(appDatabase: AppDatabase): AppInfoDao {
        return appDatabase.appInfoDao()
    }

    @Provides
    @Singleton
    fun provideAppRepository(appInfoDao: AppInfoDao): AppRepository {
        return AppRepositoryImpl(appInfoDao)
    }
}