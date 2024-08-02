package com.projects.kanade.ui.screen.home.admin.editjadwal

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.projects.kanade.data.LoggedUser
import com.projects.kanade.di.Injection
import com.projects.kanade.model.JanjiTemu
import com.projects.kanade.ui.AntrianViewModelFactory
import com.projects.kanade.ui.reusables.GetDate
import com.projects.kanade.ui.reusables.PopupBox
import com.projects.kanade.ui.screen.home.dokter.cekantri.CurAntUiEvent
import com.projects.kanade.ui.screen.home.dokter.cekantri.PoliSelect
import com.projects.kanade.ui.screen.home.dokter.cekjadwal.CekJadwalState


@Composable
fun EditJadwalScreen(
    modifier: Modifier = Modifier,
    viewModel: EditJadwalViewModel = viewModel(
        factory = AntrianViewModelFactory(
            Injection.provideRepository2()
        )
    ),
    pilihTanggal: () -> Unit,
    changePoli: () -> Unit,
) {
    val state = viewModel.getAntrian.collectAsState()
    var showPopup by remember { mutableStateOf(false) }

    Column {
        PoliSelect(
            coloranak = if (LoggedUser.selectedPoli == "Anak") { Color(0xFF0091EA)} else { Color(0xFF90A4AE)},
            colorumum = if (LoggedUser.selectedPoli == "Umum") { Color(0xFF0091EA)} else { Color(0xFF90A4AE)},
            colorlansia = if (LoggedUser.selectedPoli == "Lansia") { Color(0xFF0091EA)} else { Color(0xFF90A4AE) },
            changePoli = changePoli,
        )

        Box(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(0.1f),

            ){
            PilihTanggal(
                pilihState = state.value,
                pilihTanggal = pilihTanggal)
        }

        Box(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(),

            ) {
            ListPasien(
                names = state.value.antriSuccess!!,
                showPopup = {
                    showPopup = true
                }
            )
        }
    }

    PopupBox(
        popupWidth = 300F,
        popupHeight = 200F,
        showPopup = showPopup,
        onClickOutside = { showPopup = false },
        content = {
            Column (modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                Text(text = "Hapus antrian?")

                Button(onClick = {
                    viewModel.onUiEvent(editJUiEvent = EditJadwalUiEvent.Submit)

                    showPopup = false
                }) {
                    Text("Konfirmasi Hapus Antrian")
                }

                Button(onClick = { showPopup = false }) {
                    Text(text = "Batalkan hapus")
                }
            }
        }
    )
}

    @Composable
    fun ListPasien(
        names: List<JanjiTemu>,
        showPopup: () -> Unit
    ) {
        if (names.filter { it.nomorAntrian > 0 }.isNotEmpty()) {
            LazyColumn {
                items(names.filter { it.nomorAntrian > 0 }.sortedBy { it.nomorAntrian }) { name ->
                    PasienJanji(
                        name.nomorAntrian,
                        name.namaPasien,
                        name.username,
                        name.jamjanji,
                        name.keluhan,
                        name.hadir,
                        name.waktuhadir,
                        name.selesai,
                        name.predictedtime,
                        showPopup = showPopup
                    )
                }
            }
        } else {
            Column (
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                Icon(imageVector = Icons.Default.Article, contentDescription = "", modifier = Modifier.padding(vertical = 16.dp))

                Text(text = "Janji Temu Kosong")
            }
        }
    }

    @Composable
    fun PasienJanji(
        nomor: Int,
        pasien: String,
        username: String,
        jam: String,
        keluhan: String,
        hadir: Boolean,
        waktuhadir: String,
        selesai: Boolean,
        predictedtime: Int,
        showPopup: () -> Unit,
    ) {
        Card(
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .padding(vertical = 4.dp, horizontal = 8.dp)
                .fillMaxWidth()
                .fillMaxHeight()
                .clickable(
                    enabled = true,
                    onClick = {
                            LoggedUser.username = username
                            LoggedUser.nama = pasien
                            showPopup.invoke()
                              },
                )
        ) {
            Row() {
                Column (modifier = Modifier.padding(16.dp),) {
                    Text(
                        text = pasien,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                    )

                    Text(
                        text = "Sesi : $jam",
                        fontSize = 16.sp,
                    )

                    Text(
                        text = "Keluhan : $keluhan",
                        fontSize = 16.sp,
                    )

                    Text(text = if (hadir) {"Kehadiran : Hadir"} else {"Kehadiran : Belum Hadir"}, fontSize = 16.sp,)

                    Text(
                        text = "Waktu hadir : $waktuhadir",
                        fontSize = 16.sp,
                    )

                    Text(text = if (selesai) {"Status : Selesai"} else {"Status : Belum Selesai"}, fontSize = 16.sp,)

                    Text(
                        text = "Waktu layanan : ${predictedtime/60} menit",
                        fontSize = 16.sp,
                    )
                }
                Spacer(modifier = Modifier.weight(1f))

                Text(text = "$nomor", fontSize = 32.sp, modifier = Modifier.padding(16.dp))
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun PilihTanggal(
        pilihState: EditJadwalState,
        pilihTanggal: () -> Unit,
    ) {
        var today = GetDate()
        var todayYear = today[6].digitToInt()*1000 + today[7].digitToInt()*100 + today[8].digitToInt()*10 + today[9].digitToInt()
        var todayMonth = today[3].digitToInt()*10 + today[4].digitToInt() - 1
        var todayDay = today[0].digitToInt()*10 + today[1].digitToInt()

        var todayDate = remember {
            Calendar.getInstance().apply {
                set(Calendar.YEAR, todayYear)
                set(Calendar.MONTH, todayMonth)
                set(Calendar.DAY_OF_MONTH, todayDay)
            }.timeInMillis
        }

        var datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = todayDate
        )
        var showDatePicker by remember { mutableStateOf(false) }
        var selectedDatetext by remember { mutableStateOf("Pilih Tanggal") }

        Row(
            modifier = Modifier.padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = LoggedUser.currentSelectedTanggal,
                modifier = Modifier.align(Alignment.CenterVertically)
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    showDatePicker = true
                },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(imageVector = Icons.Default.CalendarMonth, contentDescription = "")
            }



            if (showDatePicker) {
                DatePickerDialog(
                    onDismissRequest = { /*TODO*/ },

                    confirmButton = {
                        TextButton(
                            onClick = {
                                var selectedDate = Calendar.getInstance().apply {
                                    timeInMillis = datePickerState.selectedDateMillis!!
                                }
                                selectedDatetext = SimpleDateFormat("dd/MM/yyyy").format(selectedDate)
                                pilihState.tanggal = selectedDatetext
                                LoggedUser.currentSelectedTanggal = selectedDatetext

                                showDatePicker = false

                                pilihTanggal.invoke()
                            }
                        ) { Text("OK") } },

                    dismissButton = {
                        TextButton(
                            onClick = {
                                showDatePicker = false
                            }
                        ) { Text("Cancel") }
                    }
                ) {
                    DatePicker(state = datePickerState)
                }
            }

        }
    }


@Preview
@Composable
fun EditJadwalPrev() {
    //EditJadwalScreen()
}