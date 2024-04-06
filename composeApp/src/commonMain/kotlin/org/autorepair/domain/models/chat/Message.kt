package org.autorepair.domain.models.chat

data class Message(
    val id: String,
    val userId: String,
    val currentDateTime: String,
    val userRole: String,
    val message: String,
    val isSeen: Boolean,
    val isMine: Boolean
)