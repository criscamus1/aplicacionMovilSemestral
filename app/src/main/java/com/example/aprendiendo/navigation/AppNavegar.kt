package com.example.aprendiendo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.aprendiendo.ui.screen.ContenidoScreen
import com.example.aprendiendo.ui.screen.DocenteScreen
import com.example.aprendiendo.ui.screen.InicioScreen
import com.example.aprendiendo.ui.screen.LoginScreen
import com.example.aprendiendo.ui.screen.PruebaScreen
import com.example.aprendiendo.ui.screen.RegistroScreen
import com.example.aprendiendo.ui.screen.ResultadoScreen
import com.example.aprendiendo.ui.screen.UtpScreen
import com.example.aprendiendo.viewmodel.PruebaViewModel
import com.example.aprendiendo.viewmodel.UsuarioViewModel

@Composable    // Scaffold -> AppNavegar -> pantallas
fun AppNavegar(
    modifier: Modifier,
    navController: NavHostController,
    usuarioViewModel: UsuarioViewModel,
    pruebaViewModel: PruebaViewModel
) {
    NavHost(
        navController = navController,
        startDestination = "login",
        modifier = modifier
    ) {
        // Acceso
        composable("login") { LoginScreen(navController, usuarioViewModel) }
        composable("registro") { RegistroScreen(navController, usuarioViewModel) }

        // Estudiante
        composable("inicio") { InicioScreen(navController, usuarioViewModel, pruebaViewModel) }
        composable("contenido/{indice}") { ContenidoScreen(navController, leerIndice(it)) }
        composable("prueba/{indice}") { PruebaScreen(navController, pruebaViewModel, leerIndice(it)) }
        composable("resultado/{indice}") { ResultadoScreen(navController, pruebaViewModel, leerIndice(it)) }

        // Docente y UTP
        composable("docente") { DocenteScreen(usuarioViewModel) }
        composable("utp") { UtpScreen() }
    }
}

// Lee el número de asignatura que viene en la ruta (ej: "prueba/2" -> 2)
private fun leerIndice(entrada: NavBackStackEntry): Int =
    entrada.arguments?.getString("indice")?.toInt() ?: 0
