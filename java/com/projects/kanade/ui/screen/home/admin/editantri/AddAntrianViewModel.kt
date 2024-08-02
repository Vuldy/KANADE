package com.projects.kanade.ui.screen.home.admin.editantri

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projects.kanade.data.AntrianRepository
import com.projects.kanade.data.LoggedUser
import com.projects.kanade.predict.PTTPtest
import com.projects.kanade.ui.reusables.GetDate
import com.projects.kanade.ui.reusables.GetDay
import com.projects.kanade.ui.reusables.GetTimeString
import com.projects.kanade.ui.reusables.compareTime
import com.projects.kanade.ui.reusables.desiredToSession
import com.projects.kanade.ui.reusables.determineTime
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

class AddAntrianViewModel (
    private val repository: AntrianRepository
) : ViewModel() {

    private val _getUser: MutableStateFlow<AddAntrianState> = MutableStateFlow(
        AddAntrianState()
    )
    val getUser: StateFlow<AddAntrianState> = _getUser

    private suspend fun validateInputs(): Boolean {
        val nameString = _getUser.value.nama.trim()
        val umurString = _getUser.value.umur.trim()

        return when {

            nameString.isEmpty() -> {
                _getUser.value = _getUser.value.copy(
                    invalid = true
                )
                false
            }

            umurString.isEmpty() -> {
                _getUser.value = _getUser.value.copy(
                    invalid = true
                )
                false
            }

            else -> {
                true
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun onUiEvent(addAntrianUiEvent: AddAntrianUiEvent) {
        viewModelScope.launch {
            when (addAntrianUiEvent) {
                /*is AddAntrianUiEvent.UsernameChange -> {
                    _getUser.value = _getUser.value.copy(
                        username = addAntrianUiEvent.inputValue
                    )
                }*/
                is AddAntrianUiEvent.NameChange -> {
                    _getUser.value = _getUser.value.copy(
                        nama = addAntrianUiEvent.inputValue
                    )
                }

                is AddAntrianUiEvent.UmurChange -> {
                    _getUser.value = _getUser.value.copy(
                        umur = addAntrianUiEvent.inputValue
                    )
                }

                is AddAntrianUiEvent.KeluhanChange -> {
                    _getUser.value = _getUser.value.copy(
                        keluhan = addAntrianUiEvent.inputValue
                    )
                }

                is AddAntrianUiEvent.Submit -> {
                    if (validateInputs()) {
                    var nomorAntri = repository.getMaxNomorAntrianOffline(
                        _getUser.value.poli,
                        GetDate(),
                        determineTime(),
                        desiredToSession(determineTime())
                    )
                    if (nomorAntri[1] != -1) {
                        _getUser.value = _getUser.value.copy(
                            terdaftar = true
                        )

                        repository.addAntrian(
                            "Admin",
                            nomorAntri[1],
                            _getUser.value.nama,
                            0,
                            0,
                            _getUser.value.poli,
                            GetDate(),
                            nomorAntri[0], //determineTime(),
                            _getUser.value.keluhan,
                            true,
                            GetTimeString(),
                            2,
                            PTTPtest(
                                (GetDate()[3].digitToInt() * 10 + GetDate()[4].digitToInt()).toDouble(),
                                if (GetDay() == "Monday") {
                                    2.0
                                } else if (GetDay() == "Tuesday") {
                                    3.0
                                } else if (GetDay() == "Wednesday") {
                                    4.0
                                } else if (GetDay() == "Thursday") {
                                    5.0
                                } else if (GetDay() == "Friday") {
                                    6.0
                                } else if (GetDay() == "Saturday") {
                                    7.0
                                } else if (GetDay() == "Sunday") {
                                    1.0
                                } else {
                                    0.0
                                },
                                if (GetDay() == "Monday") {
                                    1.0
                                } else if (GetDay() == "Tuesday") {
                                    1.0
                                } else if (GetDay() == "Wednesday") {
                                    1.0
                                } else if (GetDay() == "Thursday") {
                                    1.0
                                } else if (GetDay() == "Friday") {
                                    1.0
                                } else if (GetDay() == "Saturday") {
                                    1.0
                                } else if (GetDay() == "Sunday") {
                                    0.0
                                } else {
                                    0.0
                                },
                                if (nomorAntri[0] == 1 || nomorAntri[0] == 2 || nomorAntri[0] == 3) {
                                    1.0
                                } else {
                                    2.0
                                },
                                LoggedUser.gender,
                            )
                        )
                    } else if (nomorAntri[1] == -1) {
                        //nomorAntri = repository.getMaxNomorAntrianOffline(_getUser.value.poli, GetDate(), determineTime() + 1, desiredToSession(determineTime()))
                        _getUser.value = _getUser.value.copy(
                            penuh = true
                        )
                    }
                }
            }
            }
        }
    }
}