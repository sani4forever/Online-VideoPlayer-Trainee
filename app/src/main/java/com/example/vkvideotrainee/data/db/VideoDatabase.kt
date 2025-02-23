package com.example.vkvideotrainee.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.vkvideotrainee.data.db.entities.VideoEntity

@Database(entities = [VideoEntity::class], version = 2, exportSchema = false)
abstract class VideoDatabase : RoomDatabase() {
    abstract fun videoDao(): VideoDao

    companion object {
        @Volatile
        private var INSTANCE: VideoDatabase? = null

        fun getDatabase(context: Context): VideoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VideoDatabase::class.java,
                    "video_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
