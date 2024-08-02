package com.projects.kanade.ui.screen.home.pasien.daftar

sealed class PilihJadwalUiEvent {
    data class KeluhanChange(val inputValue: String) : PilihJadwalUiEvent()
    object Submit : PilihJadwalUiEvent()
}