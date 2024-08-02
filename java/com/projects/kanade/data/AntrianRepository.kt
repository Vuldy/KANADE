package com.projects.kanade.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.projects.kanade.model.JanjiTemu
import com.projects.kanade.model.Resource
import com.projects.kanade.ui.reusables.GetTimeString
import com.projects.kanade.ui.reusables.compareTwoTimes
import com.projects.kanade.ui.reusables.desiredToSession
import com.projects.kanade.ui.reusables.serviceTime
import com.projects.kanade.ui.reusables.timeElapsed
import kotlinx.coroutines.tasks.await

class AntrianRepository (){

    var db : FirebaseFirestore = FirebaseFirestore.getInstance()
    var dbAntrian: CollectionReference = db.collection("Antrian")


    fun addAntrian(
        username: String,
        lastAntrian: Int,
        nama: String,
        trust: Int,
        priority: Int,
        poli: String,
        tanggal: String,
        jamjanji : Int,
        keluhan: String,
        hadir: Boolean,
        waktuhadir: String,
        tipedaftar: Int,
        time: Int,
    ) {

        val janji = JanjiTemu(
            lastAntrian+1, nama,username,trust, priority, poli, tanggal,
            desiredToSession(jamjanji),keluhan, hadir,false, waktuhadir, tipedaftar, time, jamjanji
        )
        val idAntrian = "$nama$username"

        db.collection("Antrian").document(idAntrian).set(janji)
    }

    fun hadirAntrian(
        username: String,
        nama: String,
        waktuhadir: String,
    ){
        val idAntrian = "$nama$username"

        dbAntrian.document(idAntrian).update(
            "waktuhadir", waktuhadir,
            "hadir", true
        )
    }

    suspend fun getAllAntrian(): Resource<List<JanjiTemu>> {
        val result: List<JanjiTemu>
        return try {
            result = db.collection("Antrian").get().await().map {
                it.toObject(JanjiTemu::class.java)
            }.filter{ it.nomorAntrian != 0 }
            Resource.Success(result)
        } catch (e: FirebaseFirestoreException) {
            Resource.Error(e.message.toString())
        }
    }

    suspend fun getAllAntrianPoli(poli: String): Resource<List<JanjiTemu>> {
        val result: List<JanjiTemu>
        return try {
            result = db.collection("Antrian").get().await().map {
                it.toObject(JanjiTemu::class.java)
            }.filter { it.poli == poli }
            Resource.Success(result)
        } catch (e: FirebaseFirestoreException) {
            Resource.Error(e.message.toString())
        }
    }

    suspend fun getAllAntrianPoliTanggal(poli: String, tanggal: String): Resource<List<JanjiTemu>> {
        val result: List<JanjiTemu>
        return try {
            result = db.collection("Antrian").get().await().map {
                it.toObject(JanjiTemu::class.java)
            }.filter { it.poli == poli && it.tanggal == tanggal}
            Resource.Success(result)
        } catch (e: FirebaseFirestoreException) {
            Resource.Error(e.message.toString())
        }
    }

    suspend fun getWaktuDatangPoli(poli: String, tanggal: String, jamjanji: String, expectedtime: Int): List<Int> {
        val antrian = getAllAntrianPoliTanggal(poli, tanggal)

        return antrian.data!!.filter { it.jamjanji == jamjanji && it.expectedtime == expectedtime }.map { it.predictedtime }
    }

    suspend fun getDataAntrianUser(nama: String, username: String): Resource<JanjiTemu> {
        val data = getAllAntrian()
        val result: JanjiTemu? = data.data!!.find { it.namaPasien == nama && it.username == username}
        return if (result!= null) {
            Resource.Success(result)
        } else {
            Resource.Success(JanjiTemu(0,LoggedUser.nama,"Tidak ada data", 0,0,"Tidak ada data", "Tidak ada data", "Tidak ada data",))
            }
        }

    suspend fun getMaxNomorAntrian(poli: String, tanggal: String, expectedtime: Int, jamjanji: String): Int {
        val data = getAllAntrian()
        val dataPoli = data.data!!.filter{it.poli == poli && it.tanggal == tanggal && it.expectedtime == expectedtime && !it.selesai}

        return if (dataPoli.isNotEmpty()){
            val result = dataPoli.maxBy { p -> p.nomorAntrian }

            if (getWaitTimeSelected(poli, tanggal, expectedtime ,result.nomorAntrian, result.predictedtime) > 3600 - result.predictedtime /*result.nomorAntrian == 4*/){
                -1
            }
            else {
                result.nomorAntrian
            }
        } else {
            (expectedtime-1)*10 + 0
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getMaxNomorAntrianOffline(poli: String, tanggal: String, expectedtime: Int, jamjanji: String): List<Int> {
        val data = getAllAntrian()
        val resultList = mutableListOf(expectedtime,(expectedtime)*10)
        val resultOffline = data.data!!.filter{it.poli == poli && it.tanggal == tanggal && it.expectedtime == expectedtime && it.hadir && !it.selesai && it.tipeDaftar == 2}

        if (resultOffline.isNotEmpty()){
            val result = resultOffline.maxBy { p -> p.nomorAntrian }

            val seconds = if (jamjanji == "08.00 - 11.30") {12360} else {6960}

            if (getWaitTime(poli, tanggal,jamjanji ,expectedtime, result.nomorAntrian, result.predictedtime) > seconds - timeElapsed(
                    GetTimeString(), jamjanji) - result.predictedtime ){
                if (jamjanji == "08.00 - 11.30" && (expectedtime == 1 || expectedtime == 2)) {
                    getMaxNomorAntrianOffline(poli, tanggal, expectedtime + 1, "08.00 - 11.30")
                }
                else if (jamjanji == "08.00 - 11.30" && expectedtime == 3){
                    getMaxNomorAntrianOffline(poli, tanggal, 4, "12.30 - 14.30")
                }
                else if (jamjanji == "12.30 - 14.30" && expectedtime == 4){
                    getMaxNomorAntrianOffline(poli, tanggal, 5, "12.30 - 14.30")
                }
                else {
                    resultList[1] = -1
                }
            }
            else {
                resultList[1] = /*resultOffline.minBy { p -> p.nomorAntrian }.nomorAntrian + 3*/result.nomorAntrian
            }
        } else {
            val onlineHadir = data.data!!.filter{ it.poli == poli && it.tanggal == tanggal && it.expectedtime == expectedtime && it.hadir && !it.selesai && it.tipeDaftar == 1}
            if (onlineHadir.isNotEmpty()){
                resultList[1] = onlineHadir.maxBy { p -> p.nomorAntrian }.nomorAntrian + 1
            } else {
                resultList[1] = (expectedtime)*10
            }
        }
        return resultList
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getMaxNomorAntrianTesting(poli: String, tanggal: String, expectedtime: Int, jamjanji: String, currentSimTime: Int): List<Int> {
        val data = getAllAntrian()
        val resultList = mutableListOf(expectedtime, (expectedtime) * 10)
        val resultOffline =
            data.data!!.filter { it.poli == poli && it.tanggal == tanggal && it.expectedtime == expectedtime && it.hadir && !it.selesai && it.tipeDaftar == 2 }

        if (resultOffline.isNotEmpty()) {
            val result = resultOffline.maxBy { p -> p.nomorAntrian }

            val seconds = if (jamjanji == "08.00 - 11.30") {
                12360
            } else {
                6960
            }

            if (getTotalWaitTime(poli, tanggal, jamjanji) + result.predictedtime < seconds) {

                if (getWaitTime(
                        poli,
                        tanggal,
                        jamjanji,
                        expectedtime,
                        result.nomorAntrian,
                        result.predictedtime
                    ) > seconds - currentSimTime * 60 - result.predictedtime
                ) {
                    if (jamjanji == "08.00 - 11.30" && (expectedtime == 1 || expectedtime == 2)) {
                        getMaxNomorAntrianTesting(
                            poli,
                            tanggal,
                            expectedtime + 1,
                            "08.00 - 11.30",
                            currentSimTime
                        )
                    } else if (jamjanji == "08.00 - 11.30" && expectedtime == 3) {
                        getMaxNomorAntrianTesting(poli, tanggal, 4, "12.30 - 14.30", currentSimTime)
                    } else if (jamjanji == "12.30 - 14.30" && expectedtime == 4) {
                        getMaxNomorAntrianTesting(poli, tanggal, 5, "12.30 - 14.30", currentSimTime)
                    } else {
                        resultList[1] = -1
                    }
                } else {
                    resultList[1] = /*resultOffline.minBy { p -> p.nomorAntrian }.nomorAntrian + 3*/
                        result.nomorAntrian
                }} else {
                     resultList[1] = -1
                }
        } else {
            val onlineHadir =
                data.data!!.filter { it.poli == poli && it.tanggal == tanggal && it.expectedtime == expectedtime && it.hadir && !it.selesai && it.tipeDaftar == 1 }
            if (onlineHadir.isNotEmpty()) {
                resultList[1] = onlineHadir.maxBy { p -> p.nomorAntrian }.nomorAntrian + 1
            } else {
                resultList[1] = (expectedtime) * 10
            }
        }
        return resultList
    }

    /*suspend fun getMinNomorAntrianOffline(poli: String, tanggal: String, jamjanji: String, expectedtime: Int): Int {
        val data = getAllAntrian()
        val resultOffline = data.data!!.filter{it.nomorAntrian > 0 && it.poli == poli && it.tanggal == tanggal && it.jamjanji == jamjanji && it.hadir}

        return if (resultOffline.isNotEmpty()){
            val result = resultOffline.minBy { p -> p.nomorAntrian }

            if (getWaitTimeSelected(poli, tanggal, expectedtime,result.nomorAntrian, result.predictedtime) > 3600 - result.predictedtime ){
                -1
            }
            else {
                result.nomorAntrian
            }
        } else {
            0
        }
    }*/

    suspend fun getWaitTime(poli: String, tanggal: String, jamjanji: String, expectedtime: Int, antrian: Int, selfwaittime: Int): Int {
        val data = getAllAntrianPoli(poli)
        val prevdata = data.data!!.filter { it.tanggal == tanggal && it.jamjanji == jamjanji && it.nomorAntrian <= antrian && it.expectedtime <= expectedtime}
        return if (prevdata.isNotEmpty()) {
            prevdata.sumOf { it.predictedtime } - selfwaittime
        } else {
            0
        }
    }

    suspend fun getTotalWaitTime(poli: String, tanggal: String, jamjanji: String ): Int {
        val data = getAllAntrianPoli(poli)
        val prevdata = data.data!!.filter { it.tanggal == tanggal && it.jamjanji == jamjanji }
        return if (prevdata.isNotEmpty()) {
            prevdata.sumOf { it.predictedtime }
        } else {
            0
        }
    }

    suspend fun getWaitTimeSelected(poli: String, tanggal: String, expectedtime: Int, antrian: Int, selfwaittime: Int): Int {
        val data = getAllAntrianPoli(poli)
        val prevdata = data.data!!.filter { it.tanggal == tanggal && it.expectedtime == expectedtime && it.nomorAntrian <= antrian }
        return if (prevdata.isNotEmpty()) {
            prevdata.sumOf { it.predictedtime } - selfwaittime
        } else {
            0
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun determineWaitTime(
        poli: String,
        tanggal: String,
        antrian: Int,
        jamjanji: String,
        expectedtime: Int,
        selfwaittime: Int): String
    {
        val predictFromStart = serviceTime(jamjanji, getWaitTime(poli, tanggal, jamjanji, expectedtime, antrian, selfwaittime))
        var sessionhour = 0
        var sessionminute = 0
        var sessionstring = ""
        when (expectedtime) {
            1 -> {
                sessionhour = 8
                sessionminute = 0
                sessionstring = "08.00 - 09.00"
            }
            2 -> {
                sessionhour = 9
                sessionminute = 0
                sessionstring = "09.00 - 10.00"
            }
            3 -> {
                sessionhour = 10
                sessionminute = 0
                sessionstring = "10.00 - 11.30"
            }
            4 -> {
                sessionhour = 12
                sessionminute = 30
                sessionstring = "12.30 - 13.30"
            }
            5 -> {
                sessionhour = 13
                sessionminute = 30
                sessionstring = "13.30 - 14.30"
            }
            else -> {
                sessionhour = 0
                sessionminute = 0
                sessionstring = ""
            }
        }

        return if (
            compareTwoTimes(
                predictFromStart[0].digitToInt()*10 + predictFromStart[1].digitToInt(),
                predictFromStart[3].digitToInt()*10 + predictFromStart[4].digitToInt(),
                sessionhour, sessionminute,
            )) {
            serviceTime(sessionstring, getWaitTimeSelected(poli, tanggal, expectedtime, antrian, selfwaittime))
        } else {
            predictFromStart
        }
    }


    suspend fun getNomorAntrianwithPriority(poli: String, tanggal: String, jamjanji: String, severity: Int): Int {
        val data = getAllAntrian()
        val dataPoli = data.data!!.filter{
            it.poli == poli && it.tanggal == tanggal && it.jamjanji == jamjanji}

        return if (dataPoli.isNotEmpty()){
            val result = data.data.maxBy { p -> p.prioritas }

            if ( dataPoli.size > 3 ){
                -1
            }
            else {
               dataPoli.size + 1
            }
        } else {
            0
        }
    }

    private fun findIndex(list: List<JanjiTemu>, ): List<Int> {
        val numbers = arrayOf(1, 2, 3, 4, 3, 5)
        val elementToFind = 3

        val index = mutableListOf<Int>()

        for (i in numbers.indices) {
            if (list[i].prioritas == elementToFind) {
                index.add(i)
            }
        }
        return index
    }

    suspend fun getUserIndex(poli: String, username: String, nama: String, tanggal: String, jamjanji: String): Int {
        val data = getAllAntrianPoli(poli)
        val dataPoli = data.data!!.filter{ it.tanggal == tanggal && it.jamjanji == jamjanji && !it.selesai && it.hadir}

        return if (dataPoli.isNotEmpty()){
            dataPoli.sortedWith(compareBy<JanjiTemu> {it.expectedtime}.thenBy {it.nomorAntrian}).indexOfFirst { it.username == username && it.namaPasien == nama } + 1
        } else {
            -1
        }
    }/*{
        val data = getAllAntrianPoli(poli)

        return data.data!!.indexOfFirst { it.username == username && it.namaPasien == nama }
    }*/


    suspend fun getNextAntrian(poli: String, date: String, jamjanji: String): Resource<JanjiTemu> {
        val data = getAllAntrianPoli(poli)
        val checkdata = data.data!!.filter { p -> (p.nomorAntrian > 0 && p.tanggal == date && p.hadir && p.jamjanji == jamjanji)}
        return if (checkdata.isNotEmpty()){
            val result: JanjiTemu? = checkdata.sortedBy { p -> p.tipeDaftar }.minBy { p -> p.nomorAntrian}
            if (result!= null) {
                Resource.Success(result)
            } else {
                Resource.Success(JanjiTemu(0,"Tidak ada pasien", "Tidak ada data",0,0, "Tidak ada data", "Tidak ada data", "Tidak ada data"))
            }
        } else {
            Resource.Success(JanjiTemu(0,"Tidak ada pasien","Tidak ada data", 0,0, "Tidak ada data","Tidak ada data", "Tidak ada data"))
        }
    }

    fun cancelAntrian(idAntrian: String, username: String, trust: Int) {
        dbAntrian.document(idAntrian).update("nomorAntrian", 0, "selesai", true, "tanggal", "")
        db.collection("User").document(username).update("trust", trust - 1)
    }

    fun finishCurrentAntrian(idAntrian: String) {
        dbAntrian.document(idAntrian).update("nomorAntrian", 0, "selesai", true)
    }

    fun absentCurrentAntrian(idAntrian: String, username: String, trust: Int) {
        dbAntrian.document(idAntrian).update("nomorAntrian", 0, "selesai", true,)
        dbAntrian.document(idAntrian).update("nomorAntrian", 0, "selesai", true,)
        db.collection("User").document(username).update("trust", trust - 1)

    }

    companion object {
        @Volatile
        private var instance: AntrianRepository? = null

        fun getInstance(): AntrianRepository =
            instance ?: synchronized(this) {
                AntrianRepository().apply {
                    instance = this
                }
            }
    }
}