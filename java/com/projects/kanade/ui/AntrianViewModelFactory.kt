package com.projects.kanade.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.projects.kanade.data.AntrianRepository
import com.projects.kanade.data.UserRepository
import com.projects.kanade.ui.screen.home.admin.editantri.AddAntrianViewModel
import com.projects.kanade.ui.screen.home.admin.editjadwal.EditJadwalViewModel
import com.projects.kanade.ui.screen.home.admin.simulator.SimulatorViewModel
import com.projects.kanade.ui.screen.home.dokter.cekantri.AntrianDokterViewModel
import com.projects.kanade.ui.screen.home.dokter.cekjadwal.JadwalDokterViewModel
import com.projects.kanade.ui.screen.home.pasien.antrian.AntrianViewModel
import com.projects.kanade.ui.screen.home.pasien.daftar.DaftarViewModel
import com.projects.kanade.ui.screen.home.pasien.daftar.PilihJadwalViewModel
import com.projects.kanade.ui.screen.home.profile.ProfileViewModel
import com.projects.kanade.ui.screen.landing.login.LoginViewModel
import com.projects.kanade.ui.screen.landing.register.RegisterViewModel

class AntrianViewModelFactory(private val repository: AntrianRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @RequiresApi(Build.VERSION_CODES.O)
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PilihJadwalViewModel::class.java)) {
            return PilihJadwalViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(AntrianViewModel::class.java)) {
            return AntrianViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(AntrianDokterViewModel::class.java)) {
            return AntrianDokterViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DaftarViewModel::class.java)) {
            return DaftarViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(JadwalDokterViewModel::class.java)) {
            return JadwalDokterViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(EditJadwalViewModel::class.java)) {
            return EditJadwalViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(AddAntrianViewModel::class.java)) {
            return AddAntrianViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(SimulatorViewModel::class.java)) {
            return SimulatorViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}