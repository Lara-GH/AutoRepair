package org.autorepair.data.repository

import dev.gitlive.firebase.database.DatabaseReference
import kotlinx.serialization.json.Json
import org.autorepair.data.models.Message
import org.autorepair.domian.repository.ChatRepository

class ChatRepositoryImpl(
    private val databaseReference: DatabaseReference,
    private val json: Json
) : ChatRepository {
    override suspend fun sendMessage(
        message: Message
    ): Result<Unit> {

        return try {
            databaseReference.child("chat").child(message.userId).child(message.currentDateTime)
                .setValue(value = message)
            Result.success(Unit)
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }

    override suspend fun newMessageAdded(messageJson: String): Result<Message?> {
        return try {
            val messageProps = messageJson
                .trim() // Удаляем начальные и конечные пробелы
                .removeSurrounding("{", "}") // Удаляем начальную и конечную фигурные скобки
                .split(",") // Разделяем строку на пары "ключ=значение"
                .map { it.split("=") } // Разделяем каждую пару на ключ и значение

            val newMessage = Message("", "", "", "", false)

            messageProps.forEach { (key, value) ->
                when (key.trim()) { // Убираем пробелы из ключа
                    "userId" -> newMessage.userId = value.trim() // Установка значения userId
                    "currentDateTime" -> newMessage.currentDateTime = value.trim() // Установка значения currentDateTime
                    "userRole" -> newMessage.userRole = value.trim() // Установка значения userRole
                    "message" -> newMessage.message = value.trim() // Установка значения message
                    "isSeen" -> newMessage.isSeen = value.trim().toBoolean() // Установка значения isSeen
                }
            }
//            val message = json.decodeFromString<Message>(messageJson)

            Result.success(newMessage)
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }
}

