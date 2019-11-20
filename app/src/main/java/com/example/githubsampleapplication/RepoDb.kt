package com.example.githubsampleapplication

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.githubsampleapplication.model.RepositoryResponseModel

@Database(entities = [(RepositoryResponseModel::class)], version = 1)
@TypeConverters(BuiltByTypeConverter::class)
abstract class RepoDb : RoomDatabase(){
    abstract fun getRepoDao() : RepoDao
}