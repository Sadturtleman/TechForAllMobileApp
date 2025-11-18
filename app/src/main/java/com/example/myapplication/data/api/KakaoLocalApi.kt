package com.example.myapplication.data.api

import com.example.myapplication.data.response.KakaoLocalResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface KakaoLocalApi {

    @GET("v2/local/search/keyword.json")
    suspend fun searchKeyword(
        @Query("query") query: String
    ): KakaoLocalResponse
}
