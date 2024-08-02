
package com.projects.kanade.ui.screen.landing.register

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.projects.kanade.di.Injection
import com.projects.kanade.ui.ViewModelFactory
import com.projects.kanade.ui.screen.landing.login.LoginPage
import com.projects.kanade.ui.screen.navigation.Screen
import com.projects.kanade.ui.theme.KanadeTheme
import kotlinx.coroutines.launch

@Composable
fun RegisterPage(
    registerViewModel: RegisterViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    modifier: Modifier = Modifier,
    navigateToLogin : () -> Unit,
    registrationInvalid: () -> Unit,
    usernameInvalid: () -> Unit,
    passwordInvalid: () -> Unit,
    passandusernameInvalid: () -> Unit,
    backToLogin: () -> Unit
) {

    val registerState by remember {
        registerViewModel.registrationState
    }

    if (registerState.isRegistrationInvalid) {
        if (!registerState.isUsernameValid && !registerState.isPasswordValid){
            passandusernameInvalid.invoke()
            registerState.isUsernameValid = true
            registerState.isPasswordValid = true
        } else if (registerState.isUsernameValid && !registerState.isPasswordValid) {
            passwordInvalid.invoke()
            registerState.isPasswordValid = true
        } else if (!registerState.isUsernameValid && registerState.isPasswordValid) {
            usernameInvalid.invoke()
            registerState.isUsernameValid = true
        }
        else {
            registrationInvalid.invoke()
        }

        registerState.isRegistrationInvalid = false
    }

    if (registerState.isRegistrationSuccessful) {
        LaunchedEffect(key1 = true) {
            navigateToLogin.invoke()
        }
    } else {
        Column (modifier = Modifier
            .imePadding()
            .verticalScroll(rememberScrollState()))
        {
        BackButton (backToLogin)

        Row (
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.Start,
            ){
            Text(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                text = "Registrasi")
        }

        Column(
            modifier = modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
            ) {

            RegisterComponents(
                registerState = registerState,
                onNameChange = { inputString ->
                    registerViewModel.onUiEvent(
                        registrationUiEvent = RegisterUiEvent.NameChanged(
                            inputValue = inputString
                        )
                    )
                },
                onUmurChange = { inputString ->
                    registerViewModel.onUiEvent(
                        registrationUiEvent = RegisterUiEvent.UmurChanged(
                            inputValue = inputString
                        )
                    )
                },
                onUsernameChange = { inputString ->
                    registerViewModel.onUiEvent(
                        registrationUiEvent = RegisterUiEvent.UsernameChanged(
                            inputValue = inputString
                        )
                    )
                },
                onTelpChange = { inputString ->
                    registerViewModel.onUiEvent(
                        registrationUiEvent = RegisterUiEvent.TelpChanged(
                            inputValue = inputString
                        )
                    )
                },
                onPasswordChange = { inputString ->
                    registerViewModel.onUiEvent(
                        registrationUiEvent = RegisterUiEvent.PasswordChanged(
                            inputValue = inputString
                        )
                    )
                },
                onConfirmPasswordChange = { inputString ->
                    registerViewModel.onUiEvent(
                        registrationUiEvent = RegisterUiEvent.ConfirmPasswordChanged(
                            inputValue = inputString
                        )
                    )
                },
                navigateToLogin = {
                    registerViewModel.onUiEvent(registrationUiEvent = RegisterUiEvent.Submit)
                },
                colors1 = Color(0xFF212121),
                colors2 = Color(0xFFE64A19),
            )
        }
    }
    }
}

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

@Preview(showBackground = true)
@Composable
fun RegisterPagePreview() {
    KanadeTheme {
        PasswordBar()
    }
}