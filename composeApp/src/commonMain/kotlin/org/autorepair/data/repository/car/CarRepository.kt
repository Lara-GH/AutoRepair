package org.autorepair.data.repository.car

import org.autorepair.data.models.Car
import org.autorepair.data.models.YearManufacturers

interface CarRepository {
    suspend fun getCarHierarchy(): Result<YearManufacturers>
    suspend fun addCarsToUsers(cars: List<Car>): Result<Unit>
}