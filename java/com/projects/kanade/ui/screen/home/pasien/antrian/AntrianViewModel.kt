package com.projects.kanade.ui.screen.home.pasien.antrian

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projects.kanade.data.AntrianRepository
import com.projects.kanade.data.LoggedUser
import com.projects.kanade.data.UserRepository
import com.projects.kanade.model.JanjiTemu
import com.projects.kanade.model.Resource
import com.projects.kanade.model.User
import com.projects.kanade.ui.common.UiState
import com.projects.kanade.ui.reusables.GetTime
import com.projects.kanade.ui.reusables.GetTimeString
import com.projects.kanade.ui.reusables.compareTime
import com.projects.kanade.ui.reusables.serviceTime
import com.projects.kanade.ui.screen.home.pasien.daftar.PilihJadwalUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
class AntrianViewModel (
    private val repository: AntrianRepository
) : ViewModel() {

    private val _getAntrian: MutableStateFlow<AntrianState> = MutableStateFlow(
        AntrianState()
    )
    val getAntrian: StateFlow<AntrianState> = _getAntrian

    init {
        getAntrianUser(LoggedUser.nama, LoggedUser.username)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getAntrianUser(nama: String, username: String) = viewModelScope.launch {
        repository.getDataAntrianUser(nama, username).let { result ->
            when (result){
                is Resource.Success -> {

                    val waktutunggu = if (result.data!!.nomorAntrian != 0){
                        repository.determineWaitTime(
                            result.data!!.poli,
                            result.data!!.tanggal,
                            result.data!!.nomorAntrian,
                            result.data!!.jamjanji,
                            result.data!!.expectedtime,
                            result.data!!.predictedtime
                        )

                        /*serviceTime(
                            result.data!!.jamjanji,
                            repository.getWaitTime(
                                result.data!!.poli,
                                result.data!!.tanggal,
                                result.data!!.jamjanji,
                                result.data!!.nomorAntrian,
                                result.data!!.predictedtime
                            )
                        )*/
                    } else {
                        ""
                    }

                    val urutan = repository.getUserIndex(
                        result.data!!.poli,
                        result.data!!.username,
                        result.data!!.namaPasien,
                        result.data!!.tanggal,
                        result.data!!.jamjanji,
                    )

                    /*val antrianSekarang = repository.getMinNomorAntrianOffline(result.data!!.poli, result.data!!.tanggal, result.data!!.jamjanji, result.data!!.expectedtime)*/
                    val antrianSekarang = 0
                    val antrianSekarangRes =
                        if (antrianSekarang != 0) {
                        "$antrianSekarang"
                    }
                        else {
                            "Belum ada kedatangan"
                        }

                    _getAntrian.value = AntrianState(
                        antriSuccess = result.data,
                        waktutunggu = waktutunggu,
                        antrianSekarang = antrianSekarangRes,
                        urutan = urutan,
                    )
                }
                is Resource.Loading -> {
                    _getAntrian.value = AntrianState(isLoading = true)
                }
                is Resource.Error -> {
                    _getAntrian.value = AntrianState(error = result.message)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onUiEvent(antrianUiEvent: AntrianUiEvent) {
        viewModelScope.launch {
            when (antrianUiEvent) {
                is AntrianUiEvent.Submit -> {
                        repository.cancelAntrian("${LoggedUser.nama}${LoggedUser.username}", LoggedUser.username, LoggedUser.trust)
                    }

                is AntrianUiEvent.Hadir -> {
                    repository.hadirAntrian(
                        LoggedUser.username,
                        LoggedUser.nama,
                        GetTimeString(),
                        )
                    }
                }
            }
        }
    }
/*
    private val _uiState: MutableStateFlow<UiState<JanjiTemu?>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<JanjiTemu?>>
        get() = _uiState

    fun getUserById(nama: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getAntrianUser(nama))
        }
    }*/