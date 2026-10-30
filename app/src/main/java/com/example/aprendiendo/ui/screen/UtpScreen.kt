package com.example.aprendiendo.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.aprendiendo.model.*

@Composable
fun UtpScreen(navController: NavController) {
    val pendientes = solicitudes.count { it.estado == "Pendiente" }
    Pantalla(titulo = "Jefatura UTP") {
        Text("1.º Básico", color = Gris)
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Acceso("📚", "Administrar asignaturas", Modifier.weight(1f)) { navController.navigate("administrar") }
            Acceso("📥", "Solicitudes ($pendientes)", Modifier.weight(1f)) { navController.navigate("solicitudes") }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Acceso("➕", "Crear actividad", Modifier.weight(1f)) { navController.navigate("actividad") }
            Acceso("📜", "Historial", Modifier.weight(1f)) { navController.navigate("historial") }
        }

        // Actividades para revisar
        Text("Actividades", fontSize = 19.sp, fontWeight = FontWeight.Bold)
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

// Un acceso a menu
@Composable
private fun Acceso(icono: String, texto: String, modifier: Modifier, onClick: () -> Unit) {
    Box(modifier) {
        Tarjeta(onClick = onClick) {
            Text(icono, fontSize = 24.sp)
            Text(texto, fontWeight = FontWeight.Bold)
        }
    }
}

// revisar
@Composable
fun RevisarActividadScreen(navController: NavController, indice: Int) {
    val context = LocalContext.current
    val aviso = { Toast.makeText(context, "nada aun", Toast.LENGTH_SHORT).show() }

    val actividad = actividades[indice]
    val unidad = unidades[actividad.asignatura][actividad.unidad - 1]
    var observacion by remember { mutableStateOf("") }

    Pantalla(titulo = "Revisar actividad", onVolver = { navController.popBackStack() }) {
        Text(actividad.titulo, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        EtiquetaEstado(actividad.estado)

        Tarjeta {
            Text("${asignaturas[actividad.asignatura].nombre} · Unidad ${actividad.unidad}", fontWeight = FontWeight.Bold)
            Text(unidad.oa, color = Gris)
        }
        Tarjeta {
            Text(actividad.pregunta.texto, fontWeight = FontWeight.Bold)
            Text("Alternativas: ${actividad.pregunta.opciones.joinToString(" · ")}")
            Text("Respuesta correcta: ${actividad.pregunta.correcta}", color = Verde, fontWeight = FontWeight.Bold)
        }
        CampoTexto(observacion, { observacion = it }, "Observación", null)
        when (actividad.estado) {
            "En revisión" -> {
                BotonPrincipal("Aprobar", onClick = aviso)
                OutlinedButton(onClick = aviso, modifier = Modifier.fillMaxWidth()) { Text("Rechazar") }
            }
            "Aprobada" -> {
                BotonPrincipal("Publicar", onClick = aviso)
                OutlinedButton(onClick = aviso, modifier = Modifier.fillMaxWidth()) { Text("Deshabilitar") }
            }
            "Publicada" -> {
                OutlinedButton(onClick = aviso, modifier = Modifier.fillMaxWidth()) { Text("Deshabilitar") }
            }
            else -> Text("Esta actividad fue rechazada.", color = Gris)
        }
    }
}
@Composable
fun AdministrarScreen(navController: NavController) {
    Pantalla(titulo = "Administrar", onVolver = { navController.popBackStack() }) {
        Text("Asignaturas y unidades · 1.º Básico", color = Gris)

        asignaturas.forEachIndexed { i, asignatura ->
            Tarjeta {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("${asignatura.emoji} ${asignatura.nombre}", Modifier.weight(1f), fontWeight = FontWeight.Bold)
                    EtiquetaEstado("Habilitada")
                }
                unidades[i].forEach { unidad ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Unidad ${unidad.numero} · ${unidad.oa}", Modifier.weight(1f), fontSize = 14.sp)
                        TextButton(onClick = { navController.navigate("unidad/$i/${unidad.numero}") }) { Text("Editar") }
                    }
                }
                OutlinedButton(onClick = { navController.navigate("unidad/$i/0") }, modifier = Modifier.fillMaxWidth()) {
                    Text("➕ Agregar unidad")
                }
            }
        }
    }
}

// agregar o editar
@Composable
fun UnidadFormScreen(navController: NavController, indice: Int, numero: Int) {
    val context = LocalContext.current
    val aviso = { Toast.makeText(context, "nada aun", Toast.LENGTH_SHORT).show() }
    val unidad = unidades[indice].getOrNull(numero - 1)   // null = unidad nueva
    var nombre by remember { mutableStateOf("") }
    var oa by remember { mutableStateOf(unidad?.oa ?: "") }
    var explicacion by remember { mutableStateOf("") }

    Pantalla(
        titulo = if (unidad == null) "Agregar unidad" else "Editar unidad ${unidad.numero}",
        onVolver = { navController.popBackStack() }
    ) {
        Text("Asignatura: ${asignaturas[indice].nombre}", color = Gris)
        CampoTexto(nombre, { nombre = it }, "Nombre de la unidad", null)
        CampoTexto(oa, { oa = it }, "Objetivo de aprendizaje", null)
        CampoTexto(explicacion, { explicacion = it }, "Explicación escrita", null)
        OutlinedButton(onClick = aviso, modifier = Modifier.fillMaxWidth()) { Text("🔊 Agregar audio") }
        OutlinedButton(onClick = aviso, modifier = Modifier.fillMaxWidth()) { Text("🖼️ Agregar imagen") }
        BotonPrincipal("Guardar unidad", onClick = aviso)
    }
}

// solicitud
@Composable
fun SolicitudesScreen(navController: NavController) {
    Pantalla(titulo = "Solicitudes", onVolver = { navController.popBackStack() }) {
        Text("Guías enviadas por docentes", color = Gris)

        solicitudes.forEachIndexed { i, solicitud ->
            Tarjeta(onClick = { navController.navigate("solicitud/$i") }) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(solicitud.titulo, Modifier.weight(1f), fontWeight = FontWeight.Bold)
                    EtiquetaEstado(solicitud.estado)
                }
                Text("${solicitud.docente} · ${asignaturas[solicitud.asignatura].nombre} · Unidad ${solicitud.unidad}", color = Gris, fontSize = 13.sp)
                Text("Enviada ${solicitud.fecha}", color = Gris, fontSize = 13.sp)
            }
        }
    }
}

// revisar solicitud de un docente
@Composable
fun RevisarSolicitudScreen(navController: NavController, indice: Int) {
    val context = LocalContext.current
    val aviso = { Toast.makeText(context, "Para una futura actualización", Toast.LENGTH_SHORT).show() }
    val solicitud = solicitudes[indice]
    var observacion by remember { mutableStateOf("") }

    Pantalla(titulo = "Revisar solicitud", onVolver = { navController.popBackStack() }) {
        Text(solicitud.titulo, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        EtiquetaEstado(solicitud.estado)

        Tarjeta {
            Text("Docente: ${solicitud.docente}")
            Text("Asignatura: ${asignaturas[solicitud.asignatura].nombre}")
            Text("Unidad: ${solicitud.unidad}")
            Text("Enviada: ${solicitud.fecha}")
        }
        Tarjeta {
            Text("Descripción", fontWeight = FontWeight.Bold)
            Text(solicitud.descripcion)
            TextButton(onClick = aviso) { Text("📄 ${solicitud.archivo} · Ver") }
        }
        CampoTexto(observacion, { observacion = it }, "Observación", null)
        BotonPrincipal("Aceptar", onClick = aviso)
        OutlinedButton(onClick = aviso, modifier = Modifier.fillMaxWidth()) { Text("Rechazar") }
    }
}

// historial
@Composable
fun HistorialScreen(navController: NavController) {
    Pantalla(titulo = "Historial", onVolver = { navController.popBackStack() }) {
        Text("Modificaciones y aprobaciones", color = Gris)

        historial.forEach { cambio ->
            Tarjeta {
                Row {
                    Text(cambio.accion, Modifier.weight(1f), fontWeight = FontWeight.Bold)
                    Text(cambio.fecha, color = Gris, fontSize = 13.sp)
                }
                Text(cambio.que)
                Text(cambio.quien, color = Gris, fontSize = 13.sp)
                if (cambio.observacion != "") Text("“${cambio.observacion}”", color = Gris, fontSize = 13.sp)
            }
        }
    }
}