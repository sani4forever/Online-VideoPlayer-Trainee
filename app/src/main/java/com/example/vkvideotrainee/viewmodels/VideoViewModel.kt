package com.example.vkvideotrainee.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vkvideotrainee.data.Video
import com.example.vkvideotrainee.data.VideoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// TODO: EXCEPTION HANDLING
// TODO: UNIT TESTS

class VideoViewModel(private val repository: VideoRepository) : ViewModel() {
    private val _videos = MutableStateFlow<List<Video>>(emptyList())
    val videos: StateFlow<List<Video>> = _videos

    init {
         fetchVideos()
    }

    fun fetchVideos() {
        viewModelScope.launch {
            try {
                _videos.value = repository.getVideos()!!

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
