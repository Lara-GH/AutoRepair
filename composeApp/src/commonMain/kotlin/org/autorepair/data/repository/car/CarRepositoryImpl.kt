package org.autorepair.data.repository.car

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.serialization.json.Json
import org.autorepair.data.FirebaseHolder
import org.autorepair.data.models.YearManufacturers
import org.autorepair.data.models.YearManufacturesNew
import org.autorepair.data.repository.Ktor
import org.autorepair.data.repository.jsonObject

class CarRepositoryImpl(
    private val firebaseHolder: FirebaseHolder = FirebaseHolder,
    private val ktorClient: HttpClient = Ktor.client,
    private val json: Json = jsonObject
) : CarRepository {
    override suspend fun getCarHierarchy(): Result<YearManufacturers> {
        val carsUrl =
            "https://firebasestorage.googleapis.com/v0/b/autorepair-b4f91.appspot.com/o/cars%2Fcars.json?alt=media&token=f027cbe0-cee8-4b4b-aa98-aada1821bd33"
        val result = ktorClient.get(carsUrl) {

        }

        if (result.status.isSuccess()) {
            val year = json.decodeFromString(
                deserializer = YearManufacturesNew.serializer(),
                string = result.bodyAsText()
            )
            year
        } else {
            // handle error
        }

        return getCarHierarchyFromNative()
    }
}