package com.example.vkvideotrainee.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.vkvideotrainee.databinding.FragmentVideoListBinding
import com.example.vkvideotrainee.recyclerview.VideoAdapter
import com.example.vkvideotrainee.viewmodels.VideoViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class VideoListFragment : Fragment() {

    private var _binding: FragmentVideoListBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<VideoViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVideoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()
        val videoAdapter = VideoAdapter { videoUrl ->
            val action = VideoListFragmentDirections
                .actionVideoListFragmentToVideoPlayerFragment(videoUrl)
            navController.navigate(action)
        }



        binding.videoRecyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = videoAdapter
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            refreshVideoList()
            binding.swipeRefreshLayout.isRefreshing = false
        }



        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.videos.collect { videos ->
                    videoAdapter.submitList(videos)
                }
            }
        }

    }
    private fun refreshVideoList() {
        viewModel.fetchVideos()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
