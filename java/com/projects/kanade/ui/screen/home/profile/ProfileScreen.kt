package com.projects.kanade.ui.screen.home.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.projects.kanade.R
import com.projects.kanade.data.LoggedUser
import com.projects.kanade.di.Injection
import com.projects.kanade.ui.ViewModelFactory
import com.projects.kanade.ui.common.UiState
import com.projects.kanade.ui.screen.home.pasien.daftar.PilihJadwalUiEvent
import com.projects.kanade.ui.theme.KanadeTheme
import java.security.cert.TrustAnchor

@Composable
fun ProfileHeader(
    nama : String,
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.jetpack_compose),
                contentDescription = "Logo Jetpack Compose",
                modifier = Modifier.size(80.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = nama,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

@Composable
fun OptionBox(
    username : String,
    jeniskelamin: String,
    umur: String,
    noTelp : String,
    trust: String,
    navigateToLogin: () -> Unit,
    logout: () -> Unit,
) {
    Card (
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .navigationBarsPadding(),

    ){
        Column (
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
        ){
            Text(
                text = "Username",
                fontWeight = FontWeight.Bold,
                )

            Text(
                text = username,
                fontSize = 16.sp,
            )
            Spacer(modifier = Modifier.size(16.dp))

            Text(
                text = "Umur",
                fontWeight = FontWeight.Bold,
            )

            Text(
                text = umur,
                fontSize = 16.sp,
            )
            Spacer(modifier = Modifier.size(16.dp))

            Text(
                text = "Jenis Kelamin",
                fontWeight = FontWeight.Bold,
            )

            Text(
                text = jeniskelamin,
                fontSize = 16.sp,
            )
            Spacer(modifier = Modifier.size(16.dp))

            Text(
                text = "Nomor Telpon",
                fontWeight = FontWeight.Bold,
                )

            Text(
                text = noTelp,
                fontSize = 16.sp,
            )

            Spacer(modifier = Modifier.size(16.dp))

            Text(
                text = "Tingkat kepercayaan",
                fontWeight = FontWeight.Bold,
                )

            Text(
                text = trust,
                fontSize = 16.sp,
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    LoggedUser.nama = ""
                    LoggedUser.id = 0
                    LoggedUser.accesslevel = 0
                    LoggedUser.selectedPoli = ""
                    LoggedUser.telp = ""
                    LoggedUser.username = ""


                    logout.invoke()

                    navigateToLogin.invoke()},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height = 40.dp)
            ) {
                Text(text = "Logout")
            }
        }
    }
}

@Composable
fun OptionBoxAdmin(
    username : String,
    navigateToLogin: () -> Unit,
    logout: () -> Unit,
) {
    Card (
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .navigationBarsPadding(),

        ){
        Column (
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
        ){
            Text(
                text = "Username",
                fontWeight = FontWeight.Bold,
            )

            Text(
                text = username,
                fontSize = 16.sp,
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    LoggedUser.nama = ""
                    LoggedUser.id = 0
                    LoggedUser.accesslevel = 0
                    LoggedUser.selectedPoli = ""
                    LoggedUser.telp = ""
                    LoggedUser.username = ""


                    logout.invoke()

                    navigateToLogin.invoke()},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height = 40.dp)
            ) {
                Text(text = "Logout")
            }
        }
    }
}

@Composable
fun ProfileScreen(
    navigateToLogin: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
) {
    val state = viewModel.getUser.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (LoggedUser.accesslevel != 3){
        ProfileHeader(
            state.value.isSuccess!!.nama
        )
        Spacer(modifier = Modifier.height(16.dp))
        OptionBox(
            state.value.isSuccess!!.username,
            if (state.value.isSuccess!!.gender == 0) { "Pria" } else { "Wanita"},
            "${state.value.isSuccess!!.umur}",
            state.value.isSuccess!!.telp,
            "${state.value.isSuccess!!.trust}",
            navigateToLogin,
            logout = { viewModel.onUiEvent(profileUiEvent = ProfileUiEvent.Submit) },
        )} else {
            ProfileHeader(
                "Admin"
            )
            Spacer(modifier = Modifier.height(16.dp))
            OptionBoxAdmin(
                "Admin",
                navigateToLogin,
                logout = { viewModel.onUiEvent(profileUiEvent = ProfileUiEvent.Submit) },
            )
        }
    }





}

@Preview(showBackground = true)
@Composable
fun PrevProfileScreen() {
    KanadeTheme {
        //ProfileScreen(1)
    }
}