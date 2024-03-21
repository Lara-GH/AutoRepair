package org.autorepair.data.storages

import org.autorepair.domian.models.UserCar

interface UserCache {
    suspend fun setUserId(id: String)
    suspend fun setSelectedCarId(id: String)
    suspend fun setUserCars(cars: List<UserCar>)
    suspend fun getUserId(): String?
    suspend fun getSelectedCarId(): String?
    suspend fun getSelectedCar(): UserCar?
    suspend fun getUserCars(): List<UserCar>
}