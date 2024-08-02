package com.projects.kanade.ui.screen.home.dokter.cekjadwal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projects.kanade.data.AntrianRepository
import com.projects.kanade.data.LoggedUser
import com.projects.kanade.data.UserRepository
import com.projects.kanade.model.JanjiTemu
import com.projects.kanade.model.Resource
import com.projects.kanade.model.User
import com.projects.kanade.ui.common.UiState
import com.projects.kanade.ui.screen.home.dokter.cekantri.CurAntUiEvent
import com.projects.kanade.ui.screen.home.dokter.cekantri.CurrentAntrianState
import com.projects.kanade.ui.screen.home.pasien.antrian.AntrianState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class JadwalDokterViewModel (
    private val repository: AntrianRepository
) : ViewModel() {

    private val _getAntrian: MutableStateFlow<CekJadwalState> = MutableStateFlow(
        CekJadwalState()
    )
    val getAntrian: StateFlow<CekJadwalState> = _getAntrian

    init {
        getAntrianPoli(LoggedUser.selectedPoli, LoggedUser.currentSelectedTanggal)
    }

    fun onUiEvent(curAntUiEvent: CurAntUiEvent) {
        viewModelScope.launch {
            when (curAntUiEvent) {
                is CurAntUiEvent.Submit -> {
                    //repository.deleteCurrentAntrian(getAntrian.value.currentID)
                    getAntrianPoli(LoggedUser.selectedPoli, LoggedUser.currentSelectedTanggal)
                }
                is CurAntUiEvent.SubmitTidakDatang -> {
                    //repository.deleteCurrentAntrian(getAntrian.value.currentID)
                    getAntrianPoli(LoggedUser.selectedPoli, LoggedUser.currentSelectedTanggal)
                }
            }

        }
    }

    private fun getAntrianPoli(poli: String, tanggal: String) = viewModelScope.launch {
        repository.getAllAntrianPoliTanggal(poli, tanggal).let { result ->
            when (result){
                is Resource.Success -> {
                    _getAntrian.value = CekJadwalState(antriSuccess = result.data)
                }
                is Resource.Loading -> {
                    _getAntrian.value = CekJadwalState(isLoading = true)
                }
                is Resource.Error -> {
                    _getAntrian.value = CekJadwalState(error = result.message)
                }
            }
        }
    }

}

    /*fun getUserfromPoli() {
        viewModelScope.launch {
            repository.getUserfromPoli("Umum")
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { savedUser ->
                    _uiState.value = UiState.Success(savedUser)
                }
        }
    }*/