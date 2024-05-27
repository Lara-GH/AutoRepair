package org.autorepair.data.repository

import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.FirebaseUser
import org.autorepair.data.exceptions.IncorrectDataException
import org.autorepair.data.storages.UserCache
import org.autorepair.domain.models.User
import org.autorepair.domain.models.UserRole
import org.autorepair.domain.repository.AuthRepository
import org.autorepair.domain.repository.UserRepository

class AuthRepositoryImpl(
    private val auth: FirebaseAuth,
    private val userCache: UserCache,
    private val userRepository: UserRepository
) : AuthRepository {
    //user
    //qazwsx12345@gmail.com
    //111111
    override suspend fun createUser(email: String, password: String): Result<User> {
        return runCatching {
            val result = auth.createUserWithEmailAndPassword(email, password)
            val resultUser = result.user
            return if (resultUser != null) {
                Result.success(User(resultUser.uid, resultUser.getRole()))
            } else {
                Result.failure(IncorrectDataException())
            }
        }.onFailure {
            return Result.failure(it)
        }
    }

    override suspend fun auth(email: String, password: String): Result<User> {
        return runCatching {
            val result = auth.signInWithEmailAndPassword(email, password)
            val resultUser = result.user
            return if (resultUser != null) {
                Result.success(User(resultUser.uid, resultUser.getRole()))
            } else {
                Result.failure(IncorrectDataException())
            }
        }.onFailure {
            return Result.failure(it)
        }
    }

    private fun getUserRoleFromClaims(claims: Map<String, Any>): UserRole {
        val roleValue = claims[ROLE_CLAIM]?.toString().orEmpty()
        return when (roleValue) {
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
        return runCatching {
            userRepository.deleteMyToken().getOrThrow()
            auth.signOut()
            userCache.clearAll()
        }
    }

    companion object {
        private const val ROLE_CLAIM = "role"
        private const val ROLE_VALUE_MANAGER = "manager"
        private const val ROLE_VALUE_MECHANIC = "mechanic"
    }
}