package org.autorepair.presentation.splash

sealed interface SplashEvent {
    data object NavigateToMain: SplashEvent
    data object NavigateToLogin: SplashEvent
}