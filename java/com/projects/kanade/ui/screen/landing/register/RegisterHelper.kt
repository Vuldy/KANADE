package com.projects.kanade.ui.screen.landing.register

import com.projects.kanade.R
import com.projects.kanade.ui.common.ErrorState

val nameEmptyErrorState = ErrorState(
    hasError = true,
    errorMessageStringResource = R.string.regist_empty_name
)

val usernameEmptyErrorState = ErrorState(
    hasError = true,
    errorMessageStringResource = R.string.regist_empty_username
)

val telpEmptyErrorState = ErrorState(
    hasError = true,
    errorMessageStringResource = R.string.regist_empty_telp
)

val passwordEmptyErrorState = ErrorState(
    hasError = true,
    errorMessageStringResource = R.string.regist_empty_password
)

val confirmPasswordEmptyErrorState = ErrorState(
    hasError = true,
    errorMessageStringResource = R.string.regist_confirm_password
)

val passwordMismatchErrorState = ErrorState(
    hasError = true,
    errorMessageStringResource = R.string.regist_mismatch_password
)
