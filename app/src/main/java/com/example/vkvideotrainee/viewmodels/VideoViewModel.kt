package com.example.vkvideotrainee.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vkvideotrainee.domain.models.Video
import com.example.vkvideotrainee.data.repository.VideoRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class VideoViewModel(private val repository: VideoRepository) : ViewModel() {

    private val _videos = MutableStateFlow<List<Video>>(emptyList())
    val videos: StateFlow<List<Video>> = _videos

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    init {
        fetchVideos()
    }

    fun fetchVideos() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val result = repository.getVideos()
                if (result != null) {
                    _videos.value = result
                } else {
                    throw Exception("Сервер вернул пустой ответ")
                }
            } catch (e: Exception) {
                val cachedVideos = repository.videoDao.getAllVideos().map { videoEntity -> videoEntity.toDomain() }
                if (cachedVideos.isNotEmpty()) {
                    _videos.value = cachedVideos
                    showErrorMessage("Нет доступа к pashok11.tw1.su. Список видео загружен из кеша.")
                } else {
                    showErrorMessage("Ошибка: ${e.message}. Нет доступа к серверу и кеш пуст.")
                }
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun showErrorMessage(message: String) {
        viewModelScope.launch {
            _errorMessage.value = message
            delay(3000)
            _errorMessage.value = null
        }
    }
}
