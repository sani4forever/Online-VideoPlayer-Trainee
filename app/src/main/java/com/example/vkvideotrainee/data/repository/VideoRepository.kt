package com.example.vkvideotrainee.data.repository

import com.example.vkvideotrainee.data.api.VideoApi
import com.example.vkvideotrainee.data.db.VideoDao
import com.example.vkvideotrainee.domain.models.Video

class VideoRepository(val videoDao: VideoDao, private val api: VideoApi) {

    suspend fun getVideos(): List<Video>? {
        val response = api.getVideos()

        return if (response.isSuccessful) {
            val videoEntities = response.body()?.map { it.toDbEntity() }
            videoDao.clearVideos()
            if (videoEntities != null) {
                videoDao.insertVideos(videoEntities)
            }
            response.body()?.map { it.toDomain() }
        } else {
            videoDao.getAllVideos()?.map { videoEntity -> videoEntity.toDomain() }
        }
    }
}
