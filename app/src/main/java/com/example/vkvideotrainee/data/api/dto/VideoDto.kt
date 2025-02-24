package com.example.vkvideotrainee.data.api.dto

import com.example.vkvideotrainee.data.db.entities.VideoEntity
import com.example.vkvideotrainee.domain.models.Video
import com.google.gson.annotations.SerializedName

data class VideoDto(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("thumbnail_url") val thumbnailUrl: String,
    @SerializedName("url") val videoUrl: String,
    @SerializedName("duration") val duration: String
) {
    fun toDbEntity(): VideoEntity = VideoEntity(id, title, thumbnailUrl, videoUrl, duration)
    fun toDomain(): Video = Video(id, title, thumbnailUrl, videoUrl, duration)
}
