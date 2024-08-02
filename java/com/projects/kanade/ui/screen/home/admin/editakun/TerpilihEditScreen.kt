package com.projects.kanade.ui.screen.home.admin.editakun

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.projects.kanade.data.LoggedUser
import com.projects.kanade.di.Injection
import com.projects.kanade.ui.ViewModelFactory
import com.projects.kanade.ui.screen.home.admin.editantri.SimpleRadioButtonComponent
import com.projects.kanade.ui.screen.home.pasien.daftar.PilihJadwalUiEvent

@Composable
fun TerpilihEditScreen(
    modifier: Modifier = Modifier,
    viewModel: TerpilihEditViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    finishEdit: () -> Unit,
    inputInvalid: () -> Unit,
    back: () -> Unit
) {

    val state = viewModel.getUser.collectAsState()

    val radioOptions = listOf("Pasien", "Dokter", "Admin")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }

    if (selectedOption == "Admin") { state.value.access = 3}
    else if ( selectedOption == "Dokter") { state.value.access = 2}
    else {state.value.access = 1}

    if (state.value.success){
        state.value.success = false
        finishEdit.invoke()
    }
    if (state.value.invalid){
        inputInvalid.invoke()
        state.value.invalid = false
    }

    Column {
        BackButton( back = back )


        /*TextField(
            value = state.value.username,
            onValueChange = { inputString ->
                viewModel.onUiEvent(
                    terpilihUiEvent = TerpilihEditUiEvent.UsernameChange(
                        inputString
                    )
                ) },
            label = { Text("Username") },
            maxLines = 1,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        )*/
        Text(text = "Sedang meng-edit User ${state.value.username}",
            modifier = Modifier.padding(8.dp).align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,)

        TextField(
            value = state.value.umur,
            onValueChange = { inputInt ->
                viewModel.onUiEvent(
                    terpilihUiEvent = TerpilihEditUiEvent.UmurChange(
                        inputInt
                    )
                ) },
            label = { Text("Umur") },
            maxLines = 1,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            )
        )

        TextField(
            value = state.value.telp,
            onValueChange = { inputString ->
                viewModel.onUiEvent(
                    terpilihUiEvent = TerpilihEditUiEvent.TelpChange(
                        inputString
                    )
                ) },
            label = { Text("Telp") },
            maxLines = 1,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        )

        /*TextField(
            value = "${state.value.access}",
            onValueChange = { inputInt ->
                viewModel.onUiEvent(
                    terpilihUiEvent = TerpilihEditUiEvent.AccessChange(
                        inputInt.toInt()
                    )
                ) },
            label = { Text("Access") },
            maxLines = 1,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        )*/

        SimpleRadioButtonComponent(
            displaytext = "Akses Level",
            radioOptions = radioOptions,
            selectedOption = selectedOption,
            onOptionSelected = onOptionSelected
        )

        /*TextField(
            value = state.value.password,
            onValueChange = { inputInt ->
                viewModel.onUiEvent(
                    terpilihUiEvent = TerpilihEditUiEvent.PasswordChange(
                        inputInt
                    )
                ) },
            label = { Text("Password Baru") },
            visualTransformation = PasswordVisualTransformation(),
            maxLines = 1,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        )*/

        Button(onClick = {
            viewModel.onUiEvent(terpilihUiEvent = TerpilihEditUiEvent.Submit)

        }) {
            Text("Selesai")
        }
    }
}

