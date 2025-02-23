package com.example.vkvideotrainee.domain.models

data class Video(
    val id: Int,
    val title: String,
    val thumbnailUrl: String,
    val videoUrl: String,
    val duration: String
)
