package org.autorepair.data.models

data class YearManufacturers(
    val yearToManufacturerMap: Map<Year, List<ManufacturerModels>>
)