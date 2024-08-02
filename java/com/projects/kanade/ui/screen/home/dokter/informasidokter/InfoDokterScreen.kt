package com.projects.kanade.ui.screen.home.dokter.informasidokter

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
fun InfoDokter(
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
                text = "Melayani Pasien",
                modifier = Modifier.padding(8.dp),
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "1. Buka halaman 'Antrian' dengan menekan tombol di bawah.\n2. Pilih poli yang diinginkan dengan menekan salah satu dari tiga tombol di atas." +
                        "\n3. Detail pasien yang ditampilkan adalah pasien dipanggil selanjutnya\n" +
                        "4. Jika pasien tidak datang jika dipanggil, maka tekan tombol 'Pasien tidak datang'." +
                        "\n5. Jika sudah selesai melayani pasien, tekan tombol 'Selesaikan Janji Temu' \n",
                modifier = Modifier.padding(8.dp),
                textAlign = TextAlign.Left,
            )

            Text(
                text = "Melihat Jadwal",
                modifier = Modifier.padding(8.dp),
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "1. Buka halaman 'Jadwal' dengan menekan tombol di bawah.\n2. Pilih poli yang diinginkan dengan menekan salah satu dari tiga tombol di atas." +
                        "\n3. Pilih tanggal yang diinginkan menggunakan tombol di kanan atas\n" +
                        "4. Data antrian akan ditampilkan dalam bentuk list.",
                modifier = Modifier.padding(8.dp),
                textAlign = TextAlign.Left,
            )
        }

    }
}


@Composable
fun InfoDokterScreen(
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
            InfoDokter()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PrevRekam() {
    KanadeTheme {
        InfoDokterScreen()
    }
}