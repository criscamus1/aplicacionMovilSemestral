package com.example.aprendiendo.ui.screen

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.aprendiendo.model.asignaturas

// JEFATURA UTP: ve la lista de contenidos y su estado
@Composable
fun UtpScreen() {
    Pantalla(titulo = "Jefatura UTP") {
        Text("Lista de contenidos · 1.º Básico", color = Gris)
        asignaturas.forEach { asignatura ->
            Tarjeta {
                Row {
                    Text("${asignatura.emoji} ${asignatura.nombre}", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold)
                    Text(asignatura.estadoUtp, color = Azul, fontWeight = FontWeight.Bold)
                }
                Text("${asignatura.oa} · Prof. Andrea R.", color = Gris, fontSize = 13.sp)
            }
        }
    }
}
