package com.projects.kanade.ui.screen.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Signup : Screen("signup")

    object Antri : Screen("antri")
    object Daftar : Screen("daftar")
    object Jadwal : Screen("jadwal")
    object Gangguan: Screen("gangguan")
    object Pertanyaan : Screen("pertanyaan")
    object Proses : Screen("proses")
    object Rekam : Screen("rekam")
    object InfoDokter : Screen("infodokter")
    object InfoAdmin : Screen("infoadmin")
    object Profile : Screen("profile")

    object AntriDokter : Screen("antridokter")
    object JadwalDokter : Screen("jadwaldokter")

    object AntriAdmin : Screen("antriadmin")
    object JadwalAdmin : Screen("jadwaladmin")
    object AkunAdmin : Screen("akunadmin")
    object EditAkun : Screen("editakun")
    object AkunTerpilih : Screen("akunterpilih")
    object Simulator : Screen("simulator")

}
