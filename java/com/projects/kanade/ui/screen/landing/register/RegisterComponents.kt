package com.projects.kanade.ui.screen.landing.register

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.projects.kanade.data.LoggedUser
import com.projects.kanade.ui.screen.home.admin.editantri.SimpleRadioButtonComponent
import com.projects.kanade.ui.screen.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UNameBar() {
    var username by rememberSaveable { mutableStateOf("") }

    TextField(
        modifier = Modifier
            .padding(vertical = 8.dp),
        value = username,
        onValueChange = { username = it },
        label = { Text("Username (huruf kecil)") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NomorBar() {
    var username by rememberSaveable { mutableStateOf("") }

    TextField(
        modifier = Modifier
            .padding(vertical = 8.dp),
        value = username,
        onValueChange = { username = it },
        label = { Text("Nomor Telpon") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
    )
}

@Composable
fun NamaBar() {
    var username by rememberSaveable { mutableStateOf("") }

    TextField(
        modifier = Modifier
            .padding(vertical = 8.dp),
        value = username,
        onValueChange = { username = it },
        label = { Text("Nama") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordBar() {
    var username by rememberSaveable { mutableStateOf("") }

    TextField(
        modifier = Modifier
            .padding(vertical = 8.dp),
        value = username,
        onValueChange = { username = it },
        label = { Text("Password") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RePasswordBar() {
    var username by rememberSaveable { mutableStateOf("") }

    TextField(
        modifier = Modifier
            .padding(vertical = 8.dp),
        value = username,
        onValueChange = { username = it },
        label = { Text("Ketik ulang Password") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
    )
}

@Composable
fun RegisterButton(
    navigateToLogin : () -> Unit,
) {
    Column() {
        Button(
            onClick = navigateToLogin,
            modifier = Modifier
                .size(width = 240.dp, height = 40.dp)
        ) {
            Text(text = "Registrasi")
        }
    }
}

@Composable
fun RegisterComponents(
    registerState: RegisterState,
    onNameChange: (String) -> Unit,
    onUmurChange: (String) -> Unit,
    onUsernameChange: (String) -> Unit,
    onTelpChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    navigateToLogin : () -> Unit,
    imeAction: ImeAction = ImeAction.Next,
    imeActionDone: ImeAction = ImeAction.Done,
    colors1: Color,
    colors2: Color,
) {

    val radioGender = listOf("Pria", "Wanita")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioGender[0]) }
    if (selectedOption == "Pria") { LoggedUser.gender = 0}
    else {LoggedUser.gender = 1}

    // Nama
    OutlinedTextField(
        modifier = Modifier
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(percent = 20),
        value = registerState.name,
        onValueChange = onNameChange,
        label = { Text("Nama") },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = imeActionDone
        )
    )

    SimpleRadioButtonComponent(
        displaytext = "",
        radioOptions = radioGender,
        selectedOption = selectedOption,
        onOptionSelected = onOptionSelected
    )

    // Umur
    OutlinedTextField(
        modifier = Modifier
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(percent = 20),
        value = registerState.umur,
        onValueChange = onUmurChange,
        label = { Text("Umur") },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = imeActionDone)
    )

    // Username
    OutlinedTextField(
        modifier = Modifier
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(percent = 20),
        value = registerState.username,
        onValueChange = onUsernameChange,
        colors = if (registerState.isUsernameValid) {OutlinedTextFieldDefaults.colors( focusedBorderColor = colors1, unfocusedBorderColor = colors1)}
        else {OutlinedTextFieldDefaults.colors(focusedBorderColor = colors2, unfocusedBorderColor = colors2)},
        label = { Text("Username (huruf kecil)") },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = imeActionDone)
    )

    // Nomor
    OutlinedTextField(
        modifier = Modifier
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(percent = 20),
        value = registerState.telp,
        onValueChange = onTelpChange,
        label = { Text("Nomor Telpon") },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = imeActionDone
        )
    )

    // Password
    OutlinedTextField(
        modifier = Modifier
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(percent = 20),
        value = registerState.password,
        onValueChange = onPasswordChange,
        colors = if (registerState.isPasswordValid) {OutlinedTextFieldDefaults.colors( focusedBorderColor = colors1, unfocusedBorderColor = colors1)}
        else {OutlinedTextFieldDefaults.colors(focusedBorderColor = colors2, unfocusedBorderColor = colors2)},
        label = { Text("Password (minimal 6 digit)") },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = imeActionDone
        )
    )

    // Re-Password
    OutlinedTextField(
        modifier = Modifier
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(percent = 20),
        value = registerState.confirmPassword,
        onValueChange = onConfirmPasswordChange,
        label = { Text("Ketik ulang Password") },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = imeActionDone)
    )

    Column() {
        Button(
            onClick = navigateToLogin,
            modifier = Modifier
                .padding(vertical = 16.dp)
                .size(width = 240.dp, height = 40.dp)
        ) {
            Text(text = "Registrasi")
        }
    }
}