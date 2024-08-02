package com.projects.kanade.ui.screen.home.admin.editakun

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projects.kanade.data.LoggedUser
import com.projects.kanade.data.UserRepository
import com.projects.kanade.model.Resource
import com.projects.kanade.model.UserDB
import com.projects.kanade.ui.screen.landing.register.RegisterErrorState
import com.projects.kanade.ui.screen.landing.register.confirmPasswordEmptyErrorState
import com.projects.kanade.ui.screen.landing.register.nameEmptyErrorState
import com.projects.kanade.ui.screen.landing.register.passwordEmptyErrorState
import com.projects.kanade.ui.screen.landing.register.passwordMismatchErrorState
import com.projects.kanade.ui.screen.landing.register.telpEmptyErrorState
import com.projects.kanade.ui.screen.landing.register.usernameEmptyErrorState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TerpilihEditViewModel (
    private val repository: UserRepository
) : ViewModel() {

    private val _getUser: MutableStateFlow<TerpilihEditState> = MutableStateFlow(
        TerpilihEditState()
    )
    val getUser: StateFlow<TerpilihEditState> = _getUser

    init {
        getUser(LoggedUser.username)
    }

    fun onUiEvent(terpilihUiEvent: TerpilihEditUiEvent) {
        viewModelScope.launch {
            when (terpilihUiEvent) {
                is TerpilihEditUiEvent.UsernameChange -> {
                    _getUser.value = _getUser.value.copy(
                        username = terpilihUiEvent.inputValue
                    )
                }
                is TerpilihEditUiEvent.NameChange -> {
                    _getUser.value = _getUser.value.copy(
                        nama = terpilihUiEvent.inputValue
                    )
                }
                is TerpilihEditUiEvent.UmurChange -> {
                    _getUser.value = _getUser.value.copy(
                        umur = terpilihUiEvent.inputValue
                    )
                }
                is TerpilihEditUiEvent.TelpChange -> {
                    _getUser.value = _getUser.value.copy(
                        telp = terpilihUiEvent.inputValue
                    )
                }
                is TerpilihEditUiEvent.AccessChange -> {
                    _getUser.value = _getUser.value.copy(
                        access = terpilihUiEvent.inputValue
                    )
                }
                is TerpilihEditUiEvent.PasswordChange -> {
                    _getUser.value = _getUser.value.copy(
                        password = terpilihUiEvent.inputValue
                    )
                }
                is TerpilihEditUiEvent.Submit -> {
                    if (validateInputs()){
                        _getUser.value = _getUser.value.copy(
                            success = true
                        )

                    repository.updateUser(
                        _getUser.value.username,
                        _getUser.value.nama,
                        _getUser.value.telp,
                        _getUser.value.access,
                        _getUser.value.password,
                        _getUser.value.umur.toInt()
                    )
                    }
                }
            }
        }
    }

    private suspend fun validateInputs(): Boolean {
        val umurString = _getUser.value.umur.trim()
        val telpString = _getUser.value.telp.trim()

        return when {

            umurString.isEmpty() -> {
                _getUser.value = _getUser.value.copy(
                    invalid = true,
                )
                false
            }

            // Email empty
            telpString.isEmpty() -> {
                _getUser.value = _getUser.value.copy(
                    invalid = true,
                )
                false
            }

            // No errors
            else -> {
                // Set default error state
                _getUser.value = _getUser.value.copy(
                    invalid = false,
                )
                true
            }
        }
    }

    private fun getUser(username : String) = viewModelScope.launch {
        repository.getUserDetailsByUsername(username).let { result ->
            when (result){
                is Resource.Success -> {
                    _getUser.value = TerpilihEditState(
                        antriSuccess = result.data,
                        username = result.data!!.username,
                        umur = result.data.umur.toString(),
                        nama = result.data.nama,
                        telp = result.data.telp,
                        access = result.data.access,
                        //password = result.data.password
                    )
                }
                is Resource.Loading -> {
                    _getUser.value = TerpilihEditState(isLoading = true)
                }
                is Resource.Error -> {
                    _getUser.value = TerpilihEditState(error = result.message)
                }
            }
        }
    }

}