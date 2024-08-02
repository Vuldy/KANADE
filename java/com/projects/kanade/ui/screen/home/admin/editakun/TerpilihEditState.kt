package com.projects.kanade.ui.screen.home.admin.editakun

import com.projects.kanade.model.UserDB

data class TerpilihEditState (
    val isLoading: Boolean = false,
    val antriSuccess: UserDB? = UserDB(0,"","","",0, 0),
    val username: String = "",
    val nama: String = "",
    val telp: String = "",
    val umur: String = "",
    var access: Int = 0,
    var invalid: Boolean = false,
    val password: String = "",
    val passwordBaru: String = "",
    var success: Boolean = false,
    val error: String? = ""
)