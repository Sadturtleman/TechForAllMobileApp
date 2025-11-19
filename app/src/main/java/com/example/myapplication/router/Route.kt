package com.example.myapplication.router

import kotlinx.serialization.Serializable



// 택시 호출 관련
@Serializable object TaxiSearchingRoute
@Serializable object TaxiAssignedRoute
@Serializable object TaxiFinishedRoute


@Serializable object VoiceListeningRoute

@Serializable object HomeRoute
@Serializable object FavoriteRoute
@Serializable object UseDetailRoute



@Serializable
object SearchGraphRoute

@Serializable
object DestinationInputRoute

@Serializable
object ManualInputRoute

@Serializable
data class DestinationListRoute(val query: String)

@Serializable
data class DestinationConfirmRoute(
    val placeName: String,
)
