package org.autorepair.data.repository

import dev.gitlive.firebase.database.ChildEvent
import dev.gitlive.firebase.database.DatabaseReference
import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.http.isSuccess
import io.ktor.util.InternalAPI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.mapNotNull
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
    private val userCache: UserCache,
    private val ktorClient: HttpClient
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

        return when (userRole) {
            UserRole.MANAGER -> {
                val userChatID =
                    userCache.getUserChatID() ?: return Result.failure(UnathorizedException())
                try {
                    databaseReference.child("chat")
                        .child(userChatID)
                        .child(message.currentDateTime)
                        .setValue(value = message)

                    notifyAnotherSide(userChatID, userRole, messageText)

                    Result.success(Unit)
                } catch (t: UnathorizedException) {
                    Result.failure(t)
                }
            }
//            UserRole.MECHANIC -> {}
            else -> {
                try {
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
        }
    }

    private suspend fun notifyAnotherSide(userId: String, role: UserRole, message: String) {
        if (role == UserRole.USER) {
            val managerToken = getManagerToken() ?: return
            sendNotificationToToken(managerToken, message, userId)
        } else {
            val userToken = getUserToken(userId) ?: return
            sendNotificationToToken(userToken, message, "BodyShop")
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

    private suspend fun getUserToken(userId: String): String? {
        return databaseReference.child("tokens")
            .child("user/$userId")
            .childEvents()
            .first()
            .let {
                it.snapshot.value?.toString()
            }
    }

    @OptIn(InternalAPI::class)
    private suspend fun sendNotificationToToken(token: String, message: String, userId: String) {
        val carsUrl = "https://fcm.googleapis.com/fcm/send"
        val requestBody = """
            | {"to": "$token",
            | "notification": {
            | "body":"$message",
            | "title":"$userId",
            | "content_available" : true,
            | "priority" : "high"
            | },
            | "data":{
            | "body":"$message",
            | "title":"$userId",
            | "content_available" : true,
            | "priority" : "high"
            | }
            | }"""
            .trimMargin()
        val response = ktorClient.post(urlString = carsUrl) {
            header(
                "Authorization",
                "key=AAAA3dvCFvQ:APA91bGex7HC0FbQJACSXYRb39N_Q_6RIDnstljt8XKoQXtcmB_gvD0QxdxPUohMlIBQAEr5lYQemEC4L4yFfEjfSxOVK9ouhu1Hla0Jfs6UumKgL7bTxJRbSOqRwQNkbkN1JVkMVSXu"
            )
            header("Content-Type", "application/json")
            body = requestBody
        }

        val result = if (response.status.isSuccess()) {
            Result.success(println("!!!!! Success"))
        } else {
            // handle error
            Result.failure(Exception("error"))
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

    override suspend fun observeCurrentUserChatEvents(
        userRole: UserRole,
        userChatID: String
    ): Flow<ObserveChatEvent> {
        return when (userRole) {
            UserRole.MANAGER -> observeManagerChatEvents(userChatID)
            UserRole.MECHANIC -> observeMechanicChatEvents()
            else -> observeUserChatEvents()
        }
    }

    private suspend fun observeManagerChatEvents(userChatID: String): Flow<ObserveChatEvent> {
        return databaseReference.child("chat")
            .child(userChatID)
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

    private suspend fun observeMechanicChatEvents(): Flow<ObserveChatEvent> {
        return flowOf()
    }

    private suspend fun observeUserChatEvents(): Flow<ObserveChatEvent> {
        val userId = userCache.getUserId() ?: return flowOf()
        return databaseReference.child("chat")
            .child(userId)
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

    override suspend fun observeChatsEvents(): Flow<String> {
        return databaseReference.child("chat")
            .childEvents()
            .mapNotNull { event ->
                when (event.type) {
                    ChildEvent.Type.ADDED -> event.snapshot.key
                    else -> null
                }
            }
    }

    override suspend fun getUserChatID(): Result<String?> {
        return try {
            val userChatID = userCache.getUserChatID()
            Result.success(userChatID)
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }

    override suspend fun setUserChatID(userChatID: String): Result<Unit> {
        return try {
            userCache.setUserChatID(userChatID)
            Result.success(Unit)
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }
}

