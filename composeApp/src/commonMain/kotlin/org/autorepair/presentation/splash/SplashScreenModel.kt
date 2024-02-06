package org.autorepair.presentation.splash

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.autorepair.data.AuthRepository
import org.autorepair.data.AuthRepositoryImpl

class SplashScreenModel(
    private val authRepository: AuthRepository = AuthRepositoryImpl()
): StateScreenModel<SplashState>(SplashState.Init) {

    private val mutableEvent: MutableSharedFlow<SplashEvent> = MutableSharedFlow()
    val events: SharedFlow<SplashEvent> = mutableEvent.asSharedFlow()

    fun checkCurrentUser() {
        screenModelScope.launch {
            runCatching {
                val user = authRepository.getCurrentUser().getOrNull()
                val event = if(user != null) {
                    SplashEvent.NavigateToMain
                } else {
                    SplashEvent.NavigateToLogin
                }
                mutableEvent.emit(event)
            }
        }

        //TODO handle error
    }
}