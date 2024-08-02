package com.projects.kanade.ui.screen.home.admin.editakun

sealed class TerpilihEditUiEvent {
    data class NameChange(val inputValue: String) : TerpilihEditUiEvent()
    data class UsernameChange(val inputValue: String) : TerpilihEditUiEvent()
    data class UmurChange(val inputValue: String) : TerpilihEditUiEvent()
    data class TelpChange(val inputValue: String) : TerpilihEditUiEvent()
    data class AccessChange(val inputValue: Int) : TerpilihEditUiEvent()
    data class PasswordChange(val inputValue: String) : TerpilihEditUiEvent()
    object Submit : TerpilihEditUiEvent()
}