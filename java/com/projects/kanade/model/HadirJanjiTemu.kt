package com.projects.kanade.model

data class HadirJanjiTemu(
    var nomorAntrian: Int = 0,
    var namaPasien: String = "",
    var username: String = "",
    var trust: Int = 0,
    var poli: String = "",
    var tanggal: String = "",
    var jamjanji: String = "",
    var keluhan: String = "",
    var selesai: Boolean = false,
    var waktuhadir: String = "",
    var tipeDaftar: String = "",
)
