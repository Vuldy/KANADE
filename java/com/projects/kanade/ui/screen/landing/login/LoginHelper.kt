package com.projects.kanade.ui.screen.landing.login

import com.projects.kanade.R
import com.projects.kanade.ui.common.ErrorState

val emailOrMobileEmptyErrorState = ErrorState(
    hasError = true,
    errorMessageStringResource = R.string.login_empty_username
)

val passwordEmptyErrorState = ErrorState(
    hasError = true,
    errorMessageStringResource = R.string.login_empty_password
)