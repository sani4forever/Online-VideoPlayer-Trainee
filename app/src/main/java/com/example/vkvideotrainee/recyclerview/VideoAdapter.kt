package com.example.vkvideotrainee.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vkvideotrainee.data.Video
import com.example.vkvideotrainee.databinding.ItemVideoBinding

class VideoAdapter(
    private val onVideoClick: (String) -> Unit
) : ListAdapter<Video, VideoAdapter.VideoViewHolder>(VideoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val binding = ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoViewHolder(binding, onVideoClick)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class VideoViewHolder(
        private val binding: ItemVideoBinding,
        private val onVideoClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(video: Video) {
            binding.videoTitle.text = video.title
            binding.videoDuration.text = video.duration

            val thumbnailUrl = video.thumbnailUrl.ifBlank { video.videoUrl }
            Glide.with(binding.root)
                .load(thumbnailUrl)
                .into(binding.videoThumbnail)

            binding.root.setOnClickListener {
                onVideoClick(video.videoUrl)
            }
        }
    }
}

class VideoDiffCallback : DiffUtil.ItemCallback<Video>() {
    override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean {
        return oldItem.videoUrl == newItem.videoUrl
    }

    override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean {
        return oldItem == newItem
    }
}
