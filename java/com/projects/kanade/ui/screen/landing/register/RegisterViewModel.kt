package com.projects.kanade.ui.screen.landing.register

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projects.kanade.data.LoggedUser
import com.projects.kanade.data.UserRepository
import com.projects.kanade.ui.common.ErrorState
import kotlinx.coroutines.launch

class RegisterViewModel (
    private val repository: UserRepository
) : ViewModel() {
    var registrationState = mutableStateOf(RegisterState())
        private set

    /**
     * Function called on any login event [RegistrationUiEvent]
     */

    fun onUiEvent(registrationUiEvent: RegisterUiEvent) {
        when (registrationUiEvent) {

            is RegisterUiEvent.NameChanged -> {
                registrationState.value = registrationState.value.copy(
                    name = registrationUiEvent.inputValue,
                    errorState = registrationState.value.errorState.copy(
                        nameErrorState = if (registrationUiEvent.inputValue.trim().isEmpty()) {
                            // Email id empty state
                            nameEmptyErrorState
                        } else {
                            // Valid state
                            ErrorState()
                        }

                    )
                )
            }

            is RegisterUiEvent.UmurChanged -> {
                registrationState.value = registrationState.value.copy(
                    umur = registrationUiEvent.inputValue,
                    errorState = registrationState.value.errorState.copy(
                        nameErrorState = if (registrationUiEvent.inputValue.trim().isEmpty()) {
                            // Email id empty state
                            nameEmptyErrorState
                        } else {
                            // Valid state
                            ErrorState()
                        }

                    )
                )
            }

            // Email id changed event
            is RegisterUiEvent.UsernameChanged -> {
                registrationState.value = registrationState.value.copy(
                    username = registrationUiEvent.inputValue,
                    errorState = registrationState.value.errorState.copy(
                        usernameErrorState = if (registrationUiEvent.inputValue.trim().isEmpty()) {
                            // Email id empty state
                            usernameEmptyErrorState
                        } else {
                            // Valid state
                            ErrorState()
                        }

                    )
                )
            }

            // Mobile Number changed event
            is RegisterUiEvent.TelpChanged -> {
                registrationState.value = registrationState.value.copy(
                    telp = registrationUiEvent.inputValue,
                    errorState = registrationState.value.errorState.copy(
                        telpErrorState = if (registrationUiEvent.inputValue.trim()
                                .isEmpty()
                        ) {
                            // Mobile Number Empty state
                            telpEmptyErrorState
                        } else {
                            // Valid state
                            ErrorState()
                        }

                    )
                )
            }

            // Password changed event
            is RegisterUiEvent.PasswordChanged -> {
                registrationState.value = registrationState.value.copy(
                    password = registrationUiEvent.inputValue,
                    errorState = registrationState.value.errorState.copy(
                        passwordErrorState = if (registrationUiEvent.inputValue.trim().isEmpty()) {
                            // Password Empty state
                            passwordEmptyErrorState
                        } else {
                            // Valid state
                            ErrorState()
                        }

                    )
                )
            }

            // Confirm Password changed event
            is RegisterUiEvent.ConfirmPasswordChanged -> {
                registrationState.value = registrationState.value.copy(
                    confirmPassword = registrationUiEvent.inputValue,
                    errorState = registrationState.value.errorState.copy(
                        confirmPasswordErrorState = when {

                            // Empty state of confirm password
                            registrationUiEvent.inputValue.trim().isEmpty() -> {
                                confirmPasswordEmptyErrorState
                            }

                            // Password is different than the confirm password
                            registrationState.value.password.trim() != registrationUiEvent.inputValue -> {
                                passwordMismatchErrorState
                            }

                            // Valid state
                            else -> ErrorState()
                        }
                    )
                )
            }
            // Submit Registration event
            is RegisterUiEvent.Submit -> viewModelScope.launch{
                val inputsValidated = validateInputs()
                if (inputsValidated) {
                    registerUser()
                } else {
                    registrationState.value = registrationState.value.copy(
                        isRegistrationInvalid = true,
                        isUsernameValid = false,
                    )
                }
            }
        }
    }

    /**
     * Function to validate inputs
     * Ideally it should be on domain layer (usecase)
     * @return true -> inputs are valid
     * @return false -> inputs are invalid
     */
    private suspend fun validateInputs(): Boolean {
        val nameString = registrationState.value.name.trim()
        val usernameString = registrationState.value.username.trim()
        val telpString = registrationState.value.telp.trim()
        val umurString = registrationState.value.umur.trim()
        val passwordString = registrationState.value.password.trim()
        val confirmPasswordString = registrationState.value.confirmPassword.trim()

        return when {

            nameString.isEmpty() -> {
                registrationState.value = registrationState.value.copy(
                    errorState = RegisterErrorState(
                        usernameErrorState = nameEmptyErrorState
                    )
                )
                false
            }

            // Email empty
            usernameString.isEmpty() -> {
                registrationState.value = registrationState.value.copy(
                    errorState = RegisterErrorState(
                        usernameErrorState = usernameEmptyErrorState
                    ),
                )
                false
            }

            repository.findUsername(usernameString.lowercase())-> {
                registrationState.value = registrationState.value.copy(
                    errorState = RegisterErrorState(
                        usernameErrorState = usernameEmptyErrorState
                    ),
                    isUsernameValid = false
                )
                false
            }

            // Umur
            umurString.isEmpty() -> {
                registrationState.value = registrationState.value.copy(
                    errorState = RegisterErrorState(
                        umurErrorState = telpEmptyErrorState
                    )
                )
                false
            }

            //Mobile Number Empty
            telpString.isEmpty() -> {
                registrationState.value = registrationState.value.copy(
                    errorState = RegisterErrorState(
                        telpErrorState = telpEmptyErrorState
                    )
                )
                false
            }

            //Password Empty
            passwordString.isEmpty() -> {
                registrationState.value = registrationState.value.copy(
                    errorState = RegisterErrorState(
                        passwordErrorState = passwordEmptyErrorState
                    ),
                    isPasswordValid = false
                )
                false
            }

            passwordString.length < 6 -> {
                registrationState.value = registrationState.value.copy(
                    errorState = RegisterErrorState(
                        passwordErrorState = passwordEmptyErrorState
                    ),
                    isPasswordValid = false
                )
                false
            }

            //Confirm Password Empty
            confirmPasswordString.isEmpty() -> {
                registrationState.value = registrationState.value.copy(
                    errorState = RegisterErrorState(
                        confirmPasswordErrorState = confirmPasswordEmptyErrorState
                    )
                )
                false
            }

            // Password and Confirm Password are different
            passwordString != confirmPasswordString -> {
                registrationState.value = registrationState.value.copy(
                    errorState = RegisterErrorState(
                        confirmPasswordErrorState = passwordMismatchErrorState
                    )
                )
                false
            }

            // No errors
            else -> {
                // Set default error state
                registrationState.value =
                    registrationState.value.copy(errorState = RegisterErrorState(), isUsernameValid = true, isPasswordValid = true)
                true
            }
        }
    }

    private fun registerUser() = viewModelScope.launch {
        // TODO Trigger registration in authentication flow
        repository.registerUser(
            registrationState.value.username,
            registrationState.value.name,
            registrationState.value.telp,
            registrationState.value.password,
            1,
            registrationState.value.umur.toInt(),
            LoggedUser.gender,
        )

        /*repository.addUser(
            registrationState.value.username,
            registrationState.value.name,
            registrationState.value.telp,
            registrationState.value.password,
            registrationState.value.umur.toInt(),
            LoggedUser.gender,
        )*/

        registrationState.value =
            registrationState.value.copy(isRegistrationSuccessful = true)
    }
}