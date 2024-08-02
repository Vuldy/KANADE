package com.projects.kanade.ui.screen.home.pasien.antrian

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.projects.kanade.di.Injection
import com.projects.kanade.ui.AntrianViewModelFactory
import com.projects.kanade.ui.reusables.GetDate
import com.projects.kanade.ui.reusables.PopupBox
import com.projects.kanade.ui.reusables.compareTime
import com.projects.kanade.ui.screen.home.dokter.cekantri.CurAntUiEvent
import com.projects.kanade.ui.theme.KanadeTheme

@Composable
fun DoctorCard(
    poli: String,
    tanggal: String,
    jam: String,
    waktudatang: String,
    modifier: Modifier = Modifier,
) {
    ElevatedCard(
        shape = MaterialTheme.shapes.medium,
        /*colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ),*/
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .fillMaxWidth()
            .fillMaxHeight()

    ) {
        Column (modifier = Modifier.padding(16.dp)){
            Text(
                text = "Poli $poli",
                modifier = Modifier.padding(8.dp),
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )
            Row {
                Text(
                    text = "Tanggal",
                    modifier = Modifier.padding(8.dp),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = tanggal,
                    modifier = Modifier.padding(8.dp),
                    textAlign = TextAlign.Center,
                )
            }

            Row {
                Text(
                    text = "Jam dipilih",
                    modifier = Modifier.padding(8.dp),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = jam,
                    modifier = Modifier.padding(8.dp),
                    textAlign = TextAlign.Center,
                )
            }

            Row {
                Text(
                    text = "Waktu datang \nyang diinginkan",
                    modifier = Modifier.padding(8.dp),
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = waktudatang,
                    modifier = Modifier.padding(8.dp),
                    textAlign = TextAlign.Center,
                )
            }
        }

    }
}

@Composable
fun AntrianCard(
    nomorAntri : Int,
    antrianselanjut: String,
    jamdatang: String,
    kehadiran: String,
    hadir: Boolean,
    waktutunggu: String,
    waktudatang: String,
    colors: Color,
    modifier: Modifier = Modifier,
    showPopup: () -> Unit,
    hadirPuskesmas: () -> Unit,
) {
    ElevatedCard(
        shape = MaterialTheme.shapes.medium,
        /*colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            ),*/
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .padding(vertical = 16.dp, horizontal = 16.dp)
            .fillMaxWidth()
            .fillMaxHeight()

    ) {
        Column (modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)){
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Nomor Antrian",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 8.dp),
            )

            Text(
                text = "Kedatangan $waktudatang",
                modifier = Modifier.padding(8.dp),
            )

            Text(
                text = "$nomorAntri",
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
            )

            Row {
                Text(
                    text = "Prediksi dipanggil",
                    modifier = Modifier.padding(8.dp),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.weight(1f))
            }

            Text(
                text = waktutunggu,
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally),
                fontSize = 36.sp,
                textAlign = TextAlign.Center,
            )

            Row {
                Text(
                    text = "Pasien menunggu",
                    modifier = Modifier.padding(8.dp),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = antrianselanjut,
                    modifier = Modifier.padding(8.dp),
                    textAlign = TextAlign.Center,
                )
            }

            Row {
                Text(
                    text = "Kehadiran",
                    modifier = Modifier.padding(8.dp),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = kehadiran,
                    modifier = Modifier.padding(8.dp),
                    textAlign = TextAlign.Center,
                )
            }

            Row {
                Text(
                    text = "Jam datang",
                    modifier = Modifier.padding(8.dp),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = jamdatang,
                    modifier = Modifier.padding(8.dp),
                    textAlign = TextAlign.Center,
                )
            }

            Column {
                Button(
                    onClick = showPopup,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                ) {
                    Text(text = "Batalkan Janji Temu")
                }

                if (!hadir) {
                    ElevatedButton(
                        onClick = { if (!hadir) {hadirPuskesmas.invoke()} else { } },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = colors),
                    ) {
                        Text(text = "Sudah hadir di Puskesmas")
                    }
                } else {
                    Button(
                        onClick = { if (!hadir) {hadirPuskesmas.invoke()} else { } },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = colors),
                    ) {
                        Text(text = "Sudah hadir di Puskesmas")
                    }
                }

            }
        }

    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AntrianScreen(
    nama: String,
    modifier: Modifier = Modifier,
    viewModel: AntrianViewModel = viewModel(
        factory = AntrianViewModelFactory(
            Injection.provideRepository2()
        )
    ),
    refreshPage: () -> Unit,
    kehadiranDiterima: () -> Unit,
    tidakCancel: () -> Unit,
) {
    val state = viewModel.getAntrian.collectAsState()
    var showPopup by remember { mutableStateOf(false) }

    if (state.value.antriSuccess!!.nomorAntrian != 0) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        )   {
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Antrian",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(4.dp),
            )

            AntrianCard(
                nomorAntri = state.value.antriSuccess!!.nomorAntrian,
                kehadiran = if (state.value.antriSuccess!!.hadir) {"Hadir"} else {"-"},
                jamdatang = state.value.antriSuccess!!.waktuhadir,
                antrianselanjut = if (state.value.urutan != -1) {"${state.value.urutan}"} else {"Belum datang"},//state.value.antrianSekarang,
                waktutunggu = state.value.waktutunggu,
                showPopup = {if (!state.value.antriSuccess!!.hadir) { showPopup =  true }},
                hadirPuskesmas = {
                    viewModel.onUiEvent(antrianUiEvent = AntrianUiEvent.Hadir)

                    kehadiranDiterima.invoke()

                    refreshPage.invoke()
                },
                hadir = state.value.antriSuccess!!.hadir,
                colors = if (state.value.antriSuccess!!.hadir) {
                    Color(0xFF90A4AE)} else {
                    Color(0xFF0091EA)
                },
                waktudatang = when (state.value.antriSuccess!!.expectedtime){
                    1-> { "08.00 - 09.00" }
                    2-> { "09.00 - 10.00"}
                    3-> { "10.00 - 11.30"}
                    4-> { "12.30 - 13.30"}
                    5-> { "13.30 - 14.30"}
                    else -> { "" }
                }
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Keterangan",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                //modifier = Modifier.padding(4.dp),
            )

            DoctorCard(
                poli = state.value.antriSuccess!!.poli,
                tanggal = state.value.antriSuccess!!.tanggal,
                jam = state.value.antriSuccess!!.jamjanji,
                waktudatang = when (state.value.antriSuccess!!.expectedtime){
                    1-> { "08.00 - 09.00" }
                    2-> { "09.00 - 10.00"}
                    3-> { "10.00 - 11.30"}
                    4-> { "12.30 - 13.30"}
                    5-> { "13.30 - 14.30"}
                    else -> { "" }
                }
            )
        }
    }
    else {
        Column (
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Icon(imageVector = Icons.Default.Article, contentDescription = "", modifier = Modifier.padding(vertical = 16.dp))

            Text(text = "Silahkan daftar janji temu\ndi halaman Daftar!",
                textAlign = TextAlign.Center)
        }
    }

    PopupBox(
        popupWidth = 300F,
        popupHeight = 200F,
        showPopup = showPopup,
        onClickOutside = { showPopup = false },
        content = {
            Column {
                Text("Pembatalan hanya bisa dilakukan paling lambat 1 jam sebelum waktu janji temu", textAlign = TextAlign.Center)
                
                Column {
                    Button(onClick = { showPopup = false }, modifier = Modifier.fillMaxWidth()) {
                        Text(text = "Kembali")
                    }
                    
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            if (GetDate() != state.value.antriSuccess!!.tanggal || compareTime(
                                    state.value.antriSuccess!!.jamjanji[0].digitToInt()*10 + state.value.antriSuccess!!.jamjanji[1].digitToInt() - 1,
                                    state.value.antriSuccess!!.jamjanji[3].digitToInt()*10 + state.value.antriSuccess!!.jamjanji[4].digitToInt()
                                )
                            ) {
                                viewModel.onUiEvent(antrianUiEvent = AntrianUiEvent.Submit)

                                refreshPage.invoke()
                            } else {
                                tidakCancel.invoke()
                            }

                        }
                    ) {
                        Text(text = "Lanjutkan pembatalan")
                    }
                }
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun PrevAntrian() {
    KanadeTheme {
        //AntrianScreen("Josh")
    }
}