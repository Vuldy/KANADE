package com.projects.kanade.ui.screen.home.admin.infoadmin

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.projects.kanade.ui.theme.KanadeTheme

@Composable
fun InfoAdmin(
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
                text = "Mendaftarkan Pasien Offline",
                modifier = Modifier.padding(8.dp),
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "1. Buka halaman 'Daftar' dengan menekan tombol di bawah.\n2. Masukan data pasien seperti nama, umur, keluhan dan jenis kelamin." +
                        "\n3. Pilih poli yang diinginkan pasien\n" +
                        "4. Pasien akan terdaftar pada hari yang sama dengan pendaftaran." +
                        "\n5. Jika sesi penuh, pasien akan didaftarkan ke sesi selanjutnya. \n" +
                        "6. Pasien dipersilahkan untuk menunggu di ruang tunggu.",
                modifier = Modifier.padding(8.dp),
                textAlign = TextAlign.Left,
            )

            Text(
                text = "Menghapus Antrian",
                modifier = Modifier.padding(8.dp),
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "1. Buka halaman 'Jadwal' dengan menekan tombol di bawah.\n2. Pilih poli yang diinginkan dengan menekan salah satu dari tiga tombol di atas." +
                        "\n3. Pilih tanggal yang diinginkan menggunakan tombol di kanan atas\n" +
                        "4. Data antrian akan ditampilkan dalam bentuk list.\n"+
                        "5. Tekan antrian yang ingin dihapus\n" +
                        "6. Konfirmasi antrian yang ingin dihapus\n",
                modifier = Modifier.padding(8.dp),
                textAlign = TextAlign.Left,
            )

            Text(
                text = "Mengubah Data Pengguna",
                modifier = Modifier.padding(8.dp),
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "1. Buka halaman 'Akun' dengan menekan tombol di bawah.\n2. Tekan akun yang ingin diubah" +
                        "\n3. Tekan tombol 'Edit'\n" +
                        "4. Ubah data yang ingin diubah\n",
                modifier = Modifier.padding(8.dp),
                textAlign = TextAlign.Left,
            )
        }

    }
}


@Composable
fun InfoAdminScreen(
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
            InfoAdmin()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PrevRekam() {
    KanadeTheme {
        InfoAdminScreen()
    }
}