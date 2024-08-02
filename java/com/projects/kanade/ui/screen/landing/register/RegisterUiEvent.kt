package com.projects.kanade.ui.screen.landing.register

sealed class RegisterUiEvent {
    data class NameChanged(val inputValue: String) : RegisterUiEvent()
    data class UmurChanged(val inputValue: String) : RegisterUiEvent()
    data class UsernameChanged(val inputValue: String) : RegisterUiEvent()
    data class TelpChanged(val inputValue: String) : RegisterUiEvent()
    data class PasswordChanged(val inputValue: String) : RegisterUiEvent()
    data class ConfirmPasswordChanged(val inputValue: String) : RegisterUiEvent()
    object Submit : RegisterUiEvent()
}
