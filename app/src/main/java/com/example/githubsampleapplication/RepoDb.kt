package com.example.githubsampleapplication

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.githubsampleapplication.JoinTest.Repo
import com.example.githubsampleapplication.JoinTest.User
import com.example.githubsampleapplication.JoinTest.UserRepoJoin
import com.example.githubsampleapplication.model.RepositoryResponseModel
import com.example.githubsampleapplication.JoinTest.UserRepoJoinDao
import com.example.githubsampleapplication.JoinTest.UserDao
import com.example.githubsampleapplication.JoinTest.RepoDao



@Database(entities = [(RepositoryResponseModel::class),(User::class),(Repo::class),(UserRepoJoin::class)], version = 1)
@TypeConverters(BuiltByTypeConverter::class)
abstract class RepoDb : RoomDatabase() {
    abstract fun getRepositoryDao(): RepositoryDao
    abstract fun getRepoDao(): RepoDao
    abstract fun getUserDao(): UserDao
    abstract fun getUserRepoJoinDao(): UserRepoJoinDao
}