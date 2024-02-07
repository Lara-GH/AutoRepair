package org.autorepair.presentation.signup

sealed interface SignUpEvent {
    data object NavigateToMain : SignUpEvent
    data object NavigateToLogin : SignUpEvent
}