package com.projects.kanade.predict

import java.io.File
import java.io.InputStream

data class Data(
    val gender: Int,
    val age: Int,
    val time: Int,
)

/*fun readCsv(): List<Pair<List<Double>, Double>> {
    var listHasil: List<Pair<List<Double>, Double>> =  mutableListOf()
    csvReader().open("com/projects/kanade/predict/csvdatadummy.csv") {
        readAllAsSequence().forEach { row ->
            listHasil.add
        }
    }
}*/