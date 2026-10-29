package com.example.aprendiendo.ui.screen

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.example.aprendiendo.model.actividades
import com.example.aprendiendo.model.asignaturas

// UTP: crea actividades y revisa las de los docentes
@Composable
fun UtpScreen(navController: NavController) {
    Pantalla(titulo = "Jefatura UTP") {
        BotonPrincipal("➕ Crear actividad") { navController.navigate("actividad") }
        Text("Actividades", fontWeight = FontWeight.Bold)
        actividades.forEachIndexed { i, actividad ->
            Tarjeta(onClick = { navController.navigate("revisar/$i") }) {
                Row {
                    Text(actividad.titulo, Modifier.weight(1f), fontWeight = FontWeight.Bold)
                    EtiquetaEstado(actividad.estado)
                }
                Text("${asignaturas[actividad.asignatura].nombre} · Unidad ${actividad.unidad}", color = Gris)
            }
        }
    }
}