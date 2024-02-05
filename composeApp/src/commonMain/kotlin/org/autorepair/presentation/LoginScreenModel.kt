package org.autorepair.presentation

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginScreenModel: StateScreenModel<LoginState>(LoginState.Init) {

    fun onEmailChanged(email: String) {
        mutableState.value = mutableState.value.copy(email = email)
    }

    fun onPassChanged(pass: String) {
        mutableState.value = mutableState.value.copy(pass = pass)
    }

    fun onLoginClick() {
        screenModelScope.launch {
            mutableState.value = mutableState.value.copy(isLoading = true)
            delay(1000)

            try {
//                val result = repository.getData()
                //success
            } catch (t: Throwable) {

            }
            mutableState.value = mutableState.value.copy(isLoading = false)
        }
    }
}