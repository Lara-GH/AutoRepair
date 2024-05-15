package org.autorepair.data.storages

import org.autorepair.domain.models.UserCar
import org.autorepair.domain.models.UserRole

interface UserCache {
    suspend fun setUserId(id: String)
    suspend fun setUserRole(role: UserRole)
    suspend fun setSelectedCarId(id: String)
    suspend fun setUserCars(cars: List<UserCar>)
    suspend fun getUserId(): String?
    suspend fun getUserRole(): UserRole?
    suspend fun getSelectedCarId(): String?
    suspend fun getSelectedCar(): UserCar?
    suspend fun getUserCars(): List<UserCar>
    suspend fun clearAll()
}