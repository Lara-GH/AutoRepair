package org.autorepair.domain.models.chat


sealed interface ObserveChatEvent {
    data class MessageAdded(
        val message: Message
    ): ObserveChatEvent
}