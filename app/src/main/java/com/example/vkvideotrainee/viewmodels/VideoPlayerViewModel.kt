package com.example.vkvideotrainee.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class VideoPlayerViewModel : ViewModel() {
    private val _playbackPosition = MutableLiveData(0L)
    val playbackPosition: LiveData<Long> get() = _playbackPosition

    fun savePlaybackPosition(position: Long) {
        _playbackPosition.value = position
    }
}
