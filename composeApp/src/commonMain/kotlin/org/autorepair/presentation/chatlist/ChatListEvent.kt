package org.autorepair.presentation.chatlist

sealed interface ChatListEvent {
    data class NavigateToChat(val userId: String) : ChatListEvent

}