package com.projects.kanade.ui.screen.home.admin.simulator

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.projects.kanade.di.Injection
import com.projects.kanade.ui.AntrianViewModelFactory
import com.projects.kanade.ui.screen.home.admin.simulator.SimulatorViewModel
import com.projects.kanade.ui.screen.home.dokter.cekantri.AntrianDokterViewModel
import com.projects.kanade.ui.screen.home.dokter.cekantri.CurAntUiEvent

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SimulatorScreen(
    simulatorViewModel: SimulatorViewModel = viewModel(
        factory = AntrianViewModelFactory(
            Injection.provideRepository2()
        )
    ),
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Button(onClick = {
            simulatorViewModel.onUiEvent(simulatorUiEvent = SimulatorUiEvent.Submit)
            
        }) {
            Text(text = "Mulai Simulasi!")
        }

        Button(onClick = {
            simulatorViewModel.onUiEvent(simulatorUiEvent = SimulatorUiEvent.SubmitNoApp)

        }) {
            Text(text = "Simulasi tanpa aplikasi")
        }
    }
}