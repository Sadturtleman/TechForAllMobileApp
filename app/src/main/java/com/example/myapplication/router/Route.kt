package com.example.myapplication.router

import kotlinx.serialization.Serializable

// 목적지 관련
@Serializable object DestinationInputRoute
@Serializable object ManualInputRoute
@Serializable
data class DestinationConfirmRoute(
    val placeName: String,
    val address: String
)

// 택시 호출 관련
@Serializable object TaxiSearchingRoute
@Serializable object TaxiAssignedRoute
@Serializable object TaxiFinishedRoute


@Serializable object VoiceListeningRoute
