package org.autorepair.presentation.chat

sealed interface ChatEvent {
    data class ShowSnackbar(val text: String): ChatEvent
    data object NavigateToLogin: ChatEvent
}