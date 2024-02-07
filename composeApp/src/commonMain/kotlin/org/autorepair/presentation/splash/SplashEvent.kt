package org.autorepair.presentation.splash

sealed interface SplashEvent {
    data object NavigateToHome: SplashEvent
    data object NavigateToLogin: SplashEvent
}