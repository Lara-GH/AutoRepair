package org.autorepair.domain.models

import org.autorepair.data.models.Manufacturer
import org.autorepair.data.models.Model
import org.autorepair.data.models.Year

data class Car(
    val year: Year,
    val manufacturer: Manufacturer,
    val model: Model
)