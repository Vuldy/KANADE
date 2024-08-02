package com.projects.kanade.ui.screen.home.admin.editakun

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projects.kanade.data.LoggedUser
import com.projects.kanade.data.UserRepository
import com.projects.kanade.model.Resource
import com.projects.kanade.model.User
import com.projects.kanade.ui.common.UiState
import com.projects.kanade.ui.screen.home.dokter.cekantri.CurAntUiEvent
import com.projects.kanade.ui.screen.home.dokter.cekjadwal.CekJadwalState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class EditAkunViewModel (
    private val repository: UserRepository
) : ViewModel() {

    private val _getUser: MutableStateFlow<EditAkunState> = MutableStateFlow(
        EditAkunState()
    )
    val getUser: StateFlow<EditAkunState> = _getUser

    init {
        getUser()
    }

    private fun getUser() = viewModelScope.launch {
        repository.getAllUserExAdmin().let { result ->
            when (result){
                is Resource.Success -> {
                    _getUser.value = EditAkunState(antriSuccess = result.data)
                }
                is Resource.Loading -> {
                    _getUser.value = EditAkunState(isLoading = true)
                }
                is Resource.Error -> {
                    _getUser.value = EditAkunState(error = result.message)
                }
            }
        }
    }

}