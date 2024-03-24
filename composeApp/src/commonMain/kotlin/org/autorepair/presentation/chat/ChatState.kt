package org.autorepair.presentation.chat

import org.autorepair.data.models.chat.FirebaseMessage
import org.autorepair.domain.models.chat.Message

data class ChatState(
    val isLoading: Boolean,
    val message: String,
    val messages: List<Message>,
) {
    companion object {
        val Init = ChatState(
            isLoading = false,
            message = "",
            messages = mutableListOf(),
        )
    }
}