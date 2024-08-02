package com.projects.kanade.model

data class JanjiTemu(
    var nomorAntrian: Int = 0,
    var namaPasien: String = "",
    var username: String = "",
    var trust: Int = 0,
    var prioritas: Int = 0,

    // Spesifikasi antrian
    var poli: String = "",
    var tanggal: String = "",
    var jamjanji: String = "",
    var keluhan: String = "",

    // Kehadiran
    var hadir: Boolean = false,
    var selesai: Boolean = false,
    var waktuhadir: String = "",
    var tipeDaftar: Int = 1, // Note to self : 1 Online, 2 Offline

    var predictedtime: Int = 0,
    var expectedtime: Int = 0,
)
