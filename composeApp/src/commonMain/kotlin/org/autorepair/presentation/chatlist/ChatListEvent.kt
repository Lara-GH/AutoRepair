package org.autorepair.presentation.chatlist

sealed interface ChatListEvent {
    data object NavigateToChat : ChatListEvent

}