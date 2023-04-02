package com.example.tvshowsappmvvm.data

import androidx.room.TypeConverter
import com.example.tvshowsappmvvm.fromJson
import com.google.gson.Gson

class Converters {
    @TypeConverter
    fun fromStringArrayList(value: List<String>): String {

        return Gson().toJson(value)
    }

    @TypeConverter
    fun toStringArrayList(value: String): List<String> {
        return try {
            Gson().fromJson<List<String>>(value) //using extension function
        } catch (e: Exception) {
            arrayListOf()
        }
    }
}