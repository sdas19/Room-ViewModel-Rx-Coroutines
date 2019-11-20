package com.example.githubsampleapplication

import androidx.room.TypeConverter
import com.example.githubsampleapplication.model.BuiltByResponseModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class BuiltByTypeConverter {

    @TypeConverter
    fun toBuiltBy(json: String): List<BuiltByResponseModel> {
        val type = object : TypeToken<List<BuiltByResponseModel>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toJson(torrent: List<BuiltByResponseModel>): String {
        val type = object: TypeToken<List<BuiltByResponseModel>>() {}.type
        return Gson().toJson(torrent, type)
    }

}