package com.example.myapplication.data.di

import com.example.myapplication.data.api.KakaoLocalApi
import com.example.myapplication.data.api.RetrofitClient
import com.example.myapplication.data.repository.SearchRepository
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.Provides
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SearchModule {

    @Provides
    @Singleton
    fun provideKakaoLocalApi(): KakaoLocalApi = RetrofitClient.api

    @Provides
    @Singleton
    fun provideSearchRepository(api: KakaoLocalApi): SearchRepository =
        SearchRepository(api)
}
