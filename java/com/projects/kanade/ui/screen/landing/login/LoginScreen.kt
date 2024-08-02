package com.projects.kanade.ui.screen.landing.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.projects.kanade.R
import com.projects.kanade.data.LoggedUser
import com.projects.kanade.di.Injection
import com.projects.kanade.ui.ViewModelFactory
import com.projects.kanade.ui.reusables.GetDay
import com.projects.kanade.ui.reusables.GetMonth
import com.projects.kanade.ui.screen.landing.register.RegisterPage
import com.projects.kanade.ui.screen.navigation.Screen
import com.projects.kanade.ui.theme.KanadeTheme

@Composable
fun LoginPage(
    loginViewModel: LoginViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    modifier: Modifier = Modifier,
    navigateToHome: () -> Unit,
    navigateToRegister: () -> Unit,
    loginIncorrect: () -> Unit,
    loginFailed: () -> Unit,
) {
    val loginState by remember {
        loginViewModel.loginState
    }

    if (loginState.isLoginIncorrect) {
        loginState.isLoginIncorrect = false
        loginIncorrect.invoke()
    }

    if (loginState.isLoginSuccessful) {
        if (LoggedUser.nama == "Tidak ada data"){
            loginState.isLoginSuccessful = false
            loginFailed.invoke()
        }

        else {
            LaunchedEffect(key1 = true) {
                navigateToHome.invoke()
            }
        }
    } else {
        
        Column(
            /*modifier = Modifier
                .fillMaxSize()
                .imePadding()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,*/
        ) {
            // LoginBar()
            // PasswordBar()
            // LoginButton(
            //    navigateToHome = navigateToHome,
            // )
            // RegisterButton(
            //    navigateToRegister = navigateToRegister,
            // )
            
            /*Text(text = GetMonth())
            Text(text = "${GetDay()}")*/
            
            Spacer(modifier = Modifier.height(32.dp))

            LoginComponents(
                loginState = loginState,
                usernameChange = { inputString ->
                    loginViewModel.onUiEvent(
                        loginUiEvent = LoginUiEvent.UsernameChange(
                            inputString
                        )
                    )
                },
                passwordChange = { inputString ->
                    loginViewModel.onUiEvent(
                        loginUiEvent = LoginUiEvent.PasswordChange(
                            inputString
                        )
                    )
                },
                navigateToHome = {
                    loginViewModel.onUiEvent(loginUiEvent = LoginUiEvent.Submit)
                },
                navigateToRegister = navigateToRegister,
            )

        }
    }
}


@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    KanadeTheme {
        //LoginBar()
    }
}