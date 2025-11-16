package com.example.myapplication.data.model

import kotlinx.datetime.LocalDate

data class PurchaseTaxi(
    val arriveDate: LocalDate,
    val title: String,
    val detailTitle: String,
    val money: Int
)
