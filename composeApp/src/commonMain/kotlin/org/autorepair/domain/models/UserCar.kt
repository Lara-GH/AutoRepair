package org.autorepair.domain.models

import kotlinx.serialization.Serializable
import org.autorepair.data.models.Engine
import org.autorepair.data.models.Manufacturer
import org.autorepair.data.models.Year
@Serializable
data class UserCar(
    val id: String,
    val year: Year,
    val manufacturer: Manufacturer,
    val engine: Engine,
    val vin: String?
)