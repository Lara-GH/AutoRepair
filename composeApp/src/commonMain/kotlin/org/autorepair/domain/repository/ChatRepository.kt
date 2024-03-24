package org.autorepair.domain.repository

import kotlinx.coroutines.flow.Flow
import org.autorepair.data.models.chat.FirebaseMessage
import org.autorepair.domain.models.chat.Message
import org.autorepair.domain.models.chat.ObserveChatEvent

interface ChatRepository {
    suspend fun sendMessage(messageText: String): Result<Unit>
    suspend fun observeCurrentUserChatEvents(): Flow<ObserveChatEvent>

    // getChatMessages(offset: )

    //infinite list compose

}