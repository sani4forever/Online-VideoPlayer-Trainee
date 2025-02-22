package com.example.vkvideotrainee.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "videos")
data class Video(
    @SerializedName("id")
    @PrimaryKey val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("thumbnail_url")
    val thumbnailUrl: String,
    @SerializedName("url")
    val videoUrl: String,
    @SerializedName("duration")
    val duration : String
)