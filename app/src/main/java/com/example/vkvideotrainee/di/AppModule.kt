package com.example.vkvideotrainee.di

import com.example.vkvideotrainee.data.VideoDatabase
import com.example.vkvideotrainee.data.VideoRepository
import com.example.vkvideotrainee.data.VideoApi
import com.example.vkvideotrainee.viewmodels.VideoPlayerViewModel
import com.example.vkvideotrainee.viewmodels.VideoViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    // Retrofit
    single {
        Retrofit.Builder()
            .baseUrl("https://pashok11.tw1.su/apis/vk_trainee/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // API
    single<VideoApi> { get<Retrofit>().create(VideoApi::class.java) }

    // Room Database
    single { VideoDatabase.getDatabase(get()).videoDao() }

    // Репозиторий
    single { VideoRepository(get(), get()) }

    // ViewModel
    viewModel { VideoViewModel(get()) }

    viewModel {VideoPlayerViewModel()}
}
