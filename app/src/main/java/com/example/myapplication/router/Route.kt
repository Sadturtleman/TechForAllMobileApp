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
data class KaKaoRoute(val query: String)
@Serializable object SplashRoute
@Serializable object OnboardingRoute
@Serializable object UserInfoRoute

@Serializable object HelpRequestRoute

@Serializable object HelpVoiceRoute

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
