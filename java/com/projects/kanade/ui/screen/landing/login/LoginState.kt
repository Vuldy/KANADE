package com.projects.kanade.ui.screen.landing.login

import com.projects.kanade.ui.common.ErrorState

data class LoginState(
    var id: Long = 0,
    var telp: String = "",
    var password: String = "",
    var errorState: LoginErrorState = LoginErrorState(),
    var isLoginSuccessful: Boolean = false,
    var isLoginIncorrect: Boolean = false,
)

data class LoginErrorState(
    var telpErrorState: ErrorState = ErrorState(),
    var passwordErrorState: ErrorState = ErrorState()
)