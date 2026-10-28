package com.example.aprendiendo.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.aprendiendo.viewmodel.UsuarioViewModel

@Composable
fun RegistroScreen(
    navController: NavController,
    viewModel: UsuarioViewModel
) {
    val estado by viewModel.estado.collectAsState()
    val context = LocalContext.current

    Pantalla(titulo = "Crear cuenta", onVolver = { navController.popBackStack() }) {
        // Campos del formulario (cada uno muestra su error debajo)
        CampoTexto(estado.nombre, viewModel::onNombreChange, "Nombre", estado.errores.nombre)
        CampoTexto(estado.correo, viewModel::onCorreoChange, "Correo electrónico", estado.errores.correo)
        CampoTexto(estado.clave, viewModel::onClaveChange, "Contraseña", estado.errores.clave, esClave = true)
        // Elegir perfil
        Text("Perfil", fontWeight = FontWeight.Bold)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("Estudiante", "Docente", "UTP").forEach { opcion ->
                Opcion(opcion, estado.perfil == opcion, Modifier.weight(1f)) { viewModel.onPerfilChange(opcion) }
            }
        }

        // Botón de registrar si salio bien se podria ingresar
        BotonPrincipal("Registrarme") {
            viewModel.registrar {
                Toast.makeText(context, "Cuenta creada. Ya puedes ingresar.", Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            }
        }
    }
}
