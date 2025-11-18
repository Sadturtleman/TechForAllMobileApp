package com.example.myapplication.data.repository

import android.util.Log
import com.example.myapplication.data.api.KakaoLocalApi
import com.example.myapplication.data.model.SearchResult
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val api: KakaoLocalApi
) {

    suspend fun search(query: String): List<SearchResult> {
        val res = api.searchKeyword(query)
        Log.d("KAKAO_DEBUG", "Query = $query")
        Log.d("KAKAO_DEBUG", "Results = ${res.documents.size}")
        res.documents.forEach {
            Log.d("KAKAO_DEBUG", "Place = ${it.placeName}, address = ${it.addressName}")
        }
        return res.documents.map { doc ->

            val placeName = doc.placeName ?: "(이름 없음)"
            val address =
                doc.roadAddressName
                    ?: doc.addressName
                    ?: "(주소 없음)"

            val latitude = doc.y?.toDoubleOrNull() ?: 0.0
            val longitude = doc.x?.toDoubleOrNull() ?: 0.0


            SearchResult(
                placeName = placeName,
                address = address,
                latitude = latitude,
                longitude = longitude
            )
        }
    }
}
