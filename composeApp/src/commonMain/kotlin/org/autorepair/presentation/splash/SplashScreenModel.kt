package org.autorepair.presentation.splash

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.autorepair.domain.models.UserRole
import org.autorepair.domain.repository.AuthRepository

class SplashScreenModel(
    private val authRepository: AuthRepository
) : StateScreenModel<SplashState>(SplashState.Init) {

    private val mutableEvent: MutableSharedFlow<SplashEvent> = MutableSharedFlow()
    val events: SharedFlow<SplashEvent> = mutableEvent.asSharedFlow()

    fun checkCurrentUser() {
        screenModelScope.launch {
            runCatching {
                val user = authRepository.getCurrentUser().getOrNull()
                val event = if (user != null) {
                    println("!!!!!!!!!!user!!!!!!!!!!!$user")
                    when (user.role) {
                        UserRole.MANAGER -> SplashEvent.NavigateToManagerHome
                        UserRole.MECHANIC -> SplashEvent.NavigateToMechanicHome
                        UserRole.USER -> SplashEvent.NavigateToUserHome
                    }
                } else {
                    SplashEvent.NavigateToLogin
                }
                mutableEvent.emit(event)
            }
        }
        //TODO handle error
    }
}