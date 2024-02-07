package org.autorepair.presentation.login

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
//import org.autorepair.data.AuthRepository
//import org.autorepair.data.AuthRepositoryImpl
//import org.autorepair.data.models.IncorrectDataException

class LoginScreenModel(
//    private val repository: AuthRepository = AuthRepositoryImpl()
) : StateScreenModel<LoginState>(LoginState.Init) {

    private val mutableEvent: MutableSharedFlow<LoginEvent> = MutableSharedFlow()
    val events: SharedFlow<LoginEvent> = mutableEvent.asSharedFlow()

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
//            repository.auth(email = state.email, password = state.pass)
//                .onSuccess {
//                    mutableEvent.emit(LoginEvent.NavigateToMain)
//                }
//                .onFailure {
//                    mutableState.value = mutableState.value.copy(
//                        error = it,
//                        isIncorrectData = it is IncorrectDataException
//                    )
//                }

            mutableState.value = mutableState.value.copy(isLoading = false)
        }
    }
}