package com.projects.kanade.ui.screen.home.admin.editakun

import com.projects.kanade.model.UserDB

data class AkunTerpilihState (
    val isLoading: Boolean = false,
    val antriSuccess: UserDB? = UserDB(0,"","","",0, 0),
    val error: String? = ""
)
