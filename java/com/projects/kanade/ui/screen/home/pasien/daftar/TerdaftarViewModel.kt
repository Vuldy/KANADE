package com.projects.kanade.ui.screen.home.pasien.daftar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projects.kanade.data.AntrianRepository
import com.projects.kanade.data.LoggedUser
import com.projects.kanade.model.Resource
import com.projects.kanade.ui.screen.home.pasien.antrian.AntrianState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TerdaftarViewModel (
    private val repository: AntrianRepository
) : ViewModel() {

    private val _getAntrian: MutableStateFlow<AntrianState> = MutableStateFlow(
        AntrianState()
    )
    val getAntrian: StateFlow<AntrianState> = _getAntrian

    init {
        getAntrianUser(LoggedUser.nama, LoggedUser.username)
    }

    private fun getAntrianUser(nama: String, username: String) = viewModelScope.launch {
        repository.getDataAntrianUser(nama, username).let { result ->
            when (result){
                is Resource.Success -> {
                    _getAntrian.value = AntrianState(antriSuccess = result.data)
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

}