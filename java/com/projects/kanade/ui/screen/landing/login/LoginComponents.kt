package com.projects.kanade.ui.screen.landing.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.projects.kanade.R

@Composable
fun LogoImage(
) {
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginBar() {
    var username by rememberSaveable { mutableStateOf("") }

    TextField(
        modifier = Modifier
            .padding(vertical = 8.dp),
        value = username,
        onValueChange = { username = it },
        label = { Text("Username") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordBar(
    loginState: LoginState,
) {
    var password by rememberSaveable { mutableStateOf("") }

    TextField(
        modifier = Modifier
            .padding(vertical = 16.dp),
        value = loginState.password,
        onValueChange = { password = it },
        label = { Text("Password") },
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
    )
}

@Composable
fun LoginButton(
    navigateToHome: () -> Unit,
) {
    Column() {
        Button(
            onClick = navigateToHome,
            modifier = Modifier
                .size(width = 240.dp, height = 40.dp)
        ) {
            Text(text = "Login")
        }
    }
}

@Composable
fun RegisterButton(
    navigateToRegister: () -> Unit,
) {
    TextButton(
        onClick = navigateToRegister,
    ) {
        Text("Tidak punya akun? Registrasi disini!")
    }
}

@Composable
fun LoginComponents(
    loginState: LoginState,
    usernameChange: (String) -> Unit,
    passwordChange: (String) -> Unit,
    navigateToHome: () -> Unit,
    navigateToRegister: () -> Unit,
    imeAction: ImeAction = ImeAction.Next,
    imeActionDone: ImeAction = ImeAction.Done,
) {
    // Login Button

    Column(
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
        ) {
            Text(
                text = "Selamat datang di KANADE",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 8.dp)
                    .align(Alignment.CenterHorizontally),
            )

            // Enter Username
            OutlinedTextField(
                modifier = Modifier
                    .padding(vertical = 8.dp),
                value = loginState.telp,
                shape = RoundedCornerShape(percent = 20),
                onValueChange = usernameChange,
                label = { Text("Username") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = imeActionDone
                )
            )

            // Enter Password
            ShowHidePasswordTextField(
                passtext = loginState.password,
                passwordChange = passwordChange,
                modifier = Modifier
                    .padding(vertical = 16.dp)
            )
        }

        Spacer(modifier = Modifier
            .weight(1f))

        Column (modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,){

            Button(
                onClick = navigateToHome,
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .size(width = 240.dp, height = 40.dp)
            ) {
                Text(text = "Login")
            }

            // Register Button
            TextButton(
                onClick = navigateToRegister,
                modifier = Modifier
                    .padding(vertical = 8.dp)
            )  {
                Text("Tidak punya akun? Registrasi disini!", textDecoration = TextDecoration.Underline)
            }
        }
    }
}

@Composable
fun ShowHidePasswordTextField(
    passtext: String,
    passwordChange: (String) -> Unit,
    modifier: Modifier,
    imeActionDone: ImeAction = ImeAction.Done,
) {
    var showPassword by remember { mutableStateOf(value = false) }

    OutlinedTextField(
        value = passtext,
        onValueChange = passwordChange,
        label = {
            Text(text = "Password")
        },
        placeholder = { Text(text = "Type password here") },
        shape = RoundedCornerShape(percent = 20),
        visualTransformation = if (showPassword) {
            VisualTransformation.None

        } else {

            PasswordVisualTransformation()

        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = imeActionDone),
        trailingIcon = {
            if (showPassword) {
                IconButton(onClick = { showPassword = false }) {
                    Icon(
                        imageVector = Icons.Filled.Visibility,
                        contentDescription = "hide_password"
                    )
                }
            } else {
                IconButton(
                    onClick = { showPassword = true }) {
                    Icon(
                        imageVector = Icons.Filled.VisibilityOff,
                        contentDescription = "hide_password"
                    )
                }
            }
        }
    )
}
