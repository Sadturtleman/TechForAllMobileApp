package com.example.myapplication.data.factory

import com.example.myapplication.data.model.Favorite
import javax.inject.Inject

class FavoriteFactory @Inject constructor(){
    fun getFavoriteList(): List<Favorite> {
        return listOf(
            Favorite(
                title = "집",
                detailTitle = "강동구 집"
            ),
            Favorite(
                title = "서울 병원",
                detailTitle = "강동구 집"
            ),
            Favorite(
                title = "마트",
                detailTitle = "강동구 집"
            ),
            Favorite(
                title = "서울 아파트",
                detailTitle = "강동구 집"
            )
        )
    }
}