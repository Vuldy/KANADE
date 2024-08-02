package com.projects.kanade.ui.screen.home.admin.editantri

data class AddAntrianState (
    val isLoading: Boolean = false,
    val nama: String = "",
    val umur: String = "",
    var poli: String = "",
    val keluhan: String = "",
    var terdaftar: Boolean = false,
    var invalid: Boolean = false,
    var penuh: Boolean = false,
    val error: String? = ""
)
/*
        username: String,
        lastAntrian: Int,
        nama: String,
        trust: Int,
        priority: Int,
        poli: String,
        tanggal: String,
        jamjanji : String,
        keluhan: String,
        hadir: Boolean,
        waktuhadir: String,
        tipedaftar: String,
 */
