package com.example.githubsampleapplication.JoinTest

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.Index

@Entity(
    tableName = "user_repo_join",
    primaryKeys = ["userId", "repoId"],
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = Repo::class,
            parentColumns = ["id"],
            childColumns = ["repoId"],
            onDelete = CASCADE
        )
    ]
)
class UserRepoJoin(@ColumnInfo(name = "userId",index = true) val userId: Int, @ColumnInfo(name = "repoId",index = true) val repoId: Int)