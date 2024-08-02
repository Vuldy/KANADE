package com.projects.kanade.ui.screen.home.pasien.daftar

import PilihJadwalState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projects.kanade.data.AntrianRepository
import com.projects.kanade.data.LoggedUser
import com.projects.kanade.data.UserRepository
import com.projects.kanade.model.JanjiTemu
import com.projects.kanade.model.User
import com.projects.kanade.predict.PTTPtest
import com.projects.kanade.ui.common.ErrorState
import com.projects.kanade.ui.common.UiState
import com.projects.kanade.ui.reusables.GetDay
import com.projects.kanade.ui.reusables.desiredToSession
import com.projects.kanade.ui.screen.landing.login.LoginState
import com.projects.kanade.ui.screen.landing.login.LoginUiEvent
import com.projects.kanade.ui.screen.landing.login.emailOrMobileEmptyErrorState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PilihJadwalViewModel (
    private val repository: AntrianRepository
) : ViewModel() {

    var jadwalState = mutableStateOf(PilihJadwalState())
        private set

    fun onUiEvent(
        pilihUiEvent: PilihJadwalUiEvent,
        invalid: () -> Unit,
    ){
        viewModelScope.launch {
            when (pilihUiEvent) {
                is PilihJadwalUiEvent.Submit -> {
                    val lastAntrian = repository
                        .getMaxNomorAntrian(
                            LoggedUser.selectedPoli,
                            jadwalState.value.tanggal,
                            jadwalState.value.waktu,
                            desiredToSession(jadwalState.value.waktu))

                    if (lastAntrian == -1) {
                        invalid.invoke()
                    }

                    else {
                        jadwalState.value = jadwalState.value.copy(
                            valid = true
                        )

                        repository.addAntrian(
                            LoggedUser.username,
                            lastAntrian,
                            LoggedUser.nama,
                            LoggedUser.trust,
                            LoggedUser.severity,
                            LoggedUser.selectedPoli,
                            jadwalState.value.tanggal,
                            jadwalState.value.waktu,
                            jadwalState.value.keluhan,
                            false,
                            "",
                            1,
                            PTTPtest(
                                (jadwalState.value.tanggal[3].digitToInt()*10 + jadwalState.value.tanggal[4].digitToInt()).toDouble(),
                                if (jadwalState.value.hari == "Monday") {2.0}
                                else if (jadwalState.value.hari == "Tuesday") {3.0}
                                else if (jadwalState.value.hari == "Wednesday") {4.0}
                                else if (jadwalState.value.hari == "Thursday") {5.0}
                                else if (jadwalState.value.hari == "Friday") {6.0}
                                else if (jadwalState.value.hari == "Saturday") {7.0}
                                else if (jadwalState.value.hari == "Sunday") {1.0}
                                else {0.0},
                                if (GetDay() == "Monday") {2.0}
                                else if (jadwalState.value.hari == "Tuesday") {3.0}
                                else if (jadwalState.value.hari == "Wednesday") {4.0}
                                else if (jadwalState.value.hari == "Thursday") {5.0}
                                else if (jadwalState.value.hari == "Friday") {6.0}
                                else if (jadwalState.value.hari == "Saturday") {7.0}
                                else if (jadwalState.value.hari == "Sunday") {1.0}
                                else {0.0},
                                if (jadwalState.value.waktu == 1 || jadwalState.value.waktu == 2 || jadwalState.value.waktu == 3 ) {
                                    1.0
                                } else {
                                    2.0
                                },
                                LoggedUser.gender)
                        )
                    }
                }

                is PilihJadwalUiEvent.KeluhanChange -> {
                    jadwalState.value = jadwalState.value.copy(
                        keluhan = pilihUiEvent.inputValue,
                    )
                }

                else -> {}
            }
        }
    }
}