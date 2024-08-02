package com.projects.kanade.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.projects.kanade.data.AntrianRepository
import com.projects.kanade.data.UserRepository
import com.projects.kanade.ui.screen.home.admin.editakun.AkunTerpilihViewModel
import com.projects.kanade.ui.screen.home.admin.editakun.EditAkunViewModel
import com.projects.kanade.ui.screen.home.admin.editakun.TerpilihEditViewModel
import com.projects.kanade.ui.screen.home.dokter.cekantri.AntrianDokterViewModel
import com.projects.kanade.ui.screen.home.dokter.cekjadwal.JadwalDokterViewModel
import com.projects.kanade.ui.screen.home.pasien.antrian.AntrianViewModel
import com.projects.kanade.ui.screen.home.pasien.daftar.PilihJadwalViewModel
import com.projects.kanade.ui.screen.home.profile.ProfileViewModel
import com.projects.kanade.ui.screen.landing.login.LoginViewModel
import com.projects.kanade.ui.screen.landing.register.RegisterViewModel

class ViewModelFactory(private val repository: UserRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(EditAkunViewModel::class.java)) {
            return EditAkunViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(AkunTerpilihViewModel::class.java)) {
            return AkunTerpilihViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(TerpilihEditViewModel::class.java)) {
            return TerpilihEditViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}