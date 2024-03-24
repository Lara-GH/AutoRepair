package org.autorepair.domain.repository

import org.autorepair.domain.models.UserCar
import org.autorepair.domain.models.YearManufacturers

interface CarRepository {
    suspend fun getCarHierarchy(): Result<YearManufacturers>
    suspend fun addCarsToUsers(cars: List<UserCar>): Result<Unit>
    suspend fun selectCar(id: String): Result<Unit>
    suspend fun getSelectedCar(): Result<UserCar?>
    suspend fun getSelectedCarId(): Result<String?>
}