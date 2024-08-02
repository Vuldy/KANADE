package com.projects.kanade.ui.screen.home.dokter.cekantri

sealed class CurAntUiEvent {
    object Submit : CurAntUiEvent()
    object SubmitTidakDatang : CurAntUiEvent()
}
