package com.projects.kanade.model

data class User(
    val id: Long,
    var username: String,
    var nama: String,
    var telp: String,
    var password: String,
    var access: Int,
    var antri: AntrianUser,
)

data class AntrianUser(
    var terdaftar : Boolean,
    var nomor : Int,
    var poli : String,
)

data class UserDB (
    val id: Long = 0,
    var username: String = "",
    var nama: String = "",
    var telp: String = "",
    // var password: String = "",
    var access: Int = 0,
    var trust: Int = 0,
    var umur: Int = 0,
    var gender: Int = 0,
)