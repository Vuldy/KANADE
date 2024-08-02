package com.projects.kanade.ui.common

import androidx.annotation.StringRes
import com.projects.kanade.R

data class ErrorState(
    val hasError: Boolean = false,
    @StringRes val errorMessageStringResource: Int = R.string.empty_string
)