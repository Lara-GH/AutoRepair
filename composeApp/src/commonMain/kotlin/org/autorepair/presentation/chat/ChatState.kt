package org.autorepair.presentation.chat

import org.autorepair.domain.models.chat.Message

data class ChatState(
    val isLoading: Boolean,
    val message: String,
    val messages: List<Message>,
    val myUserRole: String
) {
    companion object {
        val Init = ChatState(
            isLoading = false,
            message = "",
            messages = mutableListOf(),
            myUserRole = ""
        )
    }
}