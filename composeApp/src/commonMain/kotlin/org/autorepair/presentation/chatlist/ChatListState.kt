package org.autorepair.presentation.chatlist

data class ChatListState(
    val isLoading: Boolean,
    val chats: List<String>,
    val chatID: String
) {
    companion object {
        val Init = ChatListState(
            isLoading = false,
            chats = mutableListOf(),
            chatID = ""
        )
    }
}