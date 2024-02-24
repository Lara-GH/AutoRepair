package org.autorepair.data.storages

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.serialization.json.Json
import org.autorepair.domian.models.UserCar

class UserCacheImpl(
    private val dataStore: DataStore<Preferences>,
    private val json: Json
): UserCache {

    override suspend fun setSelectedCarId(id: String) {
        dataStore.edit {
            it[carIdKey] = id
        }
    }

    override fun setUserCars(cars: List<UserCar>) {
        // convert to json string
        // set string by key
        TODO("Not yet implemented")
    }

    override suspend fun getSelectedCarId(): String? {
        return dataStore.data.firstOrNull()?.let { it[carIdKey] }
    }

    override fun getSelectedCar(): UserCar? {
        //getSelectedCarId
        //get car list
        // find car by id
        TODO("Not yet implemented")
    }

    override fun getUserCars(): List<UserCar> {
        //get car list
        TODO("Not yet implemented")
    }

    companion object {
        val carIdKey = stringPreferencesKey("carId")
    }
}