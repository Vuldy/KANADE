package com.projects.kanade.ui.screen.home.admin.simulator

import com.projects.kanade.ui.screen.home.dokter.cekantri.CurAntUiEvent

sealed class SimulatorUiEvent {
    object Submit : SimulatorUiEvent()
    object SubmitNoApp : SimulatorUiEvent()
}