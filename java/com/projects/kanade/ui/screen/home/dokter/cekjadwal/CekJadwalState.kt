package com.projects.kanade.ui.screen.home.dokter.cekjadwal

import com.projects.kanade.model.JanjiTemu

data class CekJadwalState(
    val isLoading: Boolean = false,
    val antriSuccess: List<JanjiTemu>? = listOf(),
    var tanggal: String = "",
    val error: String? = ""
)
