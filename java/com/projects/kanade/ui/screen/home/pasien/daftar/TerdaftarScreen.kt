package com.projects.kanade.ui.screen.home.pasien.daftar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.projects.kanade.di.Injection
import com.projects.kanade.ui.AntrianViewModelFactory
import com.projects.kanade.ui.screen.home.pasien.antrian.AntrianViewModel

@Composable
fun TerdaftarScreen(
    toAntrian: () -> Unit,
) {
    Column (
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(8.dp),
            text = "Janji temu sedang diproses...")

        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(8.dp),
            onClick = toAntrian) {
            Text(text = "Lihat status Antrian")
        }
    }
}
