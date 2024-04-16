package org.autorepair.data.repository

import dev.gitlive.firebase.database.ChildEvent
import dev.gitlive.firebase.database.DatabaseReference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.take
import kotlinx.serialization.json.Json
import org.autorepair.data.exceptions.UnathorizedException
import org.autorepair.data.models.chat.FirebaseMessage
import org.autorepair.data.storages.UserCache
import org.autorepair.domain.models.UserRole
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
        val userRole = userCache.getUserRole() ?: return Result.failure(UnathorizedException())

        val currentDateTime = DateTime.getCurrentDateTime()

        val message = FirebaseMessage(
            id = "${currentDateTime}_${userRole}",
            userId = userId,
            currentDateTime = currentDateTime,
            userRole = userRole.name,
            message = messageText,
            isSeen = false
        )

        return try {
            databaseReference.child("chat")
                .child(message.userId)
                .child(message.currentDateTime)
                .setValue(value = message)

            notifyAnotherSide(userId, userRole, messageText)

            Result.success(Unit)
        } catch (t: UnathorizedException) {
            Result.failure(t)
        }
    }

    private suspend fun notifyAnotherSide(userId: String, role: UserRole, message: String) {
        if(role == UserRole.USER) {
            val managerToken = getManagerToken() ?: return
            println("manager token = $managerToken")
            sendNotificationToToken(managerToken, message)
        } else {
            //TODO
        }
    }

    private suspend fun getManagerToken(): String? {
        return databaseReference.child("tokens")
            .child("manager")
            .childEvents()
            .first()
            .let {
                it.snapshot.value?.toString()
            }
    }

    private suspend fun sendNotificationToToken(token: String, message: String) {

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

    private suspend fun FirebaseMessage.mapToDomain(): Message {

        val messageUserRole = UserRole.values().find { it.name == userRole }

        return Message(
            id = id.let {
                it.ifEmpty { "${currentDateTime}_$userId" }
            },
            userId = userId,
            currentDateTime = DateTime.getFormattedDateTime(currentDateTime),
            userRole = userRole,
            message = message,
            isSeen = isSeen,
            isMine = messageUserRole == userCache.getUserRole(),
            authorName = getAuthorName(userCache.getUserRole(), messageUserRole)
        )
    }

    private fun getAuthorName(myRole: UserRole?, messageRole: UserRole?): String {
        return when {
            myRole == UserRole.USER && messageRole != UserRole.USER -> "BodyShop"
            myRole == UserRole.MECHANIC && messageRole != UserRole.MECHANIC -> "Manager"
            else -> "Text for manager"
            //TODO
            //            myRole == UserRole.MANAGER && messageRole != UserRole.MECHANIC -> "Manager"
        }
    }

    override suspend fun observeCurrentUserChatEvents(): Flow<ObserveChatEvent> {
        val userId = userCache.getUserId() ?: return flowOf()
        return databaseReference.child("chat")
            .child(userId)
//            .limitToLast(3)
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

