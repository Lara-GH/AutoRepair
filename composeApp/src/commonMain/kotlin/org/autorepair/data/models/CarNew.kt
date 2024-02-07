package org.autorepair.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CarNew(
    val model: String,
    @SerialName("engine")
    val engines: List<String>
)