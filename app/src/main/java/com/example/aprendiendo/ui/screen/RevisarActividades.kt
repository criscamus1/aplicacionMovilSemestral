package com.example.aprendiendo.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.aprendiendo.model.actividades
import com.example.aprendiendo.model.asignaturas
import com.example.aprendiendo.model.unidades
// UTP: revisa una actividad
@Composable
fun RevisarActividadScreen(navController: NavController, indice: Int) {
    val context = LocalContext.current
    val aviso = { Toast.makeText(context, "Para una futura actualización", Toast.LENGTH_SHORT).show() }
    val actividad = actividades[indice]
    val unidad = unidades[actividad.asignatura][actividad.unidad - 1]
    var observacion by remember { mutableStateOf("") }

    Pantalla(titulo = "Revisar actividad", onVolver = { navController.popBackStack() }) {
        Text(actividad.titulo, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        EtiquetaEstado(actividad.estado)
        // ira la actividad
        Tarjeta {
            Text("${asignaturas[actividad.asignatura].nombre} · Unidad ${actividad.unidad}", fontWeight = FontWeight.Bold)
            Text(unidad.oa, color = Gris)
        }
        // La pregunta y su respuesta correcta
        Tarjeta {
            Text(actividad.pregunta.texto, fontWeight = FontWeight.Bold)
            Text("Alternativas: ${actividad.pregunta.opciones.joinToString(" · ")}")
            Text("Respuesta correcta: ${actividad.pregunta.correcta}", color = Verde, fontWeight = FontWeight.Bold)
        }
        CampoTexto(observacion, { observacion = it }, "Observación", null)
        BotonPrincipal("Aprobar", onClick = aviso)
        OutlinedButton(onClick = aviso, modifier = Modifier.fillMaxWidth()) { Text("Rechazar") }
    }
}