package org.autorepair.data.repository

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.serialization.json.Json
import org.autorepair.data.models.Engine
import org.autorepair.data.models.Manufacturer
import org.autorepair.data.models.ManufacturerModels
import org.autorepair.data.models.Model
import org.autorepair.data.models.Year
import org.autorepair.data.models.YearManufacturesNew
import org.autorepair.data.storages.UserCache
import org.autorepair.domian.models.Car
import org.autorepair.domian.models.UserCar
import org.autorepair.domian.models.YearManufacturers
import org.autorepair.domian.repository.CarRepository

class CarRepositoryImpl(
    private val ktorClient: HttpClient,
    private val json: Json,
    private val userCache: UserCache
) : CarRepository {
    override suspend fun getCarHierarchy(): Result<YearManufacturers> {
        val carsUrl =
            "https://firebasestorage.googleapis.com/v0/b/autorepair-b4f91.appspot.com/o/cars%2Fcars.json?alt=media&token=f027cbe0-cee8-4b4b-aa98-aada1821bd33"
        val response = ktorClient.get(carsUrl) {}

        val result = if (response.status.isSuccess()) {
            val year = json.decodeFromString(
                deserializer = YearManufacturesNew.serializer(),
                string = response.bodyAsText()
            )
            Result.success(mapToYearManufactures(year))
        } else {
            // handle error
            Result.failure(Exception("error"))
        }

        return result
    }

    private fun mapToYearManufactures(input: YearManufacturesNew): YearManufacturers {

        val map = HashMap(input.yearManufacturerMap)
            .mapKeys { (key, value) ->
                Year(key)
            }
            .mapValues { (key2, value2) ->
                val map2 = HashMap(value2)
                map2.mapKeys { (key3, value3) ->
                    Manufacturer(key3)
                }
            }.mapValues { (key4, value4) ->
                val map4 = HashMap(value4)
                map4.mapValues { (key5, value5) ->
                    val map5 = HashMap(value5)
                    map5.mapValues { (key6, value6) ->
                        val listEngines = ArrayList<Engine>()
                        for (str in value6.engines) {
                            listEngines.add(Engine(str))
                        }
                        Model(model = value6.model, engines = listEngines)
                    }.values.toList()
                }
            }.mapValues { (key7, value7) ->
                val map7 = HashMap(value7)
                map7.map { (key8, value8) ->
                    ManufacturerModels(manufacturer = key8, models = value8)
                }.toList()
            }
        return YearManufacturers(yearToManufacturerMap = map)
    }

    override suspend fun addCarsToUsers(cars: List<Car>): Result<Unit> {
        return Result.success(Unit)
    }
    override suspend fun selectCar(id: String): Result<Unit> {
        return try {
            userCache.setSelectedCarId(id)
            Result.success(Unit)
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }

    override suspend fun getSelectedCar(): Result<UserCar?> {
        val car = userCache.getSelectedCar()
        return Result.success(car)
    }

    override suspend fun getSelectedCarId(): Result<String?> {
        //handle error
        val carId = userCache.getSelectedCarId()
        return Result.success(carId)
    }
}