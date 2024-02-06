package org.autorepair.data

import kotlinx.coroutines.delay
import org.autorepair.data.models.IncorrectDataException
import org.autorepair.data.models.User

class AuthRepositoryImpl: AuthRepository {
    override suspend fun auth(email: String, password: String): Result<User> {
        delay(1000)
        //try catch { result.failure }

        if(email == "123" && password == "123") {
            return Result.success(User(1))
        }
        if(email == "123" && password != "123") {
            return Result.failure(IncorrectDataException())
        }
        return Result.failure(Exception("testException"))
    }
}