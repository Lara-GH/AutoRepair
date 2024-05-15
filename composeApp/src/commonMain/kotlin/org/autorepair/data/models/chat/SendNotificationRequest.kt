package org.autorepair.data.models.chat

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SendNotificationRequest(
    @SerialName("to")
    val to: String,
    @SerialName("notification")
    val notification: NotificationContent,
    @SerialName("data")
    val data: NotificationContent
)

@Serializable
data class NotificationContent(
    @SerialName("body")
    val body: String,
    @SerialName("title")
    val title: String,
    @SerialName("content_available")
    val contentAvailable: Boolean = true,
    @SerialName("priority")
    val priority: String = "high"
)
