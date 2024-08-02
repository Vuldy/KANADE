package com.projects.kanade.ui.screen.home.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projects.kanade.data.LoggedUser
import com.projects.kanade.data.UserRepository
import com.projects.kanade.model.Resource
import com.projects.kanade.model.User
import com.projects.kanade.ui.common.UiState
import com.projects.kanade.ui.reusables.GetTimeString
import com.projects.kanade.ui.screen.home.dokter.cekantri.CurrentAntrianState
import com.projects.kanade.ui.screen.home.pasien.antrian.AntrianUiEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel (
    private val repository: UserRepository
) : ViewModel() {

    private val _getUser: MutableStateFlow<ProfileState> = MutableStateFlow(
        ProfileState()
    )
    val getUser: StateFlow<ProfileState> = _getUser

    init {
        getDataUser()
    }

    private fun getDataUser() = viewModelScope.launch {
        repository.getUserDetailsByUsername(LoggedUser.username).let { result ->
            when (result){
                is Resource.Success -> {
                    _getUser.value = ProfileState(isSuccess = result.data)
                }
                is Resource.Loading -> {
                    _getUser.value = ProfileState(isLoading = true)
                }
                is Resource.Error -> {
                    _getUser.value = ProfileState(error = result.message)
                }
            }
        }
    }

    fun onUiEvent(profileUiEvent: ProfileUiEvent) {
        viewModelScope.launch {
            when (profileUiEvent) {
                is ProfileUiEvent.Submit -> {
                    repository.auth.signOut()
                }
            }
        }
    }

    /*private val _uiState: MutableStateFlow<UiState<User>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<User>>
        get() = _uiState

    fun getUserById(username: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getUserDetailsByUsername(username).data)
        }
    }*/
}