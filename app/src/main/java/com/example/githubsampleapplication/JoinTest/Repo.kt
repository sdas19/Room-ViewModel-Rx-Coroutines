package com.example.githubsampleapplication.JoinTest

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Repo")
data class Repo(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name")val name: String,
    @ColumnInfo(name = "url")val url: String
)