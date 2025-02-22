package com.example.vkvideotrainee.fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
            initializePlayer(videoUrl, position)
        }
    }

    private fun initializePlayer(videoUrl: String, startPosition: Long) {
        exoPlayer = ExoPlayer.Builder(requireContext()).build()
        binding.playerView.player = exoPlayer

        val mediaItem = MediaItem.fromUri(Uri.parse(videoUrl))
        exoPlayer?.setMediaItem(mediaItem)
        exoPlayer?.prepare()
        exoPlayer?.seekTo(startPosition)
        exoPlayer?.play()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.savePlaybackPosition(exoPlayer?.currentPosition ?: 0L)
        releasePlayer()
        _binding = null
    }

    private fun releasePlayer() {
        exoPlayer?.release()
        exoPlayer = null
    }
}
