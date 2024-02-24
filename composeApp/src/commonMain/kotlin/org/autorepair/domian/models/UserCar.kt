package org.autorepair.domian.models

import org.autorepair.data.models.Engine
import org.autorepair.data.models.Manufacturer
import org.autorepair.data.models.Year

data class UserCar(
    val year: Year,
    val manufacturer: Manufacturer,
    val engine: Engine,
    val vin: String?
)