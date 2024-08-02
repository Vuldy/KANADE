package com.projects.kanade.ui.screen.home.admin.editjadwal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projects.kanade.data.AntrianRepository
import com.projects.kanade.data.LoggedUser
import com.projects.kanade.model.Resource
import com.projects.kanade.ui.screen.home.dokter.cekantri.CurAntUiEvent
import com.projects.kanade.ui.screen.home.dokter.cekjadwal.CekJadwalState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EditJadwalViewModel (
    private val repository: AntrianRepository
) : ViewModel() {

    private val _getAntrian: MutableStateFlow<EditJadwalState> = MutableStateFlow(
        EditJadwalState()
    )
    val getAntrian: StateFlow<EditJadwalState> = _getAntrian

    init {
        getAntrianPoli(LoggedUser.selectedPoli, LoggedUser.currentSelectedTanggal)
    }

    fun onUiEvent(editJUiEvent: EditJadwalUiEvent) {
        viewModelScope.launch {
            when (editJUiEvent) {
                is EditJadwalUiEvent.Submit -> {
                    //repository.deleteCurrentAntrian(getAntrian.value.currentID)
                    getAntrianPoli(LoggedUser.selectedPoli, LoggedUser.currentSelectedTanggal)
                    repository.cancelAntrian("${LoggedUser.nama}${LoggedUser.username}", LoggedUser.username, -1)
                }

                /*is CurAntUiEvent.SubmitTidakDatang -> {
                    //repository.deleteCurrentAntrian(getAntrian.value.currentID)
                    getAntrianPoli(LoggedUser.selectedPoli, LoggedUser.currentSelectedTanggal)
                }*/
            }
        }
    }

    private fun getAntrianPoli(poli: String, tanggal: String) = viewModelScope.launch {
        repository.getAllAntrianPoliTanggal(poli, tanggal).let { result ->
            when (result){
                is Resource.Success -> {
                    _getAntrian.value = EditJadwalState(antriSuccess = result.data)
                }
                is Resource.Loading -> {
                    _getAntrian.value = EditJadwalState(isLoading = true)
                }
                is Resource.Error -> {
                    _getAntrian.value = EditJadwalState(error = result.message)
                }
            }
        }
    }

}