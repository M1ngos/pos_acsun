package com.acsun.pos.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface AuthNavigation {

    @Serializable
    data object Splash : AuthNavigation

    @Serializable
    data object Login : AuthNavigation

    @Serializable
    data object Register : AuthNavigation
}