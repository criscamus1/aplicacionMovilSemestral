package com.example.aprendiendo.ui.screen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.aprendiendo.model.*
import com.example.aprendiendo.viewmodel.UsuarioViewModel

@Composable
fun DocenteScreen(navController: NavController, viewModel: UsuarioViewModel) {
    val estado by viewModel.estado.collectAsState()
    Pantalla(titulo = "Hola, Prof. ${estado.nombreSesion.substringBefore(" ")}") {
        Text("1.º Básico A", color = Gris)
        BotonPrincipal("➕ Crear actividad") { navController.navigate("actividad") }
        OutlinedButton(onClick = { navController.navigate("guia") }, modifier = Modifier.fillMaxWidth()) {
            Text("📤 Enviar guía a UTP")
        }
        // Puntajes al tocar el docente
        Text("Puntajes · Matemáticas", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text("Toca un estudiante para ver su informe", color = Gris, fontSize = 13.sp)
        estudiantes.forEachIndexed { i, estudiante ->
            Tarjeta(onClick = { navController.navigate("informe/$i") }) {
                Row {
                    Text(estudiante.nombre, Modifier.weight(1f), fontWeight = FontWeight.Bold)
                    val respuestas = estudiante.respuestas
                    Text(if (respuestas == null) "Pendiente ›" else "${puntajeMatematicas(respuestas)}/3 ›")
                }
            }
        }
        // Contenido de cada asignatura
        Text("Contenido", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        asignaturas.forEach { asignatura ->
            Tarjeta(asignatura.color) {
                Text("${asignatura.emoji}  ${asignatura.nombre}", fontWeight = FontWeight.Bold)
                Text(asignatura.oa, color = Gris)
            }
        }
    }
}

// informe de alumno
@Composable
fun InformeScreen(navController: NavController, indice: Int) {
    val context = LocalContext.current
    val aviso = { Toast.makeText(context, "aun nada", Toast.LENGTH_SHORT).show() }
    val estudiante = estudiantes[indice]
    val respuestas = estudiante.respuestas
    val preguntas = asignaturas[0].preguntas
    var observacion by remember { mutableStateOf("") }

    Pantalla(titulo = "Informe · ${estudiante.nombre}", onVolver = { navController.popBackStack() }) {
        if (respuestas == null) {
            Text("Este estudiante aún no rinde la prueba.", fontSize = 18.sp)
        } else {
            Text("Matemáticas · Unidad 2 · ${puntajeMatematicas(respuestas)}/3", color = Gris)
            EtiquetaEstado("Pendiente de validación")
            Tarjeta(VerdeClaro) {
                Text("Fortalezas", color = Verde, fontWeight = FontWeight.Bold)
                preguntas.forEachIndexed { n, pregunta ->
                    if (respuestas[n] == pregunta.correcta) Text("• ${pregunta.texto}")
                }
            }
            Tarjeta(RojoClaro) {
                Text("Necesita refuerzo", color = Rojo, fontWeight = FontWeight.Bold)
                preguntas.forEachIndexed { n, pregunta ->
                    if (respuestas[n] != pregunta.correcta) Text("• ${pregunta.texto} (respondió ${respuestas[n]})")
                }
            }

            CampoTexto(observacion, { observacion = it }, "Observación del docente", null)
            Text("🔒 El estudiante no ve este informe", fontSize = 13.sp, color = Gris)

            BotonPrincipal("Validar informe", onClick = aviso)
            OutlinedButton(onClick = aviso, modifier = Modifier.fillMaxWidth()) { Text("Descargar informe") }
        }
    }
}

// enviar guia
@Composable
fun EnviarGuiaScreen(navController: NavController) {
    val context = LocalContext.current
    val aviso = { Toast.makeText(context, "nada aun ", Toast.LENGTH_SHORT).show() }
    var asignatura by remember { mutableStateOf("Matemáticas") }
    var unidad by remember { mutableStateOf("Unidad 1") }
    var abierto by remember { mutableStateOf(false) }   // ¿lista de unidades abierta?
    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }

    Pantalla(titulo = "Enviar guía", onVolver = { navController.popBackStack() }) {
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
        CampoTexto(titulo, { titulo = it }, "Título de la guía", null)
        CampoTexto(descripcion, { descripcion = it }, "Descripción", null)
        OutlinedButton(onClick = aviso, modifier = Modifier.fillMaxWidth()) { Text("📎 Adjuntar archivo") }
        Text("UTP debe aceptar la guía antes de que se use con el curso.", fontSize = 13.sp, color = Gris)
        BotonPrincipal("Enviar a UTP", onClick = aviso)
    }
}