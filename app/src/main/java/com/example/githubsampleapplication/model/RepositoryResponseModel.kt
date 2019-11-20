package com.example.githubsampleapplication.model

import com.google.gson.annotations.SerializedName
import com.squareup.picasso.Picasso
import androidx.databinding.BindingAdapter
import com.example.githubsampleapplication.R
import de.hdodenhof.circleimageview.CircleImageView


data class RepositoryResponseModel(
    @SerializedName("author") var author: String,
    @SerializedName("name") var name: String,
    @SerializedName("avatar") var avatar: String,
    @SerializedName("url") var url: String,
    @SerializedName("description") var description: String,
    @SerializedName("language") var language: String,
    @SerializedName("languageColor") var languageColor: String,
    @SerializedName("stars") var stars: Int,
    @SerializedName("forks") var forks: Int,
    @SerializedName("currentPeriodStars") var currentPeriodStars: Int,
    @SerializedName("builtBy") var builtBy: List<BuiltByResponseModel>
)
