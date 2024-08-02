package com.projects.kanade.ui.screen.home.admin.simulator

import com.projects.kanade.model.JanjiTemu

data class SimulatorState(
    val isLoading: Boolean = false,
    val antriSuccess: JanjiTemu? = JanjiTemu(0,"Loading...","Loading...",0,0, "Loading...","Loading..."),
    val error: String? = ""
)