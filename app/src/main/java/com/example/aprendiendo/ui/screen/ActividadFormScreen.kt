package com.example.aprendiendo.ui.screen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
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

// Docente y UTP: crear una actividad
@Composable
fun ActividadFormScreen(navController: NavController) {
    val context = LocalContext.current
    var asignatura by remember { mutableStateOf("Matemáticas") }
    var unidad by remember { mutableStateOf("Unidad 1") }
    var abierto by remember { mutableStateOf(false) }   // ¿lista de unidades abierta?
    var titulo by remember { mutableStateOf("") }
    var pregunta by remember { mutableStateOf("") }
    var alternativas by remember { mutableStateOf("") }
    var correcta by remember { mutableStateOf("") }

    Pantalla(titulo = "Crear actividad", onVolver = { navController.popBackStack() }) {
        // Asignatura
        Text("Asignatura", fontWeight = FontWeight.Bold)
        listOf("Matemáticas", "Lenguaje", "Ciencias", "Historia").chunked(2).forEach { fila ->
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                fila.forEach { nombre ->
                    Opcion(nombre, asignatura == nombre, Modifier.weight(1f)) { asignatura = nombre }
                }
            }
        }

        // Escoger unidad
        Box {
            OutlinedTextField(
                value = unidad,
                onValueChange = {},
                readOnly = true,
                label = { Text("Escoger unidad") },
                trailingIcon = { Icon(Icons.Filled.ArrowDropDown, null) },
                modifier = Modifier.fillMaxWidth()
            )
            Box(Modifier.matchParentSize().clickable { abierto = true })
            DropdownMenu(expanded = abierto, onDismissRequest = { abierto = false }) {
                listOf("Unidad 1", "Unidad 2", "Unidad 3").forEach { opcion ->
                    DropdownMenuItem(text = { Text(opcion) }, onClick = { unidad = opcion; abierto = false })
                }
            }
        }
        CampoTexto(titulo, { titulo = it }, "Título de la actividad", null)
        CampoTexto(pregunta, { pregunta = it }, "Pregunta", null)
        CampoTexto(alternativas, { alternativas = it }, "Alternativas (separadas por coma)", null)
        CampoTexto(correcta, { correcta = it }, "Respuesta correcta", null)

        BotonPrincipal("Guardar actividad") {
            Toast.makeText(context, "Para una futura actualización", Toast.LENGTH_SHORT).show()
        }
    }
}