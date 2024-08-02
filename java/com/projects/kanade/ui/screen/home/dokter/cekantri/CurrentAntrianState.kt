package com.projects.kanade.ui.screen.home.dokter.cekantri

import com.projects.kanade.model.JanjiTemu

data class CurrentAntrianState(
    val isLoading: Boolean = false,
    val antriSuccess: JanjiTemu? = JanjiTemu(0,"Loading...","Loading...",0,0, "Loading...","Loading..."),
    val currentID: String = "",
    var currentPoli: String = "Umum",
    val error: String? = ""
)