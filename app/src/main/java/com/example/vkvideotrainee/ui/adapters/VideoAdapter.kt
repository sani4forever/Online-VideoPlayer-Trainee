package com.example.vkvideotrainee.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.vkvideotrainee.R
import com.example.vkvideotrainee.domain.models.Video
import com.example.vkvideotrainee.databinding.ItemVideoBinding

class VideoAdapter(
    private val onVideoClick: (String) -> Unit
) : ListAdapter<Video, VideoAdapter.VideoViewHolder>(VideoDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val binding = ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoViewHolder(binding, onVideoClick)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class VideoViewHolder(
        private val binding: ItemVideoBinding,
        onVideoClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private var currentVideoUrl: String? = null

        init {
            itemView.setOnClickListener {
                currentVideoUrl?.let { onVideoClick(it) }
            }
        }

        fun bind(video: Video) {
            currentVideoUrl = video.videoUrl
            binding.videoTitle.text = video.title
            binding.videoDuration.text = video.duration

            Glide.with(itemView)
                .load(video.thumbnailUrl.ifBlank { video.videoUrl })
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.videoThumbnail)
        }
    }

    companion object VideoDiffCallback : DiffUtil.ItemCallback<Video>() {
        override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean {
            return oldItem.videoUrl == newItem.videoUrl
        }

        override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean {
            return oldItem == newItem
        }
    }
}
