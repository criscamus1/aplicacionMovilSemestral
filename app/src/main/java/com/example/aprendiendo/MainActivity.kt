package com.example.aprendiendo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.aprendiendo.navigation.AppNavegar
import com.example.aprendiendo.ui.screen.MpazTema
import com.example.aprendiendo.ui.screen.rutaInicio
import com.example.aprendiendo.viewmodel.PruebaViewModel
import com.example.aprendiendo.viewmodel.UsuarioViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MpazTema {

                val navController = rememberNavController()

                // ViewModel
                val usuarioViewModel: UsuarioViewModel = viewModel()
                val pruebaViewModel: PruebaViewModel = viewModel()
                val usuario by usuarioViewModel.estado.collectAsState()

                // Ruta en la que estamos ahora y donde se usa para esconder la barra en login y registro
                val rutaActual = navController.currentBackStackEntryAsState().value?.destination?.route
                val mostrarBarra = rutaActual != "login" && rutaActual != "registro"

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (mostrarBarra) {
                            BottomAppBar {
                                Row(
                                    Modifier.fillMaxSize().padding(start = 35.dp, end = 35.dp),
                                    Arrangement.SpaceBetween,
                                    Alignment.CenterVertically
                                ) {
                                    // Botón de volver
                                    IconButton(onClick = { navController.popBackStack(rutaInicio(usuario.perfilSesion), inclusive = false) }) {
                                        Icon(
                                            imageVector = Icons.Default.Home,
                                            contentDescription = "Inicio",
                                            modifier = Modifier.size(32.dp)
                                        )
                                    }

                                    // Botón de cerrar sesion
                                    IconButton(onClick = {
                                        usuarioViewModel.limpiar()
                                        pruebaViewModel.limpiar()
                                        navController.navigate("login") { popUpTo(navController.graph.id) { inclusive = true } }
                                    }) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                                            contentDescription = "Salir",
                                            modifier = Modifier.size(32.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                ) { innerPadding ->
                    AppNavegar(Modifier.padding(innerPadding), navController, usuarioViewModel, pruebaViewModel)
                }
            }
        }
    }
}
