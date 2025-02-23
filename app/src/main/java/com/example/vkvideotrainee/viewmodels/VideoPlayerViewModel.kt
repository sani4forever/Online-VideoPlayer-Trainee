package com.example.vkvideotrainee.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class VideoPlayerViewModel : ViewModel() {

    private val _playbackPosition = MutableStateFlow(0L)
    val playbackPosition: StateFlow<Long> get() = _playbackPosition

    fun savePlaybackPosition(position: Long) {
        _playbackPosition.value = position
    }
}
