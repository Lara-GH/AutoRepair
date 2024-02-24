package org.autorepair.domian.repository

import org.autorepair.domian.models.User

interface AuthRepository {
    suspend fun auth(email: String, password: String) : Result<User>
    suspend fun getCurrentUser(): Result<User?>
    suspend fun logout(): Result<Unit>
}