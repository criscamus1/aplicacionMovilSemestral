package com.example.aprendiendo.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.aprendiendo.model.asignaturas

@Composable
fun UnidadFormScreen(navController: NavController, indice: Int) {
    val context = LocalContext.current
    val aviso = { Toast.makeText(context, "Para una futura actualización", Toast.LENGTH_SHORT).show() }
    val existente = asignaturas.getOrNull(indice)
    // formulario
    var asignatura by remember { mutableStateOf(existente?.nombre ?: "Matemáticas") }
    var unidad by remember { mutableStateOf("") }
    var codigoOa by remember { mutableStateOf(existente?.oa ?: "") }
    var explicacion by remember { mutableStateOf(existente?.explicacion ?: "") }
    var ejemplo by remember { mutableStateOf(existente?.ejemplo ?: "") }
    var origen by remember { mutableStateOf("Propio") }

    Pantalla(
        titulo = if (existente == null) "Agregar unidad" else "Modificar unidad",
        onVolver = { navController.popBackStack() }
    ) {
        // Asignatura
        Text("Asignatura", fontWeight = FontWeight.Bold)
        listOf("Matemáticas", "Lenguaje", "Ciencias", "Historia y Geografía").chunked(2).forEach { fila ->
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                fila.forEach { nombre ->
                    Opcion(nombre, asignatura == nombre, Modifier.weight(1f)) { asignatura = nombre }
                }
            }
        }
// Datos de la unidad
        CampoTexto(unidad, { unidad = it }, "Nombre de la unidad", null)
        CampoTexto(codigoOa, { codigoOa = it }, "Objetivo de aprendizaje (ej: OA 7)", null)
        CampoTexto(explicacion, { explicacion = it }, "Explicación escrita", null)
        CampoTexto(ejemplo, { ejemplo = it }, "Ejemplo guiado", null)
        // Origen del material
        Text("Origen del material", fontWeight = FontWeight.Bold)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("Propio", "Público", "Autorizado").forEach { opcion ->
                Opcion(opcion, origen == opcion, Modifier.weight(1f)) { origen = opcion }
            }
        }
        // Recursos
        OutlinedButton(onClick = aviso, modifier = Modifier.fillMaxWidth()) { Text("🔊 Agregar audio") }
        OutlinedButton(onClick = aviso, modifier = Modifier.fillMaxWidth()) { Text("🖼️ Agregar imagen") }
        // Botón: guardar
        BotonPrincipal(if (existente == null) "Guardar unidad" else "Guardar cambios", onClick = aviso)
    }
}
