package org.autorepair.data.storages

import org.autorepair.domian.models.UserCar

interface UserCache {
    suspend fun setSelectedCarId(id: String)
    suspend fun setUserCars(cars: List<UserCar>)
    suspend fun getSelectedCarId(): String?
    suspend fun getSelectedCar(): UserCar?
    suspend fun getUserCars(): List<UserCar>
}