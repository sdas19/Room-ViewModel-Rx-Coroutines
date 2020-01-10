package com.example.githubsampleapplication.JoinTest

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsers(repoList: List<User>)

    @Query("SELECT * FROM User")
    fun getAllUsers(): List<User>
}