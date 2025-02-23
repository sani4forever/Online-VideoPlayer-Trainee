package com.example.vkvideotrainee.di

import com.example.vkvideotrainee.data.db.VideoDatabase
import com.example.vkvideotrainee.data.repository.VideoRepository
import com.example.vkvideotrainee.data.api.VideoApi
import com.example.vkvideotrainee.viewmodels.VideoPlayerViewModel
import com.example.vkvideotrainee.viewmodels.VideoViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://pashok11.tw1.su/apis/vk_trainee/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<VideoApi> { get<Retrofit>().create(VideoApi::class.java) }

    single { VideoDatabase.getDatabase(get()).videoDao() }

    single { VideoRepository(get(), get()) }

    viewModel { VideoViewModel(get()) }

    viewModel {VideoPlayerViewModel()}
}
