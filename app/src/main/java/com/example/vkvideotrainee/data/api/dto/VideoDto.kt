package com.example.vkvideotrainee.data.api.dto

import com.google.gson.annotations.SerializedName
import com.example.vkvideotrainee.domain.models.Video

data class VideoDto(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("thumbnail_url") val thumbnailUrl: String,
    @SerializedName("url") val videoUrl: String,
    @SerializedName("duration") val duration: String
) {
    fun toDomain(): Video {
        return Video(id, title, thumbnailUrl, videoUrl, duration)
    }
}
