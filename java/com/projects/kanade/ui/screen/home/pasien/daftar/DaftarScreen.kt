package com.projects.kanade.ui.screen.home.pasien.daftar

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.projects.kanade.R
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ImageSearch
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.projects.kanade.data.LoggedUser
import com.projects.kanade.di.Injection
import com.projects.kanade.model.DummyDataSource.dummyPoli
import com.projects.kanade.ui.AntrianViewModelFactory
import com.projects.kanade.ui.screen.home.pasien.antrian.AntrianViewModel
import com.projects.kanade.ui.theme.KanadeTheme


@Composable
fun DaftarScreen(
    modifier: Modifier = Modifier,
    navigateToDaftar : () -> Unit,
    viewModel: DaftarViewModel = viewModel(
        factory = AntrianViewModelFactory(
            Injection.provideRepository2()
        )
    )
) {
    val state = viewModel.getAntrian.collectAsState()

    if (state.value.antriSuccess!!.nomorAntrian == 0){
        Column (
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            ElevatedCard(
                shape = MaterialTheme.shapes.medium,
                /*colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    ),*/
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                ),
                modifier = Modifier
                    .padding(vertical = 4.dp, horizontal = 16.dp)
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .navigationBarsPadding()

            ){
                Text(
                    text = "Pilih Poli",
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp,
                    modifier = Modifier.padding(16.dp)
                )

                Text(
                    text = "Dengan mendaftarkan janji temu, pasien berkomitmen untuk dapat melakukan janji temu sesuai dengan waktu yang telah ditentukan.",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(16.dp)
                )

                Text(
                    text = "Pasien akan diberikan perkiraan waktu janji temu. Untuk menghindari penyebaran penyakit, diharapkan pasien dapat datang sebelum waktu yang ditentukan",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(16.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                PoliList(
                    dummyPoli,
                    navigateToDaftar = navigateToDaftar
                )
            }
        }
    }
    else {
        Column (
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Icon(imageVector = Icons.Default.ImageSearch, contentDescription = "", modifier = Modifier.padding(vertical = 16.dp))
            
            Text(
                text = "Selesaikan janji temu di Puskesmas sebelum membuat janji temu baru!",
                textAlign = TextAlign.Center,
                fontSize = 16.sp
                )
        }
    }
}

@Composable
fun PoliChoice(
    name: String,
    navigateToDaftar : () -> Unit,
) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .clickable(
                enabled = true,
                onClick = {
                    LoggedUser.selectedPoli = name
                    navigateToDaftar.invoke()
                }
            )
            .padding(vertical = 8.dp, horizontal = 16.dp),
        colors = CardDefaults.cardColors(Color.White)

    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.jetpack_compose),
                contentDescription = "Logo Jetpack Compose",
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Poli $name",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

@Composable
fun PoliList(
    names: List<String>,
    navigateToDaftar : () -> Unit,
    ) {
    /*if (names.isNotEmpty()) {
        LazyColumn {
            items(names) { name ->
                PoliChoice(
                    name,
                    navigateToDaftar = navigateToDaftar
                )
            }
        }
    } else {
        Text("Kosong")
    }*/

    Column {
        PoliChoice(
            "Umum",
            navigateToDaftar = navigateToDaftar
        )

        PoliChoice(
            "Anak",
            navigateToDaftar = navigateToDaftar
        )

        PoliChoice(
            "Lansia",
            navigateToDaftar = navigateToDaftar
        )
    }
}

@Preview
@Composable
fun PrevDaftar() {
    KanadeTheme {
    PoliChoice(name = "john") {
        
    }
    }
}