package org.autorepair.data.repository

import dev.gitlive.firebase.database.ChildEvent
import dev.gitlive.firebase.database.DatabaseReference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.serialization.json.Json
import org.autorepair.data.exceptions.UnathorizedException
import org.autorepair.data.models.chat.FirebaseMessage
import org.autorepair.data.storages.UserCache
import org.autorepair.domain.models.chat.Message
import org.autorepair.domain.models.chat.ObserveChatEvent
import org.autorepair.domain.repository.ChatRepository
import org.autorepair.ui.datetime.DateTime

class ChatRepositoryImpl(
    private val databaseReference: DatabaseReference,
    private val json: Json,
    private val userCache: UserCache
) : ChatRepository {
    override suspend fun sendMessage(
        messageText: String
    ): Result<Unit> {

        val userId = userCache.getUserId() ?: return Result.failure(UnathorizedException())

        val currentDateTime = DateTime.getFormattedDate()

        val message = FirebaseMessage(
            id = "${currentDateTime}_user", //TODO get user role from user cache
            userId = userId,
            currentDateTime = currentDateTime,
            userRole = "user",
            message = messageText,
            isSeen = false
        )

        return try {

            throw UnathorizedException()

            databaseReference.child("chat")
                .child(message.userId)
                .child(message.currentDateTime)
                .setValue(value = message)
            Result.success(Unit)
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }

    private fun createMessage(messageJson: String): FirebaseMessage {
        print(messageJson)
        val messageProps = messageJson
            .trim() // Удаляем начальные и конечные пробелы
            .removeSurrounding("{", "}") // Удаляем начальную и конечную фигурные скобки
            .split(",") // Разделяем строку на пары "ключ=значение"
            .map { it.split("=") } // Разделяем каждую пару на ключ и значение
            .associate { it.first().trim() to it.getOrNull(1) }
        return FirebaseMessage(
            id = messageProps["id"] ?: "",
            userId = messageProps["userId"]!!,
            currentDateTime = messageProps["currentDateTime"]!!,
            userRole = messageProps["userRole"]!!,
            message = messageProps["message"]!!,
            isSeen = false
        )
    }

    private fun FirebaseMessage.mapToDomain(): Message {
        return Message(
            id = id.let {
                it.ifEmpty { "${currentDateTime}_$userId" }
            },
            userId = userId,
            currentDateTime = currentDateTime,
            userRole = userRole,
            message = message,
            isSeen = isSeen
        )
    }

    override suspend fun observeCurrentUserChatEvents(): Flow<ObserveChatEvent> {
        val userId = userCache.getUserId() ?: return flowOf()
        return databaseReference.child("chat")
            .child(userId)
            .limitToLast(3)
            .childEvents()

            .mapNotNull { event ->
                when (event.type) {
                    ChildEvent.Type.ADDED -> {
                        val message = createMessage(event.snapshot.value.toString())
                        ObserveChatEvent.MessageAdded(message.mapToDomain())
                    }

                    ChildEvent.Type.CHANGED -> null
                    ChildEvent.Type.MOVED -> null
                    ChildEvent.Type.REMOVED -> null
                }
            }
    }
}

