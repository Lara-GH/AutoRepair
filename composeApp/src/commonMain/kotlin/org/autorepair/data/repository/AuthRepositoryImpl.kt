package org.autorepair.data.repository

import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.FirebaseAuthInvalidUserException
import dev.gitlive.firebase.auth.FirebaseUser
import org.autorepair.data.models.IncorrectDataException
import org.autorepair.domian.models.User
import org.autorepair.domian.models.UserRole
import org.autorepair.domian.repository.AuthRepository

class AuthRepositoryImpl(
    private val auth: FirebaseAuth
): AuthRepository {
    //gomellora@gmail.com
    //Qaz12345
    override suspend fun auth(email: String, password: String): Result<User> {
        return runCatching {
            val result = auth.signInWithEmailAndPassword(email, password)
            val resultUser = result.user
            return if(resultUser != null) {
                Result.success(User(resultUser.uid, resultUser.getRole()))
            } else {
                Result.failure(IncorrectDataException())
            }
        }.onFailure {
            if(it is FirebaseAuthInvalidUserException) {
                return Result.failure(IncorrectDataException())
            }
            //отсутствие сети
            //другая ошибка
        }
    }

    private fun getUserRoleFromClaims(claims: Map<String, Any>): UserRole {
        val roleValue = claims[ROLE_CLAIM]?.toString().orEmpty()
        return when(roleValue) {
            ROLE_VALUE_MANAGER -> UserRole.MANAGER
            ROLE_VALUE_MECHANIC -> UserRole.MECHANIC
            else -> UserRole.USER
        }
    }

    private suspend fun FirebaseUser.getRole(): UserRole {
        val tokenResult = getIdTokenResult(forceRefresh = true)
        return getUserRoleFromClaims(tokenResult.claims)
    }

    override suspend fun getCurrentUser(): Result<User?> {
        return runCatching {
            val firebaseUser = auth.currentUser
            firebaseUser?.let { User(firebaseUser.uid, firebaseUser.getRole()) }
        }
    }

    override suspend fun logout(): Result<Unit> {
        return runCatching { auth.signOut() }
    }

    companion object {
        private const val ROLE_CLAIM = "role"
        private const val ROLE_VALUE_MANAGER = "manager"
        private const val ROLE_VALUE_MECHANIC = "mechanic"
    }
}