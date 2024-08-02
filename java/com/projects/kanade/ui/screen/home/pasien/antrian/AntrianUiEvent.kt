package com.projects.kanade.ui.screen.home.pasien.antrian

sealed class AntrianUiEvent {
    object Submit : AntrianUiEvent()
    object Hadir : AntrianUiEvent()
}
