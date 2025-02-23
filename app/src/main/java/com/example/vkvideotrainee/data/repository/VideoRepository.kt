package com.example.vkvideotrainee.data.repository

import com.example.vkvideotrainee.data.api.VideoApi
import com.example.vkvideotrainee.data.db.VideoDao
import com.example.vkvideotrainee.data.db.entities.VideoEntity
import com.example.vkvideotrainee.domain.models.Video

class VideoRepository(val videoDao: VideoDao, private val api: VideoApi) {

    suspend fun getVideos(): List<Video>? {
        val response = api.getVideos()

        if (response.isSuccessful) {
            val videoEntities = response.body()?.map { video ->
                VideoEntity(video.id, video.title, video.thumbnailUrl, video.videoUrl, video.duration)
            }
            videoDao.clearVideos()
            if (videoEntities != null) {
                videoDao.insertVideos(videoEntities)
            }
            if (videoEntities != null) {
                return videoEntities.map { video -> video.toDomain() }
            }
        } else {
            val cachedVideos = videoDao.getAllVideos()
            if (cachedVideos.isNotEmpty()) {
                return cachedVideos.map { videoEntity -> videoEntity.toDomain() }
            }
        }
        return null
    }
}
