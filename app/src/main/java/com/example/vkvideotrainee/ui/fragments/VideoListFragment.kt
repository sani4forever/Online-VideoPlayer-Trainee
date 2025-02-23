package com.example.vkvideotrainee.ui.fragments

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
import com.example.vkvideotrainee.ui.adapters.VideoAdapter
import com.example.vkvideotrainee.viewmodels.VideoViewModel
import kotlinx.coroutines.flow.collectLatest
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

        setupRecyclerView()
        setupSwipeToRefresh()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        val videoAdapter = VideoAdapter { videoUrl ->
            val action = VideoListFragmentDirections
                .actionVideoListFragmentToVideoPlayerFragment(videoUrl)
            findNavController().navigate(action)
        }

        binding.videoRecyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = videoAdapter
        }
    }

    private fun setupSwipeToRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.fetchVideos()
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.videos.collectLatest { videos ->
                        (binding.videoRecyclerView.adapter as? VideoAdapter)?.submitList(videos)
                        binding.videoRecyclerView.visibility = if (videos.isNotEmpty()) View.VISIBLE else View.GONE
                    }
                }
                launch {
                    viewModel.isLoading.collectLatest { isLoading ->
                        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
                        binding.swipeRefreshLayout.isRefreshing = isLoading
                    }
                }
                launch {
                    viewModel.errorMessage.collectLatest { errorMessage ->
                        binding.errorTextView.apply {
                            text = errorMessage
                            visibility = if (errorMessage != null) View.VISIBLE else View.GONE
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
