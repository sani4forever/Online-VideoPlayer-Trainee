package com.example.vkvideotrainee.data

import retrofit2.Response
import retrofit2.http.GET

interface VideoApi {
     @GET("videos")
     suspend fun getVideos(): Response<List<Video>>
}
