package com.example.vkvideotrainee

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.vkvideotrainee.viewmodels.VideoPlayerViewModel
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class VideoPlayerViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule() // Для работы с LiveData

    private val viewModel = VideoPlayerViewModel()

    @Test
    fun `savePlaybackPosition updates playbackPosition`() {
        viewModel.savePlaybackPosition(5000L)
        assertEquals(5000L, viewModel.playbackPosition.value)
    }
}
