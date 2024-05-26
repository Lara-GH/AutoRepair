package org.autorepair.presentation.signup

sealed interface SignUpEvent {
    data object NavigateToUserHome : SignUpEvent
    data object NavigateToMechanicHome : SignUpEvent
    data object NavigateToManagerHome : SignUpEvent
    data object NavigateToLogin : SignUpEvent
    data class ShowSnackbar(val text: String): SignUpEvent
}