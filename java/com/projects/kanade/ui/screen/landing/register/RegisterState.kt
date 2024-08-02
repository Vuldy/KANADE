package com.projects.kanade.ui.screen.landing.register

import com.projects.kanade.ui.common.ErrorState

data class RegisterState(
    val name: String = "",
    val username: String = "",
    val umur: String = "",
    val telp: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val errorState: RegisterErrorState = RegisterErrorState(),
    val isRegistrationSuccessful: Boolean = false,
    var isRegistrationInvalid: Boolean = false,
    var isUsernameValid: Boolean = true,
    var isPasswordValid: Boolean = true,
    val isTelpValid: Boolean = false,
)

data class RegisterErrorState(
    val nameErrorState: ErrorState = ErrorState(),
    val usernameErrorState: ErrorState = ErrorState(),
    val umurErrorState: ErrorState = ErrorState(),
    val telpErrorState: ErrorState = ErrorState(),
    val passwordErrorState: ErrorState = ErrorState(),
    val confirmPasswordErrorState: ErrorState = ErrorState()
)