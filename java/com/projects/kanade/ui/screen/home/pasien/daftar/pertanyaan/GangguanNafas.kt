package com.projects.kanade.ui.screen.home.pasien.daftar.pertanyaan

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun GangguanNafasCheck(
    gangguanNafas: () -> Unit,
    tidakGangguan: () -> Unit,
) {
    Column {
        Text(text = "Apakah sedang mengalami gangguan pernafasan?")

        Row {
            Button(onClick = tidakGangguan ) {
                Text(text = "Tidak")
            }
            Button(onClick = gangguanNafas ) {
                Text(text = "Iya")
            }
        }
    }
}