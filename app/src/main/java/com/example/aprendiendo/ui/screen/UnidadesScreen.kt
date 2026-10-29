package com.example.aprendiendo.ui.screen

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.example.aprendiendo.model.asignaturas
import com.example.aprendiendo.model.unidades

// eligir la unidad a trabajar
@Composable
fun UnidadesScreen(navController: NavController, indice: Int) {
    Pantalla(titulo = asignaturas[indice].nombre, onVolver = { navController.popBackStack() }) {
        Text("Elige una unidad", color = Gris)
        unidades[indice].forEach { unidad ->
            Tarjeta(onClick = {
                // Solo entra si el docente la asignó
                if (unidad.estado != "No asignada") navController.navigate("contenido/$indice")
            }) {
                Row {
                    Text("Unidad ${unidad.numero}", Modifier.weight(1f), fontWeight = FontWeight.Bold)
                    EtiquetaEstado(unidad.estado)
                }
                Text(unidad.oa, color = Gris)
            }
        }
    }
}