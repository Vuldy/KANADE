package com.projects.kanade.ui.screen.home.pasien.rekam

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.projects.kanade.R
import com.projects.kanade.ui.screen.home.pasien.antrian.AntrianScreen
import com.projects.kanade.ui.theme.KanadeTheme

@Composable
fun RekamMedis(
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .navigationBarsPadding()
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(vertical = 8.dp, horizontal = 8.dp)
            .verticalScroll(rememberScrollState())

    ) {
        Column {
            Text(
                text = "Tata cara mendaftar",
                modifier = Modifier.padding(8.dp),
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "1. Buka halaman 'Daftar' dengan menekan tombol di bawah.\n2. Pilih poli yang diinginkan.\n3. Pilih tanggal janji temu yang diinginkan\n" +
                        "4. Pilih kisaran waktu Anda ingin datang.\n5. Masukkan keluhan yang dialami \n6. Pencet tombol daftar.\n",
                modifier = Modifier.padding(8.dp),
                textAlign = TextAlign.Left,
            )

            Text(
                text = "Setelah mendaftar",
                modifier = Modifier.padding(8.dp),
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "Anda dapat melihat detail antrian pada halaman 'Antrian'\n\nJika ingin membatalkan antrian:\nKlik tombol 'Batalkan janji temu'\n\n" +
                        "Jika Anda sudah hadir di Puskesmas, tekan tombol 'Hadir di Puskesmas'\n\n" +
                        "Jika sudah menekan tombol hadir, maka silahkan menunggu panggilan dari Dokter.\n\nSilahkan datang ke Puskesmas mendekati waktu yang diprediksi.\n"
                        ,
                modifier = Modifier.padding(8.dp),
                textAlign = TextAlign.Left,
            )

            Text(
                text = "Logout",
                modifier = Modifier.padding(8.dp),
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "Anda dapat melakukan Logout pada halaman 'Profil' dengan menekan tombol 'Logout'"
                ,
                modifier = Modifier.padding(8.dp),
                textAlign = TextAlign.Left,
            )
        }

    }
}


@Composable
fun RekamScreen(
    modifier: Modifier = Modifier
) {
    Column {
        Text(
            text = "Panduan Penggunaan KANADE",
            modifier = Modifier.padding(8.dp),
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
        )

        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            RekamMedis()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PrevRekam() {
    KanadeTheme {
        RekamScreen()
    }
}