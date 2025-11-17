package com.example.myapplication.data.factory

import com.example.myapplication.data.model.Favorite
import javax.inject.Inject

class FavoriteFactory @Inject constructor(){
    fun getFavoriteList(): List<Favorite> {
        return listOf(
            Favorite(
                title = "서울역",
                address = "서울특별시 중구 한강대로 405"
            ),
            Favorite(
                title = "서울대병원",
                address = "서울특별시 종로구 대학로 101"
            ),
            Favorite(
                title = "고려대",
                address = "서울특별시 성북구 안암로 145"
            ),
            Favorite(
                title = "서울시청",
                address = "서울특별시 중구 세종대로 110"
            )
        )
    }
}