package org.autorepair.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class YearManufacturesNew(
    @SerialName("cars")
    val yearManufacturerMap: Map<String, Map<String, Map<String, CarNew>>>
)