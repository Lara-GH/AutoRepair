package org.autorepair.presentation.splash

data class SplashState(
    val isLoading: Boolean
) {
    companion object {
        val Init = SplashState(isLoading = false)
    }
}