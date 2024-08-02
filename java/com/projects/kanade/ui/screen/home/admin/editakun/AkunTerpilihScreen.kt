package com.projects.kanade.ui.screen.home.admin.editakun

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.projects.kanade.di.Injection
import com.projects.kanade.ui.ViewModelFactory
import com.projects.kanade.ui.screen.home.pasien.daftar.PilihJadwalUiEvent
import com.projects.kanade.ui.screen.home.profile.ProfileViewModel

@Composable
fun AkunTerpilihScreen(
    editAkun: () -> Unit,
    back: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AkunTerpilihViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    )
) {
    val state = viewModel.getUser.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        BackButton (
            back = back
            )

        Row {
            Text(
                text = "Nama :",
                modifier = Modifier.padding(8.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = state.value.antriSuccess!!.nama,
                modifier = Modifier.padding(8.dp),
                textAlign = TextAlign.Center,
            )
        }

        Row {
            Text(
                text = "Username :",
                modifier = Modifier.padding(8.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = state.value.antriSuccess!!.username,
                modifier = Modifier.padding(8.dp),
                textAlign = TextAlign.Center,
            )
        }

        Row {
            Text(
                text = "Umur :",
                modifier = Modifier.padding(8.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "${state.value.antriSuccess!!.umur}",
                modifier = Modifier.padding(8.dp),
                textAlign = TextAlign.Center,
            )
        }

        Row {
            Text(
                text = "Nomor :",
                modifier = Modifier.padding(8.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = state.value.antriSuccess!!.telp,
                modifier = Modifier.padding(8.dp),
                textAlign = TextAlign.Center,
            )
        }

        Row {
            Text(
                text = "Level akses :",
                modifier = Modifier.padding(8.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = if (state.value.antriSuccess!!.access == 1) {"Pasien"} else if (state.value.antriSuccess!!.access == 2) {"Dokter"} else if (state.value.antriSuccess!!.access == 3) {"Admin"} else {""},
                modifier = Modifier.padding(8.dp),
                textAlign = TextAlign.Center,
            )
        }
        
        Button(onClick = editAkun
        ) {
            Text(text = "Edit")
        }
    }
}

@Composable
fun BackButton(
    back: () -> Unit
) {
    OutlinedButton(onClick = back,
        modifier= Modifier.size(50.dp),  //avoid the oval shape
        shape = CircleShape,
        border= BorderStroke(1.dp, Color.Transparent),
        contentPadding = PaddingValues(0.dp),  //avoid the little icon
        colors = ButtonDefaults.outlinedButtonColors()
    ) {
        Icon(Icons.Default.ArrowBack, contentDescription = "content description")
    }
}