package com.example.aprendiendo.ui.screen

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.aprendiendo.viewmodel.UsuarioViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: UsuarioViewModel
) {
    val estado by viewModel.estado.collectAsState()
    val context = LocalContext.current
    Pantalla(titulo = "MPAZ PRO 1.º") {
        Text("Apoyo didáctico · 1.º Básico", color = Gris, fontSize = 17.sp)
        // Campo correo
        CampoTexto(estado.correo, viewModel::onCorreoChange, "Correo electrónico", estado.errores.correo)
        // Campo clave
        CampoTexto(estado.clave, viewModel::onClaveChange, "Contraseña", estado.errores.clave, esClave = true)
        // Mensaje animado si el correo o la clave no existen
        AnimatedVisibility(visible = estado.mensaje != null) {
            Text(estado.mensaje ?: "", color = Rojo)
        }
        // Mientras carga
        if (estado.cargando) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                CircularProgressIndicator()
                Text("Cargando datos...", color = Gris)
            }
        }
        // Botón: ingresar
        BotonPrincipal("Ingresar", habilitado = !estado.cargando) {
            viewModel.login { perfil ->
                // Cada perfil va a su propia pantalla
                navController.navigate(rutaInicio(perfil)) { popUpTo("login") { inclusive = true } }
            }
        }
        TextButton(onClick = {
            viewModel.limpiar()
            navController.navigate("registro")
        }) { Text("¿No tienes cuenta? Regístrate") }
        TextButton(onClick = {
            Toast.makeText(context, "Para una futura actualización", Toast.LENGTH_SHORT).show()
        }) { Text("¿Olvidaste tu contraseña?") }

        Text("Cuentas de prueba (clave 123456): estudiante@prueba.com · docente@prueba.com · utp@prueba.com", fontSize = 12.sp, color = Gris)
        Text("Escuela Escritora Marcela Paz · datos ficticios", fontSize = 12.sp, color = Gris)
    }
}

// Pantalla de inicio
fun rutaInicio(perfil: String): String = when (perfil) {
    "Docente" -> "docente"
    "UTP" -> "utp"
    else -> "inicio"
}
