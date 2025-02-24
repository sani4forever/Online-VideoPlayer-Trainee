package com.example.vkvideotrainee.ui.fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.fragment.navArgs
import com.example.vkvideotrainee.R
import com.example.vkvideotrainee.databinding.FragmentVideoPlayerBinding
import com.example.vkvideotrainee.viewmodels.VideoPlayerViewModel
import kotlinx.coroutines.launch
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

        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.playbackPosition.collect { position ->
                    initializePlayer(videoUrl, position)
                }
            }
        }
    }

    fun initializePlayer(videoUrl: String, startPosition: Long) {
        try {
            exoPlayer = ExoPlayer.Builder(requireContext()).build().also { player ->
                binding.playerView.player = player
                val mediaItem = MediaItem.fromUri(Uri.parse(videoUrl))
                with(player) {
                    setMediaItem(mediaItem)
                    prepare()
                    seekTo(startPosition)
                    play()
                }
            }
        } catch (e: Exception) {
            showError(getString(R.string.error, e.localizedMessage))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.savePlaybackPosition(exoPlayer?.currentPosition ?: 0L)
        releasePlayer()
        _binding = null
    }

    private fun releasePlayer() {
        try {
            exoPlayer?.release()
        } catch (e: Exception) {
            showError(getString(R.string.exoplayer_release_error, e.localizedMessage))
        } finally {
            exoPlayer = null
        }
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
