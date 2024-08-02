package com.projects.kanade.ui.screen.home.pasien.antrian

import com.projects.kanade.model.JanjiTemu

data class AntrianState(
    val isLoading: Boolean = false,
    val antriSuccess: JanjiTemu? = JanjiTemu(0,"Loading...","Loading...",0,0,"Loading...","Loading...", hadir = false),
    val waktutunggu: String = "",
    val antrianSekarang: String = "",
    val urutan: Int = 0,
    val error: String? = ""
)