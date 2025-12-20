package com.example.myapplication.data.factory

import com.example.myapplication.data.model.PurchaseTaxi
import kotlinx.datetime.LocalDate
import javax.inject.Inject


class PurchaseTaxiFactory @Inject constructor(){
    fun getPurchaseTaxiList(): List<PurchaseTaxi> {
        return listOf(
            PurchaseTaxi(
                title = "서울역",
                arriveDate = LocalDate(2025, 10, 11),
                detailTitle = "서울 중구",
                money = 30100
            ),
            PurchaseTaxi(
                title = "건국대학교",
                arriveDate = LocalDate(2025, 8, 13),
                detailTitle = "서울 광진구",
                money = 27800
            ),
            PurchaseTaxi(
                title = "숭례문",
                arriveDate = LocalDate(2025, 7, 24),
                detailTitle = "서울 중구",
                money = 14000
            )
        )
    }
}