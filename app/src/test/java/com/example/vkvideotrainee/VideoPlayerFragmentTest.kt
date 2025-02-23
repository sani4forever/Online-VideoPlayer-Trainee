package com.example.vkvideotrainee

import android.os.Looper
import android.text.TextUtils
import androidx.media3.exoplayer.ExoPlayer
import com.example.vkvideotrainee.ui.fragments.VideoPlayerFragment
import io.mockk.*
import org.junit.Before
import org.junit.Test

class VideoPlayerFragmentTest {

    private lateinit var fragment: VideoPlayerFragment
    private val mockExoPlayer: ExoPlayer = mockk(relaxed = true)

    @Before
    fun setUp() {
        fragment = spyk(VideoPlayerFragment())
        every { fragment.requireContext() } returns mockk(relaxed = true)

        mockkStatic(TextUtils::class)
        every { TextUtils.isEmpty(any()) } returns mockk(relaxed = false)

        mockkStatic(Looper::class)
        every { Looper.myLooper() } returns mockk(relaxed = true)
    }

    @Test
    fun `initializePlayer does not crash on exception`() {
        every { ExoPlayer.Builder(any()) } throws RuntimeException("Ошибка инициализации плеера")

        try {
            fragment.initializePlayer("http://pashok11.tw1.su:7788/videos/Rick%20Astley%20-%20Never%20Gonna%20Give%20You%20Up%20%28Official%20Music%20Video%29%20%5BdQw4w9WgXcQ%5D.mp4", 0L)
        } catch (e: Exception) {
            assert(false) { "Метод не должен падать с ошибкой!" }
        }
    }

    @Test
    fun `initializePlayer initializes ExoPlayer correctly`() {
        every { ExoPlayer.Builder(any()).build() } returns mockExoPlayer

        fragment.initializePlayer("http://pashok11.tw1.su:7788/videos/Rick%20Astley%20-%20Never%20Gonna%20Give%20You%20Up%20%28Official%20Music%20Video%29%20%5BdQw4w9WgXcQ%5D.mp4", 1000L)

        verify { mockExoPlayer.setMediaItem(any()) }
        verify { mockExoPlayer.prepare() }
        verify { mockExoPlayer.seekTo(1000L) }
    }
}
