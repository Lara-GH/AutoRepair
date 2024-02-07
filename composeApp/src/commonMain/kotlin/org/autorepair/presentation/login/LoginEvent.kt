package org.autorepair.presentation.login

sealed interface LoginEvent {
    data object NavigateToMain : LoginEvent
    data object NavigateToSignUp : LoginEvent
}