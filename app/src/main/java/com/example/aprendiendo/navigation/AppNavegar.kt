package com.example.aprendiendo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.aprendiendo.ui.screen.*
import com.example.aprendiendo.viewmodel.PruebaViewModel
import com.example.aprendiendo.viewmodel.UsuarioViewModel

@Composable
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
        composable("unidades/{indice}") { UnidadesScreen(navController, leerIndice(it)) }
        composable("contenido/{indice}") { ContenidoScreen(navController, leerIndice(it)) }
        composable("prueba/{indice}") { PruebaScreen(navController, pruebaViewModel, leerIndice(it)) }
        composable("resultado/{indice}") { ResultadoScreen(navController, pruebaViewModel, leerIndice(it)) }
        // Docente
        composable("docente") { DocenteScreen(navController, usuarioViewModel) }
        composable("actividad") { ActividadFormScreen(navController) }   // también lo usa UTP
        composable("informe/{indice}") { InformeScreen(navController, leerIndice(it)) }
        composable("guia") { EnviarGuiaScreen(navController) }

        // UTP
        composable("utp") { UtpScreen(navController) }
        composable("revisar/{indice}") { RevisarActividadScreen(navController, leerIndice(it)) }
        composable("administrar") { AdministrarScreen(navController) }
        composable("unidad/{indice}/{numero}") {
            UnidadFormScreen(navController, leerIndice(it), it.arguments?.getString("numero")?.toInt() ?: 0)
        }
        composable("solicitudes") { SolicitudesScreen(navController) }
        composable("solicitud/{indice}") { RevisarSolicitudScreen(navController, leerIndice(it)) }
        composable("historial") { HistorialScreen(navController) }
    }
}
private fun leerIndice(entrada: NavBackStackEntry): Int =
    entrada.arguments?.getString("indice")?.toInt() ?: 0