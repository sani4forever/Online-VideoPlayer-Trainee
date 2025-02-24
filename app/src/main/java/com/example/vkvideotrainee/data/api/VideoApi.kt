package com.example.vkvideotrainee.data.api

import com.example.vkvideotrainee.data.api.dto.VideoDto
import retrofit2.Response
import retrofit2.http.GET

interface VideoApi {
    @GET("videos")
    suspend fun getVideos(): Response<List<VideoDto>>
}
