package com.example.vkvideotrainee.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.vkvideotrainee.domain.models.Video

@Entity(tableName = "videos")
data class VideoEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val thumbnailUrl: String,
    val videoUrl: String,
    val duration: String
) {
    fun toDomain(): Video = Video(id, title, thumbnailUrl, videoUrl, duration)
}
