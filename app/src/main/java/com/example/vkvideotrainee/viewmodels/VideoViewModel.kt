package com.example.vkvideotrainee.viewmodels
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vkvideotrainee.domain.models.Video
import com.example.vkvideotrainee.data.repository.VideoRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class VideoViewModel(private val repository: VideoRepository) : ViewModel() {

    private val _videos = MutableStateFlow<List<Video>>(emptyList())
    val videos: StateFlow<List<Video>> = _videos

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage: SharedFlow<String> = _toastMessage

    init {
        fetchVideos()
    }

    fun fetchVideos() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = repository.getVideos()
                if (result != null) {
                    _videos.value = result
                } else {
                    throw Exception("Сервер вернул пустой ответ")
                }
            } catch (e: Exception) {
                val cachedVideos = repository.videoDao.getAllVideos()?.map { it.toDomain() }
                if (!cachedVideos.isNullOrEmpty()) {
                    _videos.value = cachedVideos
                    showToast("Нет доступа к серверу. Список видео загружен из кеша.")
                } else {
                    showToast("Ошибка: ${e.message}. Нет доступа к серверу и кеш пуст.")
                }
                Log.e("com.example.vkvideotrainee.viewmodels.VideoViewModel", "Ошибка загрузки видео", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun showToast(message: String) {
        viewModelScope.launch {
            _toastMessage.emit(message)
        }
    }
}
