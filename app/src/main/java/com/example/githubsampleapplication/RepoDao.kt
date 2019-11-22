package com.example.githubsampleapplication

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.githubsampleapplication.model.RepositoryResponseModel
import io.reactivex.Flowable

@Dao
interface RepoDao {

    @Query("SELECT * FROM Repo")
    fun getAllRepos(): LiveData<List<RepositoryResponseModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(repoList: List<RepositoryResponseModel>)

    @Query("DELETE FROM Repo")
    fun deleteAll()

}