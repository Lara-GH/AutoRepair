package org.autorepair.domain.models

import org.autorepair.data.models.ManufacturerModels
import org.autorepair.data.models.Year

data class YearManufacturers(
    val yearToManufacturerMap: Map<Year, List<ManufacturerModels>>
)