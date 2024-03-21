package org.autorepair.domian.repository

import org.autorepair.data.models.Message

interface ChatRepository {
    suspend fun sendMessage(message: Message): Result<Unit>
    suspend fun newMessageAdded(messageJson: String): Result<Message?>

}