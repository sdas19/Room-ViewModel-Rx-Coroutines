package com.example.githubsampleapplication.di.module

import android.app.Application
import dagger.Provides
import javax.inject.Singleton
import androidx.room.Room
import com.example.githubsampleapplication.RepoDao
import com.example.githubsampleapplication.RepoDb
import dagger.Module

@Module
class DatabaseModule {

    private val dBName = "repo_database.db"

    @Singleton
    @Provides
    fun provideDatabase(application: Application): RepoDb {
        return Room.databaseBuilder(
            application,
            RepoDb::class.java,
            dBName
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideRepoDao(db: RepoDb): RepoDao {
        return db.getRepoDao()
    }

}