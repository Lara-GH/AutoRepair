package org.autorepair.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("id") val id: String,
    @SerialName("role") val role: UserRole,
    @SerialName("firstName") val firstName: String = "",
    @SerialName("lastName") val lastName: String = "",
    @SerialName("address") val address: String = "",
    @SerialName("email") val email: String = "",
    @SerialName("phone") val phone: String = ""
)