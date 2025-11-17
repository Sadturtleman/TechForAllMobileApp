package com.example.myapplication.router

import com.example.myapplication.data.model.SearchResult
import kotlinx.serialization.Serializable

// 목적지 관련
@Serializable
object DestinationInputRoute
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

@Serializable object HomeRoute
@Serializable object FavoriteRoute
@Serializable object UseDetailRoute

@kotlinx.serialization.Serializable
data class DestinationListRoute(
    val query: String,
    val results: List<SearchResult>
)
