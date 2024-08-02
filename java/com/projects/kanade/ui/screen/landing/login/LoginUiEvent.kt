package com.projects.kanade.ui.screen.landing.login

sealed class LoginUiEvent {
    data class UsernameChange(val inputValue: String) : LoginUiEvent()
    data class PasswordChange(val inputValue: String) : LoginUiEvent()
    object Submit : LoginUiEvent()
}