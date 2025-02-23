package com.example.vkvideotrainee.fragments

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.fragment.navArgs
import com.example.vkvideotrainee.databinding.FragmentVideoPlayerBinding
import com.example.vkvideotrainee.viewmodels.VideoPlayerViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class VideoPlayerFragment : Fragment() {

    private var _binding: FragmentVideoPlayerBinding? = null
    private val binding get() = _binding!!
    private var exoPlayer: ExoPlayer? = null
    private val args: VideoPlayerFragmentArgs by navArgs()
    private val viewModel by viewModel<VideoPlayerViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVideoPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val videoUrl = args.url
        viewModel.playbackPosition.observe(viewLifecycleOwner) { position ->
            try {
                initializePlayer(videoUrl, position)
            } catch (e: Exception) {
                showError("Ошибка при запуске видео: ${e.localizedMessage}")
            }
        }
    }

    fun initializePlayer(videoUrl: String, startPosition: Long) {
        try {
            exoPlayer = ExoPlayer.Builder(requireContext()).build().also { player ->
                binding.playerView.player = player
                val mediaItem = MediaItem.fromUri(Uri.parse(videoUrl))
                player.setMediaItem(mediaItem)
                player.prepare()
                player.seekTo(startPosition)
                player.play()
                Log.d("aboba", videoUrl)
            }
        } catch (e: Exception) {
            showError("Не удалось инициализировать ExoPlayer: ${e.localizedMessage}")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        try {
            viewModel.savePlaybackPosition(exoPlayer?.currentPosition ?: 0L)
        } catch (e: Exception) {
            showError("Ошибка при сохранении позиции: ${e.localizedMessage}")
        }
        releasePlayer()
        _binding = null
    }

    private fun releasePlayer() {
        try {
            exoPlayer?.release()
        } catch (e: Exception) {
            showError("Ошибка при освобождении ExoPlayer: ${e.localizedMessage}")
        } finally {
            exoPlayer = null
        }
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
