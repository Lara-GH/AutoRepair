package org.autorepair.presentation.home

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class HomeScreenModel : StateScreenModel<HomeState>(HomeState.Init)  {

    private val mutableEvent: MutableSharedFlow<HomeEvent> = MutableSharedFlow()
    val events: SharedFlow<HomeEvent> = mutableEvent.asSharedFlow()

    fun onBookServiceClick() {
        screenModelScope.launch {
            mutableEvent.emit(HomeEvent.NavigateToBookService)
        }
    }

    fun onAddCarClick() {
        screenModelScope.launch {
            mutableEvent.emit(HomeEvent.NavigateToAddCar)
        }
    }

    fun onContactToBodyshopClick() {
        screenModelScope.launch {
            mutableEvent.emit(HomeEvent.NavigateToChat)
        }
    }
}