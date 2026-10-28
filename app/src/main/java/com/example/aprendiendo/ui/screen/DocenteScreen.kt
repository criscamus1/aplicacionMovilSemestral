package com.example.aprendiendo.ui.screen

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.aprendiendo.model.asignaturas
import com.example.aprendiendo.model.puntajesCurso
import com.example.aprendiendo.viewmodel.UsuarioViewModel

// DOCENTE: ve el contenido y quién sacó qué puntaje (datos de ejemplo)
@Composable
fun DocenteScreen(viewModel: UsuarioViewModel) {
    val estado by viewModel.estado.collectAsState()

    Pantalla(titulo = "Hola, Prof. ${estado.nombreSesion.substringBefore(" ")}") {
        Text("1.º Básico A · datos ficticios", color = Gris)

        // Contenido de cada asignatura
        Text("Contenido", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        asignaturas.forEach { asignatura ->
            Tarjeta(asignatura.color) {
                Text("${asignatura.emoji}  ${asignatura.nombre}", fontWeight = FontWeight.Bold)
                Text(asignatura.oa, color = Gris)
                Text(asignatura.explicacion)
            }
        }

        // Puntajes del curso
        Text("Puntajes · Matemáticas", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        puntajesCurso.forEach { (nombre, nota) ->
            Tarjeta {
                Row {
                    Text(nombre, modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold)
                    Text(nota)
                }
            }
        }
    }
}
