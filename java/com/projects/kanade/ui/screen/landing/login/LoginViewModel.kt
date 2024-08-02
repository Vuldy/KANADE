package com.projects.kanade.ui.screen.landing.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projects.kanade.data.LoggedUser
import com.projects.kanade.data.UserRepository
import com.projects.kanade.model.Resource
import com.projects.kanade.model.User
import com.projects.kanade.ui.common.ErrorState
import com.projects.kanade.ui.common.UiState
import com.projects.kanade.ui.screen.home.pasien.antrian.AntrianState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel (
    private val repository: UserRepository
) : ViewModel() {

    var loginState = mutableStateOf(LoginState())
    private set

    fun onUiEvent(loginUiEvent: LoginUiEvent) {
        when (loginUiEvent) {

            // Email/Mobile changed
            is LoginUiEvent.UsernameChange -> {
                loginState.value = loginState.value.copy(
                    telp = loginUiEvent.inputValue,
                    errorState = loginState.value.errorState.copy(
                        telpErrorState = if (loginUiEvent.inputValue.trim().isNotEmpty())
                            ErrorState()
                        else
                            emailOrMobileEmptyErrorState
                    )
                )
            }
            // Password changed
            is LoginUiEvent.PasswordChange -> {
                loginState.value = loginState.value.copy(
                    password = loginUiEvent.inputValue,
                    errorState = loginState.value.errorState.copy(
                        passwordErrorState = if (loginUiEvent.inputValue.trim().isNotEmpty())
                            ErrorState()
                        else
                            passwordEmptyErrorState
                    )
                )
            }

            // Submit Login
            is LoginUiEvent.Submit -> {
                val inputsValidated = validateInputs()
                if (inputsValidated) {
                    checkLogin()
                }
                else {
                    loginState.value = loginState.value.copy(
                        isLoginIncorrect = true
                    )
                }
            }
        }
    }

    private fun validateInputs(): Boolean {
        val telpString = loginState.value.telp.trim()
        val passwordString = loginState.value.password
        return when {

            // Email/Mobile empty
            telpString.isEmpty() -> {
                loginState.value = loginState.value.copy(
                    errorState = LoginErrorState(
                        telpErrorState = emailOrMobileEmptyErrorState
                    )
                )
                false
            }

            //Password Empty
            passwordString.isEmpty() -> {
                loginState.value = loginState.value.copy(
                    errorState = LoginErrorState(
                        passwordErrorState = passwordEmptyErrorState
                    )
                )
                false
            }


            // No errors
            else -> {
                // Set default error state
                loginState.value = loginState.value.copy(errorState = LoginErrorState())
                true
            }
        }
    }

    private fun checkLogin() = viewModelScope.launch {
        repository.auth.signInWithEmailAndPassword("${loginState.value.telp}@gmail.com", loginState.value.password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                viewModelScope.launch{
                val dataUsername = repository.getUserDetailsByUsername(loginState.value.telp.lowercase())

                    LoggedUser.nama = dataUsername.data!!.nama
                    LoggedUser.telp = dataUsername.data.telp
                    LoggedUser.username = dataUsername.data.username
                    LoggedUser.umur = dataUsername.data.umur
                    LoggedUser.gender = dataUsername.data.gender
                    LoggedUser.id = dataUsername.data.id
                    LoggedUser.trust = dataUsername.data.trust
                    LoggedUser.accesslevel = dataUsername.data.access
                    LoggedUser.currentAntrianID = "${dataUsername.data.nama}${dataUsername.data.username}"
                    LoggedUser.selectedPoli = "Umum"


                loginState.value = loginState.value.copy(
                    id = 1,
                    isLoginSuccessful = true,
                    )
                }
            }
            else {
                loginState.value = loginState.value.copy(
                    isLoginIncorrect = true
                )
            }
        }

        /*repository.loginUser(
                loginState.value.telp,
                loginState.value.password
                )
            ){
            val dataUsername = repository.getUserDetailsByUsername(loginState.value.telp)

            LoggedUser.nama = dataUsername.data!!.nama
            LoggedUser.telp = dataUsername.data.telp
            LoggedUser.username = dataUsername.data.username
            LoggedUser.umur = dataUsername.data.umur
            LoggedUser.gender = dataUsername.data.gender
            LoggedUser.id = dataUsername.data.id
            LoggedUser.trust = dataUsername.data.trust
            LoggedUser.accesslevel = dataUsername.data.access
            LoggedUser.currentAntrianID = "${dataUsername.data.nama}${dataUsername.data.username}"

            loginState.value = loginState.value.copy(
                id = 1,
                isLoginSuccessful = true,
            )
        }*/

        /*if (repository.findUsername(loginState.value.telp)) {
            val dataUsername = repository.getUserDetailsByUsername(loginState.value.telp)

            if (loginState.value.password == dataUsername.data!!.password) {
                loginState.value = loginState.value.copy(
                    id = dataUsername.data.id,
                    isLoginSuccessful = true,
                )

                LoggedUser.nama = dataUsername.data.nama
                LoggedUser.telp = dataUsername.data.telp
                LoggedUser.username = dataUsername.data.username
                LoggedUser.umur = dataUsername.data.umur
                LoggedUser.gender = dataUsername.data.gender
                LoggedUser.id = dataUsername.data.id
                LoggedUser.trust = dataUsername.data.trust
                LoggedUser.accesslevel = dataUsername.data.access
                LoggedUser.currentAntrianID = "${dataUsername.data.nama}${dataUsername.data.username}"
            }
        }*/
    }
}