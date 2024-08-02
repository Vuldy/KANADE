import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.projects.kanade.ui.theme.KanadeTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.projects.kanade.data.LoggedUser
import com.projects.kanade.di.Injection
import com.projects.kanade.ui.AntrianViewModelFactory
import com.projects.kanade.ui.ViewModelFactory
import com.projects.kanade.ui.reusables.GetDate
import com.projects.kanade.ui.reusables.GetTime
import com.projects.kanade.ui.reusables.PopupBox
import com.projects.kanade.ui.reusables.compareTime
import com.projects.kanade.ui.screen.home.pasien.daftar.PilihJadwalUiEvent
import com.projects.kanade.ui.screen.home.pasien.daftar.PilihJadwalViewModel
import com.projects.kanade.ui.screen.landing.login.LoginState
import com.projects.kanade.ui.screen.landing.login.LoginUiEvent
import com.projects.kanade.ui.screen.landing.login.LoginViewModel
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun JadwalScreen(
    pilihJadwalViewModel: PilihJadwalViewModel = viewModel(
        factory = AntrianViewModelFactory(
            Injection.provideRepository2()
        )
    ),
    navigateToHome : () -> Unit,
    sesiPenuh: () -> Unit,
    backToDaftar: () -> Unit,
    jamInvalid: () -> Unit,
    tanggalInvalid: () -> Unit
) {
    val pilihState by remember {
        pilihJadwalViewModel.jadwalState
    }

    var showPopup by remember { mutableStateOf(false) }

    if (pilihState.valid){
        pilihState.valid = false

        navigateToHome.invoke()
    }

    else {
        Column(modifier = Modifier
            .fillMaxSize(),
        ) {
            BackButton(
                backToDaftar
            )

            SelectedPoli(
                pilihState = pilihState,
                navigateToHome =
                {
                    pilihJadwalViewModel.onUiEvent(pilihUiEvent = PilihJadwalUiEvent.Submit, invalid = sesiPenuh)
                },
                keluhanChange = { inputString ->
                    pilihJadwalViewModel.onUiEvent(
                        pilihUiEvent = PilihJadwalUiEvent.KeluhanChange(
                            inputString
                        ),
                        invalid = sesiPenuh
                    )
                },
                jamInvalid = jamInvalid,
                tanggalInvalid = tanggalInvalid,
                showPopup = { showPopup =  true },
            )
        }
    }

    PopupBox(
        popupWidth = 300F,
        popupHeight = 250F,
        showPopup = showPopup,
        onClickOutside = { showPopup = false } ,
        /*SessionSelect(
            pilihState = pilihState,
            jamInvalid = jamInvalid,
        )*/
        {
            var selectedTime by remember { mutableStateOf(0) }
            var selectedTimetext by remember { mutableStateOf("Pilih Waktu") }

            Column (
            ){
                    Button(
                        modifier = Modifier
                            .fillMaxWidth(),
                        onClick = {
                            if (pilihState.tanggal != GetDate() || compareTime(8,0)) {
                                selectedTime = 1
                                selectedTimetext = "08.00 - 09.00"
                                pilihState.waktu = selectedTime

                                showPopup = false
                            }
                            else {
                                jamInvalid.invoke()
                            }
                        }
                    ) {
                        Text(text = "08.00 - 09.00",
                            fontSize = 12.sp)
                    }

                    Button(
                        modifier = Modifier
                            .fillMaxWidth(),
                        onClick = {
                            if (pilihState.tanggal != GetDate() || compareTime(9,0)) {
                                selectedTimetext = "09.00 - 10.00"
                                selectedTime = 2
                                pilihState.waktu = selectedTime

                                showPopup = false
                            }
                            else {
                                jamInvalid.invoke()
                            }
                        }
                    ) {
                        Text(text = "09.00 - 10.00",
                            fontSize = 12.sp)
                    }

                    Button(
                        modifier = Modifier
                            .fillMaxWidth(),
                        onClick = {
                            if (pilihState.tanggal != GetDate() || compareTime(10,0)) {
                                selectedTimetext = "10.00 - 11.30"
                                selectedTime = 3
                                pilihState.waktu = selectedTime

                                showPopup = false
                            }
                            else {
                                jamInvalid.invoke()
                            }
                        }
                    ) {
                        Text(text = "10.00 - 11.30",
                            fontSize = 12.sp)
                    }


                    Button(
                        modifier = Modifier
                            .fillMaxWidth(),
                        onClick = {
                            if (pilihState.tanggal != GetDate() || compareTime(12,30)) {
                                selectedTimetext = "12.30 - 13.30"
                                selectedTime = 4
                                pilihState.waktu = selectedTime

                                showPopup = false
                            }
                            else {
                                jamInvalid.invoke()
                            }
                        }
                    ) {
                        Text(text = "12.30 - 13.30",
                            fontSize = 12.sp)
                    }

                    Button(
                        modifier = Modifier
                            .fillMaxWidth(),
                        onClick = {
                            if (pilihState.tanggal != GetDate() || compareTime(13,30)) {
                                selectedTimetext = "13.30 - 14.30"
                                selectedTime = 5
                                pilihState.waktu = selectedTime

                                showPopup = false
                            }
                            else {
                                jamInvalid.invoke()
                            }
                        }
                    ) {
                        Text(text = "13.30 - 14.30",
                            fontSize = 12.sp)
                    }

            }
        }
    )
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SelectedPoli(
    pilihState: PilihJadwalState,
    navigateToHome : () -> Unit,
    keluhanChange: (String) -> Unit,
    jamInvalid: () -> Unit,
    tanggalInvalid: () -> Unit,
    showPopup: () -> Unit,
) {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())) {
        ElevatedCard (
            shape = MaterialTheme.shapes.medium,
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 16.dp)
                .fillMaxHeight()
        ) {
            Column {
                Text(
                    text = "Daftar Poli " + LoggedUser.selectedPoli,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp))

                Text(
                    text = "Pilih tanggal",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 4.dp, horizontal = 16.dp))
                
                PilihTanggal(
                    pilihState = pilihState,
                    tanggalInvalid = tanggalInvalid)

                Text(
                    text = "Pilih waktu datang yang diinginkan",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 4.dp, horizontal = 16.dp))

                Row {
                    Text(text = when (pilihState.waktu){
                        1-> { "08.00 - 09.00" }
                        2-> { "09.00 - 10.00"}
                        3-> { "10.00 - 11.30"}
                        4-> { "12.30 - 13.30"}
                        5-> { "13.30 - 14.30"}
                        else -> { "" }
                    },
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(16.dp))

                    Spacer(modifier = Modifier.weight(1f))

                    Button(
                        onClick = showPopup,
                        modifier = Modifier.padding(16.dp),
                    ) {
                        Icon(imageVector = Icons.Default.AccessTime, contentDescription = "")
                    }
                }

                Keluhan(
                    pilihState = pilihState,
                    keluhanChange = keluhanChange,
                )

                Text(text = "Nomor antrian yang akan diterima pasien adalah nomor designasi pasien.\n" +
                        "Nomor didapatkan berdasarkan waktu yang dipilih pasien saat mendaftar.",
                    modifier = Modifier.padding(16.dp))

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = {
                        if (pilihState.tanggal != "" && pilihState.waktu != 0)
                        { navigateToHome.invoke() } },
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = "Konfirmasi Janji Temu",)
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PilihTanggal(
    pilihState: PilihJadwalState,
    tanggalInvalid: () -> Unit
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
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        //horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Text(
            text = pilihState.tanggal,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .align(Alignment.CenterVertically)
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                showDatePicker = true
            },
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            //Text(text = "Date Picker")
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

                                if (selectedDate.after(Calendar.getInstance()) || SimpleDateFormat("dd/MM/yyyy").format(
                                        selectedDate
                                    ) == GetDate()
                                ) {

                                    selectedDatetext =
                                        SimpleDateFormat("dd/MM/yyyy").format(selectedDate)
                                    val selectedDateDay =
                                        SimpleDateFormat("EEEE").format(selectedDate)
                                    pilihState.tanggal = selectedDatetext
                                    pilihState.hari = selectedDateDay

                                    showDatePicker = false
                                } else {
                                    showDatePicker = false

                                    tanggalInvalid.invoke()
                                }
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


@Composable
fun Keluhan(
    pilihState: PilihJadwalState,
    keluhanChange: (String) -> Unit,
) {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = pilihState.keluhan,
        onValueChange = keluhanChange,
        shape = RoundedCornerShape(percent = 20),
        label = { Text("Keluhan") },
        maxLines = 1,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White
        ),
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
            )
        )
}


/*@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SessionSelect(
    pilihState: PilihJadwalState,
    jamInvalid: () -> Unit,
    ) {

    var selectedTimetext by remember { mutableStateOf("Pilih Waktu") }

    Text(text = selectedTimetext,
        modifier = Modifier.padding(bottom = 16.dp))

    Column {
        Row {
            Button(
                modifier = Modifier
                    .weight(0.33f)
                    .size(width = 80.dp, height = 40.dp),
                onClick = {
                    if (pilihState.tanggal != GetDate() || compareTime(10)) {
                        selectedTimetext = "10.00 - 11.00"
                        pilihState.waktu = selectedTimetext
                    }
                    else {
                        jamInvalid.invoke()
                    }
                }
            ) {
                Text(text = "10.00 - 11.00",
                    fontSize = 12.sp)
            }

            Button(
                modifier = Modifier
                    .weight(0.33f)
                    .size(width = 80.dp, height = 40.dp),
                onClick = {
                    if (pilihState.tanggal != GetDate() || compareTime(11)) {
                        selectedTimetext = "11.00 - 12.00"
                        pilihState.waktu = selectedTimetext
                    }
                    else {
                        jamInvalid.invoke()
                    }
                }
            ) {
                Text(text = "11.00 - 12.00",
                    fontSize = 12.sp)
            }

            Button(
                modifier = Modifier
                    .weight(0.33f)
                    .size(width = 80.dp, height = 40.dp),
                onClick = {
                    if (pilihState.tanggal != GetDate() || compareTime(13)) {
                        selectedTimetext = "13.00 - 14.00"
                        pilihState.waktu = selectedTimetext
                    }
                    else {
                        jamInvalid.invoke()
                    }
                }
            ) {
                Text(text = "13.00 - 14.00",
                    fontSize = 12.sp)
            }
        }

        Row {
            Button(
                modifier = Modifier
                    .weight(0.33f)
                    .size(width = 80.dp, height = 40.dp),
                onClick = {
                    if (pilihState.tanggal != GetDate() || compareTime(14)) {
                        selectedTimetext = "14.00 - 15.00"
                        pilihState.waktu = selectedTimetext
                    }
                    else {
                        jamInvalid.invoke()
                    }
                }
            ) {
                Text(text = "14.00 - 15.00",
                    fontSize = 12.sp)
            }

            Button(
                modifier = Modifier
                    .weight(0.33f)
                    .size(width = 80.dp, height = 40.dp),
                onClick = {
                    if (pilihState.tanggal != GetDate() || compareTime(15)) {
                        selectedTimetext = "15.00 - 16.00"
                        pilihState.waktu = selectedTimetext
                    }
                    else {
                        jamInvalid.invoke()
                    }
                }
            ) {
                Text(text = "15.00 - 16.00",
                    fontSize = 12.sp)
            }
        }
    }
    
}*/

@Composable
fun BackButton(
    backToDaftar: () -> Unit
) {
    OutlinedButton(onClick = backToDaftar,
        modifier= Modifier.size(50.dp),  //avoid the oval shape
        shape = CircleShape,
        border= BorderStroke(1.dp, Color.Transparent),
        contentPadding = PaddingValues(0.dp),  //avoid the little icon
        colors = ButtonDefaults.outlinedButtonColors()
    ) {
        Icon(Icons.Default.ArrowBack, contentDescription = "content description")
    }
}

@Preview
@Composable
fun ButtonCheck() {
    
}