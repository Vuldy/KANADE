package com.projects.kanade.ui.screen.home.admin.editakun

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projects.kanade.data.LoggedUser
import com.projects.kanade.data.UserRepository
import com.projects.kanade.model.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AkunTerpilihViewModel (
    private val repository: UserRepository
) : ViewModel() {

    private val _getUser: MutableStateFlow<AkunTerpilihState> = MutableStateFlow(
        AkunTerpilihState()
    )
    val getUser: StateFlow<AkunTerpilihState> = _getUser

    init {
        getUser(LoggedUser.username)
    }

    private fun getUser(username : String) = viewModelScope.launch {
        repository.getUserDetailsByUsername(username).let { result ->
            when (result){
                is Resource.Success -> {
                    _getUser.value = AkunTerpilihState(antriSuccess = result.data)
                }
                is Resource.Loading -> {
                    _getUser.value = AkunTerpilihState(isLoading = true)
                }
                is Resource.Error -> {
                    _getUser.value = AkunTerpilihState(error = result.message)
                }
            }
        }
    }

}