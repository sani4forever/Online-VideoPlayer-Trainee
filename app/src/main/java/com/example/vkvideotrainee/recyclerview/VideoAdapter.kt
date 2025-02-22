package com.example.vkvideotrainee.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vkvideotrainee.R
import com.example.vkvideotrainee.data.Video

class VideoAdapter(
    private val onVideoClick: (String) -> Unit
) : ListAdapter<Video, VideoAdapter.VideoViewHolder>(VideoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_video, parent, false)
        return VideoViewHolder(view, onVideoClick)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class VideoViewHolder(itemView: View, private val onVideoClick: (String) -> Unit) :
        RecyclerView.ViewHolder(itemView) {

        private val videoThumbnail: ImageView = itemView.findViewById(R.id.videoThumbnail)
        private val videoTitle: TextView = itemView.findViewById(R.id.videoTitle)
        private val videoDuration: TextView = itemView.findViewById(R.id.videoDuration)

        fun bind(video: Video) {
            videoTitle.text = video.title
            videoDuration.text = video.duration
            if (video.thumbnailUrl.isNotBlank()) {
                Glide.with(itemView)
                    .load(video.thumbnailUrl)
                    .into(videoThumbnail)
            } else {
                Glide.with(itemView)
                    .load(video.videoUrl)
                    .frame(1000000)
                    .into(videoThumbnail)
            }

            itemView.setOnClickListener {
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
