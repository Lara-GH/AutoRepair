package org.autorepair.domain.repository

import kotlinx.coroutines.flow.Flow
import org.autorepair.domain.models.UserRole
import org.autorepair.domain.models.chat.ObserveChatEvent

interface ChatRepository {
    suspend fun sendMessage(userChatID: String, messageText: String): Result<Unit>
    suspend fun observeCurrentUserChatEvents(userRole: UserRole, userChatID: String): Flow<ObserveChatEvent>
    suspend fun observeChatsEvents(): Flow<String>

    // getChatMessages(offset: )

    //infinite list compose

}