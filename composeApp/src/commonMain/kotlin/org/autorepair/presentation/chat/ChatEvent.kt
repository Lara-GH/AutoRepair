package org.autorepair.presentation.chat

sealed interface ChatEvent {
    data class ShowSnackBar(val text: String): ChatEvent
    data object NavigateToLogin: ChatEvent
}