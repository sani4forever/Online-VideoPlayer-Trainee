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
    fun toDomain(): Video {
        return Video(id, title, thumbnailUrl, videoUrl, duration)
    }

    companion object {
        fun fromDomain(video: Video): VideoEntity {
            return VideoEntity(video.id, video.title, video.thumbnailUrl, video.videoUrl, video.duration)
        }
    }
}
