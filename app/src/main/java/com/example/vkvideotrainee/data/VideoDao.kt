package com.example.vkvideotrainee.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface VideoDao {
    @Query("SELECT * FROM videos")
    suspend fun getAllVideos(): List<Video>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideos(videos: List<Video>)

    @Query("DELETE FROM videos")
    suspend fun clearVideos()
}