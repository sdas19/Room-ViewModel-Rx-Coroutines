package com.example.githubsampleapplication.Model

import com.google.gson.annotations.SerializedName

data class BuiltByResponseModel(
    @SerializedName("username") var userName : String,
    @SerializedName("href") var href : String,
    @SerializedName("avatar") var avatar : String
)

