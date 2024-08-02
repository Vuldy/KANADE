package com.projects.kanade.ui.screen.home.admin.editjadwal

import com.projects.kanade.model.JanjiTemu

class EditJadwalState (
    val isLoading: Boolean = false,
    val antriSuccess: List<JanjiTemu>? = listOf(),
    var tanggal: String = "",
    val error: String? = ""
)