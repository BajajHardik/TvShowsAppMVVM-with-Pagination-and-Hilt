package com.example.tvshowsappmvvm.api

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TvShowX(
    val country: String,
    val description: String,
    val image_path: String,
    val name: String,
    val network: String,
    val pictures: List<String>,
    val start_date: String,
    val status: String,
    val id: Int=0,
    val url: String
):Parcelable