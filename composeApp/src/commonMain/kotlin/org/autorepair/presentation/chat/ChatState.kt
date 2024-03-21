package org.autorepair.presentation.chat

import org.autorepair.data.models.Message

data class ChatState(
    val isLoading: Boolean,
    var message: String,
    var messages: MutableList<Message>,
    val userId: String
) {
    companion object {
        val Init = ChatState(
            isLoading = false,
            message = "",
            messages = mutableListOf(),
            userId = ""
        )
    }
}