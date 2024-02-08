package org.autorepair.presentation.settings

sealed interface SettingsEvent {
    data object NavigateToLogin: SettingsEvent

}