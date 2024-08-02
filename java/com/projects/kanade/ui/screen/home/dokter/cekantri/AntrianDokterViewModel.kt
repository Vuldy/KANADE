package com.projects.kanade.ui.screen.home.dokter.cekantri

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projects.kanade.data.AntrianRepository
import com.projects.kanade.data.LoggedUser
import com.projects.kanade.data.UserRepository
import com.projects.kanade.model.Resource
import com.projects.kanade.model.User
import com.projects.kanade.ui.common.UiState
import com.projects.kanade.ui.reusables.GetDate
import com.projects.kanade.ui.reusables.desiredToSession
import com.projects.kanade.ui.reusables.determineTime
import com.projects.kanade.ui.screen.home.pasien.antrian.AntrianState
import com.projects.kanade.ui.screen.home.pasien.daftar.PilihJadwalUiEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class AntrianDokterViewModel (
    private val repository: AntrianRepository
) : ViewModel() {

    private val _getAntrian: MutableStateFlow<CurrentAntrianState> = MutableStateFlow(
        CurrentAntrianState()
    )
    val getAntrian: StateFlow<CurrentAntrianState> = _getAntrian
    private val date = GetDate()

    init {
        getAntrianList()
    }

    fun onUiEvent(curAntUiEvent: CurAntUiEvent) {
        viewModelScope.launch {
            when (curAntUiEvent) {
                is CurAntUiEvent.Submit -> {
                    //repository.deleteCurrentAntrian(getAntrian.value.currentID)
                    repository.finishCurrentAntrian(getAntrian.value.currentID)

                }

                is CurAntUiEvent.SubmitTidakDatang -> {
                    //repository.deleteCurrentAntrian(getAntrian.value.currentID)
                    repository.absentCurrentAntrian(getAntrian.value.currentID, getAntrian.value.antriSuccess!!.username, getAntrian.value.antriSuccess!!.trust)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getAntrianList() = viewModelScope.launch {
        repository.getNextAntrian(LoggedUser.selectedPoli, date, desiredToSession(determineTime())).let { result ->
            when (result){
                is Resource.Success -> {
                    _getAntrian.value = CurrentAntrianState(antriSuccess = result.data, currentID = "${result.data!!.namaPasien}${result.data!!.username}")
                }
                is Resource.Loading -> {
                    _getAntrian.value = CurrentAntrianState(isLoading = true)
                }
                is Resource.Error -> {
                    _getAntrian.value = CurrentAntrianState(error = result.message)
                }
            }
        }
    }
}