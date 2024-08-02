package com.projects.kanade.ui.screen.home.dokter.cekantri

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.projects.kanade.R
import com.projects.kanade.data.LoggedUser
import com.projects.kanade.di.Injection
import com.projects.kanade.ui.AntrianViewModelFactory
import com.projects.kanade.ui.ViewModelFactory
import com.projects.kanade.ui.common.UiState
import com.projects.kanade.ui.reusables.GetDate
import com.projects.kanade.ui.reusables.GetTimeString
import com.projects.kanade.ui.reusables.desiredToSession
import com.projects.kanade.ui.reusables.determineTime
import com.projects.kanade.ui.screen.home.dokter.cekjadwal.JadwalDokterViewModel
import com.projects.kanade.ui.screen.home.dokter.cekjadwal.ListPasien
import com.projects.kanade.ui.screen.home.pasien.daftar.PilihJadwalUiEvent
import com.projects.kanade.ui.screen.home.pasien.daftar.PoliChoice

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AntrianDokterScreen(
    finishAntrian: () -> Unit,
    changePoli: () -> Unit,
    antrianDViewModel: AntrianDokterViewModel = viewModel(
        factory = AntrianViewModelFactory(
            Injection.provideRepository2()
        )
    ),
    modifier: Modifier = Modifier,
    ) {
    val state = antrianDViewModel.getAntrian.collectAsState()

    val tipe =
        when (state.value.antriSuccess!!.tipeDaftar) {
            1 -> {"Online"}
            2 -> {"Offline"}
            else -> {""}
        }

    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
    ) {
        PoliSelect(
            changePoli = changePoli,
            coloranak = if (LoggedUser.selectedPoli == "Anak") {Color(0xFF0091EA)} else {Color(0xFF90A4AE)},
            colorumum = if (LoggedUser.selectedPoli == "Umum") {Color(0xFF0091EA)} else {Color(0xFF90A4AE)},
            colorlansia = if (LoggedUser.selectedPoli == "Lansia") {Color(0xFF0091EA)} else {Color(0xFF90A4AE)},

        )

        if (LoggedUser.selectedPoli != "") {
        InfoCard(
            poli = LoggedUser.selectedPoli,
            jam = desiredToSession(determineTime()),
            pasien = state.value.antriSuccess!!.namaPasien,
            tanggal = GetDate(),
            hadir = if (state.value.antriSuccess!!.hadir) {"Hadir"} else {"Tidak Hadir"},
            keluhan = state.value.antriSuccess!!.keluhan,
            jenis = tipe,
            waktuhadir = state.value.antriSuccess!!.waktuhadir,
            nomor = state.value.antriSuccess!!.nomorAntrian,
            finishAntrian = {
                antrianDViewModel.onUiEvent(curAntUiEvent = CurAntUiEvent.Submit)

                finishAntrian.invoke()
            },
            cancelAntrian = {
                antrianDViewModel.onUiEvent(curAntUiEvent = CurAntUiEvent.SubmitTidakDatang)

                finishAntrian.invoke()
            },
            trust = state.value.antriSuccess!!.trust
        )
        } else {
            Text(text = "Silahkan pilih poli untuk melihat pasien selanjutnya!",
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,)
        }
    }
}

@Composable
fun InfoCard(
    poli: String,
    jam: String,
    pasien: String,
    nomor: Int,
    tanggal: String,
    hadir: String,
    jenis: String,
    waktuhadir: String,
    keluhan: String,
    finishAntrian: () -> Unit,
    cancelAntrian: () -> Unit,
    trust: Int,
) {
    ElevatedCard(
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 8.dp)
            .fillMaxWidth()
            .fillMaxHeight()

    ) {
        Column (Modifier.fillMaxHeight()){
            if (pasien != "Tidak ada pasien") {

                Text(
                    text = "Poli $poli - $tanggal",
                    modifier = Modifier.padding(8.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                )


                Text(
                    text = "Sesi $jam",
                    modifier = Modifier.padding(8.dp),
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "Nomor Antrian",
                    modifier = Modifier.padding(8.dp),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                )

                Text(
                    text = "$nomor",
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                )

                Row {
                    Text(
                        text = "Nama pasien",
                        modifier = Modifier.padding(8.dp),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = pasien,
                        modifier = Modifier.padding(8.dp),
                        textAlign = TextAlign.Center,
                    )
                }

                Row {
                    Text(
                        text = "Tingkat Kepercayaan",
                        modifier = Modifier.padding(8.dp),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = "$trust",
                        modifier = Modifier.padding(8.dp),
                        textAlign = TextAlign.Center,
                    )
                }

                Row {
                    Text(
                        text = "Hadir",
                        modifier = Modifier.padding(8.dp),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = hadir,
                        modifier = Modifier.padding(8.dp),
                        textAlign = TextAlign.Center,
                    )
                }

                Row {
                    Text(
                        text = "Waktu hadir pasien",
                        modifier = Modifier.padding(8.dp),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = waktuhadir,
                        modifier = Modifier.padding(8.dp),
                        textAlign = TextAlign.Center,
                    )
                }

                Row {
                    Text(
                        text = "Tipe Pendaftaran",
                        modifier = Modifier.padding(8.dp),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = jenis,
                        modifier = Modifier.padding(8.dp),
                        textAlign = TextAlign.Center,
                    )
                }

                Text(
                    text = "Keluhan",
                    modifier = Modifier.padding(8.dp),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                )

                Text(
                    text = keluhan,
                    modifier = Modifier.padding(8.dp),
                    textAlign = TextAlign.Center,
                )

                Button(
                    onClick = {if (pasien != "Tidak ada pasien") {cancelAntrian.invoke()}},
                    modifier = Modifier
                        .height(height = 50.dp)
                        .fillMaxWidth()
                        .padding(8.dp)
                        .align(Alignment.CenterHorizontally),
                ) {
                    Text(text = "Pasien Tidak Datang")
                }

                Button(
                    onClick = {if (pasien != "Tidak ada pasien") {finishAntrian.invoke()}},
                    modifier = Modifier
                        .height(height = 50.dp)
                        .fillMaxWidth()
                        .padding(8.dp)
                        .align(Alignment.CenterHorizontally),
                ) {
                    Text(text = "Selesaikan Janji Temu")
                }
            }

            else {
                Text(
                    text = "Belum ada pasien yang hadir di Puskesmas pada Poli $poli pada : \nTanggal $tanggal \nJam $jam",
                    modifier = Modifier.padding(8.dp),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                )

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = {if (pasien != "Tidak ada pasien") {cancelAntrian.invoke()}},
                    modifier = Modifier
                        .height(height = 50.dp)
                        .fillMaxWidth()
                        .padding(8.dp)
                        .align(Alignment.CenterHorizontally),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF90A4AE))
                ) {
                    Text(text = "Pasien Tidak Datang")
                }

                Button(
                    onClick = {if (pasien != "Tidak ada pasien") {finishAntrian.invoke()}},
                    modifier = Modifier
                        .height(height = 50.dp)
                        .fillMaxWidth()
                        .padding(8.dp)
                        .align(Alignment.CenterHorizontally),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF90A4AE))
                ) {
                    Text(text = "Selesaikan Janji Temu")
                }
            }
        }
    }
}

@Composable
fun DiagnoseCard(
    finishAntrian: () -> Unit,
    modifier: Modifier = Modifier,

) {
    ElevatedCard(
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .padding(vertical = 16.dp, horizontal = 16.dp)
            .fillMaxWidth()
            .fillMaxHeight()
            .navigationBarsPadding()

    ) {
        Column {
            Text(
                text = "HASIL DIAGNOSIS",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp),
            )

        }
        Row (
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ){
            Button(
                onClick = finishAntrian,
                modifier = Modifier
                    .size(width = 240.dp, height = 40.dp)
            ) {
                Text(text = "Pasien Tidak Datang")
            }

            Button(
                onClick = finishAntrian,
                modifier = Modifier
                    .size(width = 240.dp, height = 40.dp)
            ) {
                Text(text = "Selesaikan Janji Temu")
            }

        }
    }
}

@Composable
fun Diagnosis() {
    var text by remember { mutableStateOf("") }

    TextField(
        value = text,
        onValueChange = { text = it },
        label = { },
        maxLines = 6,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .fillMaxWidth()
    )
}

@Composable
fun PoliSelect(
    colorumum: Color,
    coloranak: Color,
    colorlansia: Color,
    changePoli: () -> Unit,
) {
    var selectedPoli by remember { mutableStateOf("") }

    Column {
        Text(
            text = "Pilih Poli",
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            )

        Row {
            Button(
                modifier = Modifier
                    .weight(0.33f)
                    .size(width = 80.dp, height = 40.dp),
                onClick = {
                    LoggedUser.selectedPoli = "Umum"
                    changePoli.invoke()
                },
                colors = ButtonDefaults.buttonColors(containerColor = colorumum),
            ) {
                Text(
                    text = "Umum",
                    fontSize = 12.sp)
            }

            Button(
                modifier = Modifier
                    .weight(0.33f)
                    .size(width = 80.dp, height = 40.dp),
                onClick = {
                    LoggedUser.selectedPoli = "Anak"
                    changePoli.invoke()
                },
                colors = ButtonDefaults.buttonColors(containerColor = coloranak),
            ) {
                Text(
                    text = "Anak",
                    fontSize = 12.sp)
            }

            Button(
                modifier = Modifier
                    .weight(0.33f)
                    .size(width = 80.dp, height = 40.dp),
                onClick = {
                    LoggedUser.selectedPoli = "Lansia"
                    changePoli.invoke()
                },
                colors = ButtonDefaults.buttonColors(containerColor = colorlansia),
            ) {
                Text(text = "Lansia",
                    fontSize = 12.sp)
            }
        }
    }
}

@Preview()
@Composable
fun AntrianDokterPrev() {
    //AntrianDokterScreen({}, {})
}