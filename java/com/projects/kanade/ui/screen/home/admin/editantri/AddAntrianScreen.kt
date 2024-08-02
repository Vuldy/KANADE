package com.projects.kanade.ui.screen.home.admin.editantri

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.projects.kanade.data.LoggedUser
import com.projects.kanade.di.Injection
import com.projects.kanade.ui.AntrianViewModelFactory
import com.projects.kanade.ui.ViewModelFactory
import com.projects.kanade.ui.screen.home.admin.editakun.TerpilihEditUiEvent
import com.projects.kanade.ui.screen.home.admin.editakun.TerpilihEditViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddAntrianScreen(
    modifier: Modifier = Modifier,
    viewModel: AddAntrianViewModel = viewModel(
        factory = AntrianViewModelFactory(
            Injection.provideRepository2()
        )
    ),
    antrianOffline: () -> Unit,
    antrianOfflinemsg: () -> Unit,
    sesiPenuh: () -> Unit,
    inputInvalid: () -> Unit,
) {

    val state = viewModel.getUser.collectAsState()

    val radioOptions = listOf("Umum", "Lansia", "Anak")
    val radioGender = listOf("Pria", "Wanita")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
    val (selectedOption2, onOptionSelected2) = remember { mutableStateOf(radioGender[0]) }

    state.value.poli = selectedOption
    if (selectedOption2 == "Pria") { LoggedUser.gender = 0}
    else {LoggedUser.gender = 1}

    if (state.value.invalid){
        inputInvalid.invoke()
        state.value.invalid = false
    }

    if (state.value.penuh){
        sesiPenuh.invoke()
        state.value.penuh = false
    }

    if (state.value.terdaftar){
        state.value.terdaftar = false
        antrianOffline.invoke()
        antrianOfflinemsg.invoke()
    }

    Column (
        modifier = Modifier.verticalScroll(rememberScrollState())
    ){
        Text(text = "Pendaftaran Pasien Offline", fontSize = 24.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(16.dp))

        OutlinedTextField(
            value = state.value.nama,
            onValueChange = { inputString ->
                viewModel.onUiEvent(
                    addAntrianUiEvent = AddAntrianUiEvent.NameChange(
                        inputString
                    )
                ) },
            label = { Text("Nama Pasien") },
            maxLines = 1,
            shape = RoundedCornerShape(percent = 20),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            )
        )

        OutlinedTextField(
            value = state.value.umur,
            onValueChange = { inputInt ->
                viewModel.onUiEvent(
                    addAntrianUiEvent = AddAntrianUiEvent.UmurChange(
                        inputInt
                    )
                ) },
            label = { Text("Umur") },
            maxLines = 1,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(percent = 20),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            )
        )

        OutlinedTextField(
            value = state.value.keluhan,
            onValueChange = { inputInt ->
                viewModel.onUiEvent(
                    addAntrianUiEvent = AddAntrianUiEvent.KeluhanChange(
                        inputInt
                    )
                ) },
            label = { Text("Keluhan") },
            maxLines = 1,
            shape = RoundedCornerShape(percent = 20),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            )
        )

        SimpleRadioButtonComponent("Pilih jenis kelamin!",radioOptions = radioGender, selectedOption = selectedOption2, onOptionSelected = onOptionSelected2)

        SimpleRadioButtonComponent("Pilih Poli!", radioOptions, selectedOption, onOptionSelected)

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
            viewModel.onUiEvent(addAntrianUiEvent = AddAntrianUiEvent.Submit)

                /*if (state.value.terdaftar){
                    antrianOffline.invoke()
                }*/

        }) {
            Text("Daftar!")
        }
    }
}

@Composable
fun SimpleRadioButtonComponent(
    displaytext: String,
    radioOptions: List<String>,
    selectedOption: String,
    onOptionSelected: (selectedOption: String) -> Unit,
) {
    Column(
        // we are using column to align our
        // imageview to center of the screen.
        modifier = Modifier
            .padding(8.dp),

        // below line is used for
        // specifying vertical arrangement.
        verticalArrangement = Arrangement.Center,

        // below line is used for
        // specifying horizontal arrangement.
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // we are displaying all our
        // radio buttons in column.
        Column {
            Text(text = displaytext, modifier = Modifier.padding(horizontal = 8.dp))

            // below line is use to set data to
            // each radio button in columns.
            radioOptions.forEach { text ->
                Row(
                    Modifier
                        // using modifier to add max
                        // width to our radio button.
                        //.fillMaxWidth()
                        // below method is use to add
                        // selectable to our radio button.
                        .selectable(
                            // this method is called when
                            // radio button is selected.
                            selected = (text == selectedOption),
                            // below method is called on
                            // clicking of radio button.
                            onClick = {
                                onOptionSelected(text)
                            }
                        )
                        // below line is use to add
                        // padding to radio button.
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    // below line is use to
                    // generate radio button
                    RadioButton(
                        // inside this method we are
                        // adding selected with a option.
                        selected = (text == selectedOption),/*modifier = Modifier.padding(all = 8.dp)*/
                        onClick = {
                            // inside on click method we are setting a
                            // selected option of our radio buttons.
                            onOptionSelected(text)
                        }
                    )
                    // below line is use to add
                    // text to our radio buttons.
                    Text(
                        text = text,
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun AddAntrianPrev() {
    //AddAntrianScreen()
}