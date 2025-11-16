package com.example.myapplication.data.factory

import com.example.myapplication.data.model.PurchaseTaxi
import kotlinx.datetime.LocalDate
import javax.inject.Inject


class PurchaseTaxiFactory @Inject constructor(){
    fun getPurchaseTaxiList(): List<PurchaseTaxi> {
        return listOf(
            PurchaseTaxi(
                title = "서울 아파트",
                arriveDate = LocalDate(2025, 10, 11),
                detailTitle = "서울 강동구 아파트",
                money = 30100
            ),
            PurchaseTaxi(
                title = "서울 아파트",
                arriveDate = LocalDate(2025, 10, 11),
                detailTitle = "서울 강동구 아파트",
                money = 30100
            ),
            PurchaseTaxi(
                title = "서울 아파트",
                arriveDate = LocalDate(2025, 10, 11),
                detailTitle = "서울 강동구 아파트",
                money = 30100
            )
        )
    }
}