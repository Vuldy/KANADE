package com.projects.kanade.ui.screen.home.admin.editakun


import com.projects.kanade.model.UserDB

data class EditAkunState(
    val isLoading: Boolean = false,
    val antriSuccess: List<UserDB>? = listOf(),
    val error: String? = ""
)
