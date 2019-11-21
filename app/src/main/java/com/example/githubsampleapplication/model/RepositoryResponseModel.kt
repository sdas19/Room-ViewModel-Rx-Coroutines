package com.example.githubsampleapplication.model

import com.google.gson.annotations.SerializedName
import com.squareup.picasso.Picasso
import androidx.databinding.BindingAdapter
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.githubsampleapplication.BuiltByTypeConverter
import com.example.githubsampleapplication.R
import de.hdodenhof.circleimageview.CircleImageView

@Entity(tableName = "Repo")
data class RepositoryResponseModel(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id") @ColumnInfo(name = "id") var id:Int = 0,
    @SerializedName("author") @ColumnInfo(name = "author") var author: String?,
    @SerializedName("name") @ColumnInfo(name = "name") var name: String?,
    @SerializedName("avatar") @ColumnInfo(name = "avatar")  var avatar: String?,
    @SerializedName("url") @ColumnInfo(name = "url")  var url: String?,
    @SerializedName("description") @ColumnInfo(name = "description")  var description: String?,
    @SerializedName("language") @ColumnInfo(name = "language")  var language: String?,
    @SerializedName("languageColor") @ColumnInfo(name = "languageColor")  var languageColor: String?,
    @SerializedName("stars") @ColumnInfo(name = "stars")  var stars: Int?,
    @SerializedName("forks") @ColumnInfo(name = "forks")  var forks: Int?,
    @SerializedName("currentPeriodStars") @ColumnInfo(name = "currentPeriodStars")  var currentPeriodStars: Int?,
    @TypeConverters(BuiltByTypeConverter::class)
    @SerializedName("builtBy") @ColumnInfo(name = "builtBy")  var builtBy: List<BuiltByResponseModel>?

)
