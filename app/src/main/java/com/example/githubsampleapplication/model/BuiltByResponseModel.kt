package com.example.githubsampleapplication.model

import com.google.gson.annotations.SerializedName

data class BuiltByResponseModel(
    @SerializedName("username") var userName : String,
    @SerializedName("href") var href : String,
    @SerializedName("avatar") var avatar : String
)

