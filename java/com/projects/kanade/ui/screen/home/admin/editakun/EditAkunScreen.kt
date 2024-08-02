package com.projects.kanade.ui.screen.home.admin.editakun

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.projects.kanade.data.LoggedUser
import com.projects.kanade.di.Injection
import com.projects.kanade.model.JanjiTemu
import com.projects.kanade.model.UserDB
import com.projects.kanade.ui.AntrianViewModelFactory
import com.projects.kanade.ui.ViewModelFactory
import com.projects.kanade.ui.screen.home.admin.editjadwal.EditJadwalViewModel
import com.projects.kanade.ui.screen.home.admin.editjadwal.PilihTanggal

@Composable
fun EditAkunScreen(
    modifier: Modifier = Modifier,
    viewModel: EditAkunViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigatetoEdit: () -> Unit
) {
    val state = viewModel.getUser.collectAsState()

    Column {
        Text(
            text = "Klik Akun untuk ubah!",
            modifier = Modifier.padding(8.dp),
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
        )

        Box(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(),

            ) {
            ListPasien(
                names = state.value.antriSuccess!!,
                navigatetoEdit
            )
        }
    }
}

@Composable
fun ListPasien(
    names: List<UserDB>,
    navigatetoEdit: () -> Unit,
) {
    if (names.isNotEmpty()) {
        LazyColumn {
            items(names) { name ->
                PasienJanji(
                    name.nama,
                    name.username,
                    navigatetoEdit
                )
            }
        }
    } else {
        Text("Kosong")
    }
}

@Composable
fun PasienJanji(
    pasien: String,
    username: String,
    navigatetoEdit: () -> Unit
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        /*colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ),*/
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .fillMaxWidth()
            .clickable(
                enabled = true,
                onClick = {
                    LoggedUser.username = username
                    navigatetoEdit.invoke()
                          },
            )
    ) {
        Column (modifier = Modifier.padding(8.dp)) {
            Text(
                text = pasien,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )

            Text(
                text = "Username : $username",
                fontSize = 16.sp,
            )
        }
    }
}

@Preview
@Composable
fun EditAkunPrev() {
    //EditAkunScreen()
}