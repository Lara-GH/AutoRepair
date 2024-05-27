package org.autorepair.presentation.login

sealed interface LoginEvent {
    data object NavigateToUserHome : LoginEvent
    data object NavigateToMechanicHome: LoginEvent
    data object NavigateToManagerHome: LoginEvent
    data object NavigateToSignUp : LoginEvent
    data class ShowSnackBar(val text: String): LoginEvent
}