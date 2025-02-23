package com.example.vkvideotrainee.data

class VideoRepository(val videoDao: VideoDao, private val api: VideoApi) {

    suspend fun getVideos(): List<Video>? {
        val response = api.getVideos()

        if (response.isSuccessful) {
            val videoEntities = response.body()?.map { video ->
                Video(video.id, video.title, video.thumbnailUrl, video.videoUrl, video.duration)
            }
            videoDao.clearVideos()
            if (videoEntities != null) {
                videoDao.insertVideos(videoEntities)
            }
            return videoEntities
        } else {
            val cachedVideos = videoDao.getAllVideos()
            if (cachedVideos.isNotEmpty()) {
                return cachedVideos
            }
        }
        return null
    }
}
