package org.autorepair.data.models.chat

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FirebaseMessage(
    @SerialName("id") val id: String,
    @SerialName("userId") val userId: String,
    @SerialName("currentDateTime") val currentDateTime: String,
    @SerialName("userRole") val userRole: String,
    @SerialName("message") val message: String,
    @SerialName("isSeen") val isSeen: Boolean
)