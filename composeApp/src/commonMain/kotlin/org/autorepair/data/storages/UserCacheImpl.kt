package org.autorepair.data.storages

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.autorepair.domain.models.UserCar
import org.autorepair.domain.models.UserRole

class UserCacheImpl(
    private val dataStore: DataStore<Preferences>,
    private val json: Json
) : UserCache {
    override suspend fun setUserId(id: String) {
        dataStore.edit {
            it[userIdKey] = id
        }
    }

    override suspend fun setUserRole(role: UserRole) {
        dataStore.edit {
            it[userRoleKey] = role.name
        }
    }

    override suspend fun setSelectedCarId(id: String) {
        dataStore.edit {
            it[carIdKey] = id
        }
    }

    override suspend fun setUserCars(cars: List<UserCar>) {
        val jsonString = json.encodeToString(cars)
        dataStore.edit {
            it[userCarsKey] = jsonString
        }
    }

    override suspend fun setUserChatID(userID: String) {
        dataStore.edit {
            it[userChatID] = userID
        }
    }

    override suspend fun getUserId(): String? {
        return dataStore.data.firstOrNull()?.let { it[userIdKey] }
    }

    override suspend fun getUserRole(): UserRole? {
        return dataStore.data.firstOrNull()?.let { it[userRoleKey] }
            ?.let { stringRole ->
                UserRole.values().find { it.name == stringRole }
            }
    }

    override suspend fun getSelectedCarId(): String? {
        return dataStore.data.firstOrNull()?.let { it[carIdKey] }
    }

    override suspend fun getSelectedCar(): UserCar? {
        val selectedCarId = getSelectedCarId() ?: return null
        return getUserCars().find { it.id == selectedCarId }
    }

    override suspend fun getUserCars(): List<UserCar> {
        return dataStore.data
            .firstOrNull()
            ?.let { it[userCarsKey] }
            ?.let { json.decodeFromString<List<UserCar>>(it) }
            .orEmpty()
    }

    override suspend fun getUserChatID(): String? {
        return dataStore.data.firstOrNull()?.let { it[userChatID] }
    }

    override suspend fun clearAll() {
        dataStore.edit {
            it.minusAssign(userIdKey)
            it.minusAssign(userRoleKey)
            it.minusAssign(carIdKey)
            it.minusAssign(userCarsKey)
            it.minusAssign(userChatID)
        }
    }

    companion object {
        val userIdKey = stringPreferencesKey("userId")
        val userRoleKey = stringPreferencesKey("userRole")
        val carIdKey = stringPreferencesKey("carId")
        val userCarsKey = stringPreferencesKey("userCars")
        val userChatID = stringPreferencesKey("userChatID")
    }
}