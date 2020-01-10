package com.example.githubsampleapplication.JoinTest

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRepos(repoList: List<Repo>)

    @Query("SELECT * FROM Repo")
    fun getAllRepository(): List<Repo>
}