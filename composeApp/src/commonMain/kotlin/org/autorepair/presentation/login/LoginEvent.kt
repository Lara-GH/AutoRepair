package org.autorepair.presentation.login

sealed interface LoginEvent {
    data object NavigateToUserHome : LoginEvent
    data object NavigateToMechanicHome: LoginEvent
    data object NavigateToSignUp : LoginEvent
}