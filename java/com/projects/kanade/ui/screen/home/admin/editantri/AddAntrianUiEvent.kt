package com.projects.kanade.ui.screen.home.admin.editantri

sealed class AddAntrianUiEvent {
    data class NameChange(val inputValue: String) : AddAntrianUiEvent()
    //data class UsernameChange(val inputValue: String) : AddAntrianUiEvent()
    data class UmurChange(val inputValue: String) : AddAntrianUiEvent()
    data class KeluhanChange(val inputValue: String) : AddAntrianUiEvent()
    object Submit : AddAntrianUiEvent()
}