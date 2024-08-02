package com.projects.kanade

import JadwalScreen
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Accessibility
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.projects.kanade.ui.screen.home.pasien.antrian.AntrianScreen
import com.projects.kanade.ui.screen.home.pasien.daftar.DaftarScreen
import com.projects.kanade.ui.screen.home.pasien.rekam.RekamScreen
import com.projects.kanade.ui.screen.home.profile.ProfileScreen
import com.projects.kanade.ui.screen.navigation.Screen
import com.projects.kanade.ui.theme.KanadeTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.currentBackStackEntryAsState
import com.projects.kanade.data.LoggedUser
import com.projects.kanade.ui.screen.home.admin.editakun.AkunTerpilihScreen
import com.projects.kanade.ui.screen.home.admin.editakun.EditAkunPrev
import com.projects.kanade.ui.screen.home.admin.editakun.EditAkunScreen
import com.projects.kanade.ui.screen.home.admin.editakun.TerpilihEditScreen
import com.projects.kanade.ui.screen.home.admin.editantri.AddAntrianScreen
import com.projects.kanade.ui.screen.home.admin.editjadwal.EditJadwalPrev
import com.projects.kanade.ui.screen.home.admin.editjadwal.EditJadwalScreen
import com.projects.kanade.ui.screen.home.admin.infoadmin.InfoAdminScreen
import com.projects.kanade.ui.screen.home.admin.simulator.SimulatorScreen
import com.projects.kanade.ui.screen.home.dokter.cekantri.AntrianDokterScreen
import com.projects.kanade.ui.screen.home.dokter.cekjadwal.JadwalDokterScreen
import com.projects.kanade.ui.screen.home.dokter.informasidokter.InfoDokterScreen
import com.projects.kanade.ui.screen.home.pasien.daftar.TerdaftarScreen
import com.projects.kanade.ui.screen.home.pasien.daftar.pertanyaan.GangguanNafasCheck
import com.projects.kanade.ui.screen.home.pasien.daftar.pertanyaan.PertanyaanScreen
import com.projects.kanade.ui.screen.landing.login.LoginPage
import com.projects.kanade.ui.screen.landing.login.LoginState
import com.projects.kanade.ui.screen.landing.register.RegisterPage
import com.projects.kanade.ui.screen.navigation.NavigationItem
import kotlinx.coroutines.launch

@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavigationBar (
      modifier = modifier, containerColor = MaterialTheme.colorScheme.background,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItems = listOf(
        NavigationItem(
            title = stringResource(R.string.menu_antrian),
            icon = Icons.Default.Home,
            screen = Screen.Antri,
        ),
        NavigationItem(
            title = stringResource(R.string.menu_daftar),
            icon = Icons.Default.EditCalendar,
            screen = Screen.Daftar,
        ),
        NavigationItem(
            title = stringResource(R.string.menu_profil),
            icon = Icons.Default.AccountCircle,
            screen = Screen.Profile,
        ),
            NavigationItem(
                title = "Bantuan",
                icon = Icons.Default.QuestionMark,
                screen = Screen.Rekam,
            ),
    )

        navigationItems.map {item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                label = {
                    Text(item.title)
                },
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }

    }
}

@Composable
fun BottomBarDokter(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavigationBar (
        modifier = modifier, containerColor = MaterialTheme.colorScheme.background
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.menu_antrian),
                icon = Icons.Default.Home,
                screen = Screen.AntriDokter,
            ),
            NavigationItem(
                title = stringResource(R.string.menu_jadwal_dokter),
                icon = Icons.Default.EditCalendar,
                screen = Screen.JadwalDokter,
            ),
            NavigationItem(
                title = stringResource(R.string.menu_profil),
                icon = Icons.Default.AccountCircle,
                screen = Screen.Profile,
            ),
            NavigationItem(
                title = "Bantuan",
                icon = Icons.Default.QuestionMark,
                screen = Screen.InfoDokter,
            ),
        )

        navigationItems.map {item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                label = {
                    Text(item.title)
                },
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }

    }
}

@Composable
fun BottomBarAdmin(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavigationBar (
        modifier = modifier, containerColor = MaterialTheme.colorScheme.background
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItems = listOf(
            NavigationItem(
                title = "Daftar",
                icon = Icons.Default.Home,
                screen = Screen.AntriAdmin,
            ),
            NavigationItem(
                title = stringResource(R.string.menu_jadwal_dokter),
                icon = Icons.Default.EditCalendar,
                screen = Screen.JadwalAdmin,
            ),
            NavigationItem(
                title = "Akun",
                icon = Icons.Default.Accessibility,
                screen = Screen.AkunAdmin,
            ),
            NavigationItem(
                title = "Profil",
                icon = Icons.Default.AccountCircle,
                screen = Screen.Profile,
            ),
            NavigationItem(
                title = "Bantuan",
                icon = Icons.Default.QuestionMark,
                screen = Screen.InfoAdmin,
            ),
            /*NavigationItem(
                title = "Sim",
                icon = Icons.Default.Assessment,
                screen = Screen.Simulator,
            ),*/
        )

        navigationItems.map {item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                label = {
                    Text(item.title)
                },
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar(onMenuClick: () -> Unit) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = {
                onMenuClick()
            }) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = stringResource(R.string.menu)
                )
            }
        },
        title = {
            Text(stringResource(R.string.app_name))
        },
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun KanadeApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current


    Scaffold (
        bottomBar = {
            if ((currentRoute != Screen.Login.route) && (currentRoute != Screen.Signup.route) && (currentRoute != Screen.Jadwal.route)) {
                when (LoggedUser.accesslevel) {
                    1 -> {
                        BottomBar(navController)
                    }
                    2 -> {
                        BottomBarDokter(navController)
                    }
                    3 -> {
                        BottomBarAdmin(navController)
                    }
                }
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = modifier,
    ) {innerPadding ->
        NavHost(
        navController = navController,
        startDestination = Screen.Login.route,
        modifier = Modifier.padding(innerPadding)
    )
        {
            composable(Screen.Antri.route) {
                AntrianScreen(
                    LoggedUser.nama,
                    refreshPage = { navController.navigate(Screen.Antri.route) },
                    kehadiranDiterima = {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Kehadiran telah diterima, mohon tunggu panggilan!"
                            )
                        }
                    },
                    tidakCancel = {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Batas waktu pembatalan sudah lewat!"
                            )
                        }
                    },
                )
                }
            composable(Screen.Daftar.route) {
                DaftarScreen( navigateToDaftar = {
                    //navController.navigate(Screen.Gangguan.route)
                    navController.navigate(Screen.Jadwal.route)
                })
            }
            composable(Screen.Jadwal.route) {
                JadwalScreen(
                    navigateToHome = {
                            navController.popBackStack()
                            navController.navigate(Screen.Proses.route){
                                popUpTo(navController.graph.id) {
                                    inclusive = true
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                                     },
                    backToDaftar = {
                        navController.popBackStack()
                        navController.navigate(Screen.Daftar.route)
                    },
                    sesiPenuh = {
                        //navController.navigate(Screen.Jadwal.route)

                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Sesi penuh, mohon ganti pilihan sesi!"
                            )
                        }
                    },
                    jamInvalid = {
                        //navController.navigate(Screen.Jadwal.route)

                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Jam Invalid!"
                            )
                        }
                    },
                    tanggalInvalid = {
                        //navController.navigate(Screen.Jadwal.route)

                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Mohon pilih tanggal setelah hari ini!"
                            )
                        }
                    }
                )
            }
            composable(Screen.Gangguan.route) {
                GangguanNafasCheck(
                    gangguanNafas = { navController.navigate(Screen.Pertanyaan.route) },
                    tidakGangguan = { navController.navigate(Screen.Jadwal.route) })
            }
            composable(Screen.Pertanyaan.route) {
                PertanyaanScreen( navigateToNext = { navController.navigate(Screen.Jadwal.route) } )
            }
            composable(Screen.Proses.route) {
                TerdaftarScreen( toAntrian = { navController.navigate(Screen.Antri.route) })
            }
            composable(Screen.Rekam.route) {
                RekamScreen()
            }
            composable(Screen.InfoDokter.route) {
                InfoDokterScreen()
            }
            composable(Screen.InfoAdmin.route) {
                InfoAdminScreen()
            }
            composable(Screen.Profile.route) {
                ProfileScreen(
                    navigateToLogin = {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(navController.graph.id) {
                                inclusive = true
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
            composable(Screen.Login.route) {
                LoginPage(
                    navigateToHome =
                    {
                        navController.popBackStack()
                            when (LoggedUser.accesslevel) {
                            1 -> {
                                navController.navigate(Screen.Antri.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }

                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = "Selamat datang ${LoggedUser.nama}!",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            }
                            2 -> {
                                navController.navigate(Screen.AntriDokter.route){
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                            3 -> {
                                navController.navigate(Screen.AntriAdmin.route){
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                    },
                    navigateToRegister = {
                        navController.navigate(Screen.Signup.route)
                                         },
                    loginIncorrect = {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Kredensial login salah, silakan coba lagi!",
                                duration = SnackbarDuration.Short
                            )
                        }

                        /*navController.navigate(Screen.Login.route){
                            popUpTo(navController.graph.id) {
                                inclusive = true
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }*/
                    },
                    loginFailed = {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Login gagal! Silahkan tekan ulang tombol!",
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                )
            }
            composable(Screen.Signup.route) {
                RegisterPage(
                    navigateToLogin = {
                        navController.popBackStack()
                        navController.navigate(Screen.Login.route)

                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Terdaftar!"
                                )
                            }
                                      },
                    backToLogin = {
                        navController.navigate(Screen.Login.route) },
                    registrationInvalid = {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Data tidak valid/Password kurang dari 6 digit!",
                                duration = SnackbarDuration.Short
                            )
                        }
                },
                    usernameInvalid = {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Data username telah digunakan/Tidak valid!",
                                duration = SnackbarDuration.Short
                            )
                        }
                    },
                    passwordInvalid = {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Password tidak valid!",
                                duration = SnackbarDuration.Short
                            )
                        }
                    },
                    passandusernameInvalid = {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Password tidak valid dan username tidak valid!",
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                )
            }
            composable(Screen.AntriDokter.route) {
                AntrianDokterScreen(
                    changePoli = { navController.navigate(Screen.AntriDokter.route) },
                    finishAntrian = { navController.navigate(Screen.AntriDokter.route) }
                )
            }
            composable(Screen.JadwalDokter.route) {
                JadwalDokterScreen(
                    pilihTanggal = { navController.navigate(Screen.JadwalDokter.route) },
                    changePoli = { navController.navigate(Screen.JadwalDokter.route) }
                )
            }
            composable(Screen.AntriAdmin.route) {
                AddAntrianScreen(
                    antrianOffline = {
                    navController.navigate(Screen.AntriAdmin.route)
                },
                    antrianOfflinemsg = {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Terdaftar!",
                                duration = SnackbarDuration.Short
                            )
                        }
                    },
                    inputInvalid = {
                        scope.launch {
                        snackbarHostState.showSnackbar(
                            message = "Input data tidak valid!",
                            duration = SnackbarDuration.Short
                        )
                    }},
                    sesiPenuh = {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Sesi penuh! Silahkan datang besok!",
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                    )
            }
            composable(Screen.AkunAdmin.route) {
                EditAkunScreen( navigatetoEdit = { navController.navigate(Screen.EditAkun.route) })
            }
            composable(Screen.JadwalAdmin.route) {
                EditJadwalScreen(
                    pilihTanggal = { navController.navigate(Screen.JadwalAdmin.route) },
                    changePoli = { navController.navigate(Screen.JadwalAdmin.route) }
                )
            }
            composable(Screen.EditAkun.route) {
                AkunTerpilihScreen(
                    editAkun = { navController.navigate(Screen.AkunTerpilih.route) },
                    back = { navController.navigate(Screen.AkunAdmin.route) })
            }
            composable(Screen.AkunTerpilih.route) {
                TerpilihEditScreen(
                    finishEdit = { navController.navigate(Screen.EditAkun.route) },
                    back = { navController.navigate(Screen.EditAkun.route) },
                    inputInvalid = {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Input kosong!",
                                duration = SnackbarDuration.Short
                            )
                        }
                    })
            }
            composable(Screen.Simulator.route) {
                SimulatorScreen()
            }
        }
    }
}

@Preview
@Composable
fun HomePreview() {
    KanadeTheme {
        // KanadeApp()
    }
}