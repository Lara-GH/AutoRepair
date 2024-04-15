package org.autorepair.presentation.settings

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.autorepair.domain.repository.AuthRepository

class SettingsScreenModel(private val authRepository: AuthRepository) :
    StateScreenModel<SettingsState>(SettingsState.Init) {

    private val mutableEvent: MutableSharedFlow<SettingsEvent> = MutableSharedFlow()
    val events: SharedFlow<SettingsEvent> = mutableEvent.asSharedFlow()

    init {
        println("StateScreenModel created $this!!!!!")
    }

    fun onLogoutClick() {
        screenModelScope.launch {
            mutableState.value = mutableState.value.copy(isLoading = true)
            authRepository.logout()
                .onSuccess {
                    mutableEvent.emit(SettingsEvent.NavigateToLogin)
                }
                .onFailure {
                    mutableState.value = mutableState.value.copy(
                        error = it
                    )
                }
            mutableState.value = mutableState.value.copy(isLoading = false)
        }
    }

    override fun onDispose() {
        println()
        println("onDispose SettingsScreenModel!!!!!")
        super.onDispose()
    }
}