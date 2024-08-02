package com.projects.kanade.ui.screen.home.admin.simulator

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projects.kanade.data.AntrianRepository
import com.projects.kanade.data.LoggedUser
import com.projects.kanade.model.Resource
import com.projects.kanade.predict.PTTPtest
import com.projects.kanade.ui.reusables.GetDate
import com.projects.kanade.ui.reusables.GetTimeString
import com.projects.kanade.ui.reusables.desiredToSession
import com.projects.kanade.ui.reusables.determineTime
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class SimulatorViewModel (
    private val repository: AntrianRepository
) : ViewModel() {

    private val _getAntrian: MutableStateFlow<SimulatorState> = MutableStateFlow(
        SimulatorState()
    )
    val getAntrian: StateFlow<SimulatorState> = _getAntrian
    private val date = GetDate()

    init {
        //getAntrianList()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onUiEvent(simulatorUiEvent: SimulatorUiEvent) {
        viewModelScope.launch {
            when (simulatorUiEvent) {
                is SimulatorUiEvent.Submit -> {
                    startSimulation(19, 5)
                }
                is SimulatorUiEvent.SubmitNoApp -> {
                    simulasiNonAplikasi(24)
                }

                /*is CurAntUiEvent.SubmitTidakDatang -> {
                    repository.deleteCurrentAntrian(getAntrian.value.currentID)
                    repository.cancelAntrian(getAntrian.value.currentID, getAntrian.value.antriSuccess!!.username, getAntrian.value.antriSuccess!!.trust)
                }*/
                else -> {}
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getAntrianList() = viewModelScope.launch {
        repository.getNextAntrian(LoggedUser.selectedPoli, date, desiredToSession(determineTime())).let { result ->
            when (result){
                is Resource.Success -> {
                    _getAntrian.value = SimulatorState(antriSuccess = result.data)
                }
                is Resource.Loading -> {
                    _getAntrian.value = SimulatorState(isLoading = true)
                }
                is Resource.Error -> {
                    _getAntrian.value = SimulatorState(error = result.message)
                }

                else -> {}
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalStdlibApi::class)
    private fun startSimulation(pasien: Int, pasienOffline: Int) = viewModelScope.launch{
        // Spesifikasi testing
        val testPoli = "Umum"
        val testDate = "29/06/2024"
        val testTime = 1 //08.00 - 09.00
        val testSession = "08.00 - 11.30"

        val pasien1 = 6//(0..pasien).random()
        val pasien2 = 6//if ((pasien - pasien1) > 0){(0..(pasien - pasien1)).random()} else {0}
        val pasien3 = 7//if ((pasien - pasien1 - pasien2) > 0) {pasien - pasien1 - pasien2} else {0}

        // Pengecekan
        var listGagal = mutableListOf<Int>()

        // Puskesmas belum buka
        // Add antrian Online
        println("SIMULASI DIMULAI")

        var i = 0
        while ( i < pasien1) {
            val nomorAntri = repository.getMaxNomorAntrian(testPoli, testDate, 1, testSession)
            if (nomorAntri != -1){
            repository.addAntrian(
                "User$i",
                nomorAntri,
                "Nama$i",
                0,
                0,
                testPoli,
                testDate,
                1,
                "",
                false,
                "",
                1,
                PTTPtest(
                    6.0,
                    6.0,
                    1.0,
                    1.0,
                    (0..1).random()
                )
                )
                println("Pasien dengan nama User$i mendaftar ke sesi 1!")
            } else {
                listGagal.add(i)
                println("Pasien $i ditolak karena sesi penuh!")
            }
            i++
            delay(500)
        }

        while ( i < pasien1 + pasien2) {
            val nomorAntri2 = repository.getMaxNomorAntrian(testPoli, testDate, 2, testSession)
            if (nomorAntri2 != -1){
            repository.addAntrian(
                "User$i",
                nomorAntri2,
                "Nama$i",
                0,
                0,
                testPoli,
                testDate,
                2,
                "",
                false,
                "",
                1,
                PTTPtest(6.0,6.0,1.0,1.0,(0..1).random())
                )
                println("Pasien dengan nama User$i mendaftar ke sesi 2!")
            } else {
                listGagal.add(i)
                println("Pasien $i ditolak karena sesi penuh!")
            }
            i++
            delay(500)
        }

        while ( i < pasien1 + pasien2 + pasien3) {
            val nomorAntri3 = repository.getMaxNomorAntrian(testPoli, testDate, 3, testSession)
            if (nomorAntri3 != -1) {
            repository.addAntrian(
                "User$i",
                repository.getMaxNomorAntrian(testPoli, testDate, 3, testSession),
                "Nama$i",
                0,
                0,
                testPoli,
                testDate,
                3,
                "",
                false,
                "",
                1,
                PTTPtest(6.0,6.0,1.0,1.0,(0..1).random())
                )
                println("Pasien dengan nama User$i mendaftar ke sesi 3!")
            } else {
                listGagal.add(i)
                println("Pasien $i ditolak karena sesi penuh!")
            }
            i++
            delay(500)
        }
        delay(1000)

        // Puskesmas sudah buka
        println("Puskesmas sudah dibuka!")
        var ruangtunggu = 0
        var nextAntrian = 0


        // Pasien Online
        var cumulative = 0
        var cumulative2 = 60
        var cumulative3 = 120
        val waktukedatanganOnline = 20 // Menit sebelum janji temu
        val waktuPelayanan1 = repository.getWaktuDatangPoli(testPoli, testDate, testSession, 1)
        val waktuPelayanan2 = repository.getWaktuDatangPoli(testPoli, testDate, testSession, 2)
        val waktuPelayanan3 = repository.getWaktuDatangPoli(testPoli, testDate, testSession, 3)
        var cumulativeTime = mutableListOf<Int>()

        for (pasienSize in 0..< pasien1) {
            if (listGagal.contains(pasienSize)){
                cumulativeTime.add(999)
            } else {
                cumulative += waktuPelayanan1[pasienSize]/60
                cumulativeTime.add(cumulative - waktukedatanganOnline)
            }
        }
        for (pasienSize in 0..< pasien2) {
            if (listGagal.contains(pasienSize + pasien1)){
                cumulativeTime.add(999)
            } else {
                cumulative2 += waktuPelayanan2[pasienSize]/60
                //cumulativeTime.add(cumulative - waktukedatanganOnline)
                cumulativeTime.add(cumulative2 - waktukedatanganOnline)
            }
        }
        for (pasienSize in 0..< pasien3) {
            if (listGagal.contains(pasienSize + pasien1 + pasien2)){
                cumulativeTime.add(999)
            } else {
                cumulative3 += waktuPelayanan3[pasienSize]/60
                //cumulativeTime.add(cumulative - waktukedatanganOnline)
                cumulativeTime.add(cumulative3 - waktukedatanganOnline)
            }
        }

        // Pasien offline yang akan datang
        var kedatanganoffline = mutableListOf<Int>()

        // Waktu pasien offline datang
        for (offline in 0..< pasienOffline ){
            kedatanganoffline.add((0..15).random())
        }
        val listOffline = kedatanganoffline.sorted()

        //Waktu kedatangan
        println(listOffline)
        println(cumulativeTime)

        // Sistem berjalan
        var j = 0
        var k = 0
        var currentUsername = ""
        var currentName = ""
        var nomorAntrian = 0
        var log = mutableListOf<String>()
        var ruangtunggutime = mutableListOf<Int>()

        // Satu hari
        for (minute in 0..209) {
            println("========== Menit $minute ==========")
            val minuteSession = if (minute in 0..59) {1} else if (minute in 60..119) {2} else if (minute in 120..209) {3} else {0}

            if (pasienOffline != 0){
            if (minute == listOffline[j]) {
                for (n in 1..listOffline.count{it == minute}){
                val nomorOffline = repository.getMaxNomorAntrianTesting(testPoli, testDate, minuteSession, testSession, minute)
                if (nomorOffline[1] != -1 && nomorOffline[1] < 40) {
                repository.addAntrian(
                    "Admin$j",
                    nomorOffline[1],
                    "Nama$j",
                    0,
                    0,
                    testPoli,
                    testDate,
                    nomorOffline[0],
                    "",
                    true,
                    "$minute",
                    2,
                    PTTPtest(6.0,6.0,1.0,1.0,(0..1).random())
                    )
                    println("> Admin$j mendaftar")
                    log.add("In;Admin$j;$minute;\n")
                    ruangtunggu += 1
                } else {
                    println("> Admin$j ditolak karena sesi penuh hingga akhir sesi")
                }
                if (j < listOffline.size - 1){
                    j += 1
                }
                }
            }}

            if (pasien > 0){
            if (minute == cumulativeTime[k] || cumulativeTime[k] < 0) {
                repository.hadirAntrian("User$k","Nama$k","$minute")
                println("> Pasien User$k telah hadir!")
                log.add("In;User$k;$minute;\n")
                ruangtunggu += 1
                if (k < cumulativeTime.size - 1) {
                    k += 1
                }
            } else if (cumulativeTime[k] == 999) {
                if (k < cumulativeTime.size - 1) {
                    k += 1
                }
            }}
            if (minute >= nextAntrian){
                if (currentUsername != "" && currentName != "") {
                    //selesai.add(currentUsername)
                    println("> $currentUsername selesai janji temu")
                    repository.finishCurrentAntrian("$currentName$currentUsername")
                }
                val current = repository.getNextAntrian(testPoli, testDate, testSession)
                if (current.data!!.nomorAntrian != 0) {
                    currentUsername = current.data!!.username
                    currentName = current.data.namaPasien
                    nomorAntrian = current.data.nomorAntrian
                    nextAntrian = minute + current.data.predictedtime/60
                    ruangtunggu -= 1
                    println("> Melayani $currentUsername (nomor antrian $nomorAntrian)")
                    log.add("Out;$currentUsername;$minute;\n")
                } else {
                    currentUsername = ""
                    currentName = ""
                    println("Menunggu ada kedatangan...")
                }
            }

            delay(500)
            println(ruangtunggu)
            ruangtunggutime.add(ruangtunggu)

            when (minute) {
                0 -> { println("Memulai sesi 08.00 - 09.00") }
                60 -> {println("Memulai sesi 09.00 - 10.00")}
                120 -> { println("Memulai sesi 10.00 - 11.30")}
            }
        }

        println("Finish!")
        println(log)
        println(ruangtunggutime)
        //println(selesai)
    }


    // Simulasi Tanpa Aplikasi
    @OptIn(ExperimentalStdlibApi::class)
    suspend fun simulasiNonAplikasi(pasienOffline: Int) {
        println("Memulai simulasi tanpa Aplikasi!")
        var kedatanganoffline = mutableListOf<Int>()
        var durasioffline = mutableListOf<Int>()
        var listHadir = mutableListOf<Int>()

        // Puskesmas sudah buka
        println("Puskesmas sudah dibuka!")
        var ruangtunggu = 0
        var nextAntrian = 0

        // Waktu pasien offline datang
        for (offline in 0..< pasienOffline ){
            kedatanganoffline.add((0..209).random())
            durasioffline.add((PTTPtest(6.0,6.0,1.0,1.0,(0..1).random()))/60)
            delay(500)
        }
        val listOffline = kedatanganoffline.sorted()

        println(listOffline)
        println(durasioffline)

        // Sistem berjalan
        var j = 0
        var k = 0
        var nomorAntrian = 0
        var log = mutableListOf<String>()

        val datanglist: MutableList<Int> = MutableList(24) { 0 }
        val pergilist: MutableList<Int> = MutableList(24) { 0 }
        val waktulist: MutableList<Int> = MutableList(24) { 0 }

        var ruangtunggutime = mutableListOf<Int>()

        for (minute in 0..209) {
            println("========== Menit $minute ==========")
            val minuteSession = if (minute in 0..59) {1} else if (minute in 60..119) {2} else if (minute in 120..209) {3} else {0}

            if (minute == listOffline[j]) {
                for (k in 1..listOffline.count{it == minute}){
                    ruangtunggu += 1
                    println("Pasien $j datang")
                    log.add("In;Pasien$j;$minute;\n")
                    datanglist[j] = minute

                    listHadir.add(j)
                    if (j < listOffline.size - 1){
                        j += 1
                }
                }
            }

            if (ruangtunggu != 0){
                if (minute >= nextAntrian && listHadir.contains(nomorAntrian)){
                    nextAntrian = minute + durasioffline[nomorAntrian]
                    println("Pasien $nomorAntrian dilayani")
                    log.add("Out;Pasien$nomorAntrian;$minute;\n")
                    pergilist[nomorAntrian] = minute
                    ruangtunggu -= 1
                    if (nomorAntrian < listOffline.size - 1){
                        //Nomor selanjutnya
                        nomorAntrian += 1
                    }
                    } else {

                    }
                }

            delay(100)
            println("Kondisi ruang tunggu : $ruangtunggu")
            ruangtunggutime.add(ruangtunggu)

            when (minute) {
                0 -> { println("Memulai sesi 08.00 - 09.00") }
                60 -> {println("Memulai sesi 09.00 - 10.00")}
                120 -> { println("Memulai sesi 10.00 - 11.30")}
            }
        }
        println(log)
        println(datanglist)
        println(pergilist)

        for (i in 0..23) {
            waktulist[i] = pergilist[i] - datanglist[i]
        }

        var sum = 0
        var count = 0
        for (i in 0..23) {
            if (waktulist[i] >= 0) {
                sum += waktulist[i]
                count ++
            }
        }

        println(waktulist)
        println(sum.toDouble()/count.toDouble())
        println(ruangtunggutime)
        println("Finish!")
    }
}