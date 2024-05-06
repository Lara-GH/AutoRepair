package org.autorepair.presentation.splash

sealed interface SplashEvent {
    data object NavigateToUserHome: SplashEvent
    data object NavigateToMechanicHome: SplashEvent
    data object NavigateToManagerHome: SplashEvent
    data object NavigateToLogin: SplashEvent
}