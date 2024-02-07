package org.autorepair.data.repository.car

import kotlinx.coroutines.flow.first
import org.autorepair.data.FirebaseHolder
import org.autorepair.data.models.Car
import org.autorepair.data.models.YearManufacturers

class CarRepositoryImpl(
    private val firebaseHolder: FirebaseHolder = FirebaseHolder
): CarRepository {
    override suspend fun getCarHierarchy(): Result<YearManufacturers> {
        val years = firebaseHolder.database.reference().child("year")
        val data = years.valueEvents.first()
        return Result.success(YearManufacturers(emptyMap()))
    }

    override suspend fun addCarsToUsers(cars: List<Car>): Result<Unit> {
        return Result.success(Unit)
    }
}