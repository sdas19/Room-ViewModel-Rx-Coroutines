package com.example.githubsampleapplication.JoinTest

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserRepoJoinDao {

    @Insert
    fun insert(userRepoJoinList: List<UserRepoJoin>)

    @Query("SELECT * FROM User INNER JOIN user_repo_join ON User.id=user_repo_join.userId WHERE user_repo_join.repoId=:repoId")
    fun getUsersForRepository(repoId: Int): LiveData<List<User>>

    @Query("SELECT * FROM Repo INNER JOIN user_repo_join ON Repo.id=user_repo_join.repoId WHERE user_repo_join.userId=:userId")
    fun getRepositoriesForUsers(userId: Int): LiveData<List<Repo>>
}