package com.projects.kanade.ui.screen.home.pasien.daftar.pertanyaan

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.projects.kanade.data.LoggedUser

@Composable
fun PertanyaanScreen(
    navigateToNext: () -> Unit
) {

    CheckBoxSymptoms(
        navigateToNext = navigateToNext,
    )
}

@Composable
fun SimpleRadioButtonComponent(
    navigateToNext: () -> Unit,
) {
    val radioOptions = listOf("Symptom 1", "Symptom 2", "Symptom 3")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[2]) }
    Column(
        // we are using column to align our
        // imageview to center of the screen.
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),

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
            Text(text = "Insert pertanyaan here")

            // below line is use to set data to
            // each radio button in columns.
            radioOptions.forEach { text ->
                Row(
                    Modifier
                        // using modifier to add max
                        // width to our radio button.
                        .fillMaxWidth()
                        // below method is use to add
                        // selectable to our radio button.
                        .selectable(
                            // this method is called when
                            // radio button is selected.
                            selected = (text == selectedOption),
                            // below method is called on
                            // clicking of radio button.
                            onClick = { onOptionSelected(text) }
                        )
                        // below line is use to add
                        // padding to radio button.
                        .padding(horizontal = 16.dp)
                ) {

                    // below line is use to
                    // generate radio button
                    RadioButton(
                        // inside this method we are
                        // adding selected with a option.
                        selected = (text == selectedOption),modifier = Modifier.padding(all = Dp(value = 8F)),
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
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }

            }

            Button(onClick = navigateToNext) {
                Text(text = "Lanjutkan")
            }
        }
    }
}

@Composable
fun CheckBoxSymptoms(
    navigateToNext: () -> Unit,
) {
    val options = listOf(
        "Batuk",
        "Sesak nafas",
        "Demam",
    )

    val optionsMapped = mapOf(
        "Batuk" to 1,
        "Sesak nafas" to 3,
        "Demam" to 2,
        )

    val optionsStates = remember { mutableStateListOf(false, false, false) }

    var severity = remember { mutableStateOf(0) }
    LoggedUser.severity = severity.value

    Column {
        optionsStates.forEachIndexed { index, checked ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Checkbox(
                    checked = checked,
                    onCheckedChange = { isChecked ->
                        // Update the individual child state
                        optionsStates[index] = isChecked
                        if (isChecked){
                            severity.value += optionsMapped.getValue(options[index])
                        }
                        else {
                            severity.value -= optionsMapped.getValue(options[index])
                        }
                    }
                )
                Text(options[index])
            }
        }

        Text("${severity.value}")

        Text("${LoggedUser.severity}")

        Button(onClick = navigateToNext) {
            Text(text = "Lanjutkan")
        }
    }
}
