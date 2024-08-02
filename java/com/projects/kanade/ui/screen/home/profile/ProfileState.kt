package com.projects.kanade.ui.screen.home.profile

import com.projects.kanade.model.UserDB

data class ProfileState(
    val isLoading: Boolean = false,
    val isSuccess: UserDB? = UserDB(0,"Loading...","Loading...","Loading...", 0),
    val logoutSuccess: Boolean = false,
    val error: String? = ""
)
