package org.autorepair.presentation

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.autorepair.data.AuthRepository
import org.autorepair.data.AuthRepositoryImpl
import org.autorepair.data.models.IncorrectDataException
import kotlin.random.Random

class LoginScreenModel(
    private val repository: AuthRepository = AuthRepositoryImpl()
) : StateScreenModel<LoginState>(LoginState.Init) {

    //не очень понятно
    // flow, sharedFlow, stateFlow, channel
    // how / cold источники
//    protected val mutableEvent: MutableSharedFlow<LoginEvent> = MutableStateFlow()
//    public val events: SharedFlow<LoginEvent> = mutableState.asStateFlow()

    //посмотреть sealed class примеры

    fun onEmailChanged(email: String) {
        mutableState.value = mutableState.value.copy(email = email)
    }

    fun onPassChanged(pass: String) {
        mutableState.value = mutableState.value.copy(pass = pass)
    }

    fun onLoginClick() {
        screenModelScope.launch {
            mutableState.value = mutableState.value.copy(
                isLoading = true,
                isIncorrectData = false,
                error = null
            )
            val state = mutableState.value
            repository.auth(email = state.email, password = state.pass)
                .onSuccess {
                    // navigate
//                    events.value = NavigateToMain
                }
                .onFailure {
                    mutableState.value = mutableState.value.copy(
                        error = it,
                        isIncorrectData = it is IncorrectDataException
                    )

                }

            mutableState.value = mutableState.value.copy(isLoading = false)
        }
    }
}