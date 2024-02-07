package org.autorepair.presentation.home

sealed interface HomeEvent {
    data object NavigateToBookService: HomeEvent
    data object NavigateToAddCar: HomeEvent
    data object NavigateToChat: HomeEvent
}