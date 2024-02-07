package org.autorepair.presentation.login

sealed interface LoginEvent {
    data object NavigateToHome : LoginEvent
    data object NavigateToSignUp : LoginEvent
}