package org.autorepair.presentation.signup

sealed interface SignUpEvent {
    data object NavigateToHome : SignUpEvent
    data object NavigateToLogin : SignUpEvent
}