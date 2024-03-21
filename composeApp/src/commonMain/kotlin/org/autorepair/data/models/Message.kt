package org.autorepair.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Message(
    @SerialName("userId") var userId: String,
    @SerialName("currentDateTime") var currentDateTime: String,
    @SerialName("userRole") var userRole: String,
    @SerialName("message") var message: String,
    @SerialName("isSeen") var isSeen: Boolean
)