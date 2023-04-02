package com.example.tvshowsappmvvm.api

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@kotlinx.parcelize.Parcelize
@Entity(tableName = "popular_tv_shows")
data class TvShow (
    @SerializedName("country")
    val country: String,
    @SerializedName("id")
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @SerializedName("image_thumbnail_path")
    val image_thumbnail_path: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("network")
    val network: String,
    @SerializedName("start_date")
    val start_date: String,
    @SerializedName("status")
    val status: String
):java.io.Serializable, Parcelable