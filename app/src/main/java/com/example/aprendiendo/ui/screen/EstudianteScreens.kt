package com.example.aprendiendo.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.aprendiendo.model.asignaturas
import com.example.aprendiendo.viewmodel.PruebaViewModel
import com.example.aprendiendo.viewmodel.UsuarioViewModel

// asignaturas y puntaje
@Composable
fun InicioScreen(
    navController: NavController,
    usuarioViewModel: UsuarioViewModel,
    pruebaViewModel: PruebaViewModel
) {
    val usuario by usuarioViewModel.estado.collectAsState()
    val prueba by pruebaViewModel.estado.collectAsState()
    Pantalla(titulo = "¡Hola, ${usuario.nombreSesion.substringBefore(" ")}!") {
        Text("Elige una asignatura", color = Gris, fontSize = 18.sp)
        asignaturas.forEachIndexed { i, asignatura ->
            Tarjeta(asignatura.color, onClick = { navController.navigate("unidades/$i") }) {                Text("${asignatura.emoji}  ${asignatura.nombre}", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                val nota = prueba.puntajes[i]
                Text(if (nota == null) "Sin prueba" else "Mi avance: $nota/3", color = Gris)
            }
        }
    }
}

// los objetivos mas ejemplo
@Composable
fun ContenidoScreen(navController: NavController, indice: Int) {
    val asignatura = asignaturas[indice]

    Pantalla(titulo = "${asignatura.emoji} ${asignatura.nombre}", onVolver = { navController.popBackStack() }) {
        Text("Objetivos asignados por tu profesor(a)", color = Gris)
        asignatura.objetivos.forEach { Text(it, fontSize = 17.sp, fontWeight = FontWeight.Bold) }
        Text(asignatura.oa, color = Gris, fontWeight = FontWeight.Bold)
        Text(asignatura.explicacion, fontSize = 20.sp, lineHeight = 28.sp)
        Text("Ejemplo guiado", fontWeight = FontWeight.Bold)
        Tarjeta(AzulClaro) { Text(asignatura.ejemplo, fontSize = 30.sp, fontWeight = FontWeight.ExtraBold) }

        BotonPrincipal("Ir a la prueba") { navController.navigate("prueba/$indice") }
    }
}

// 3 preguntas
@Composable
fun PruebaScreen(navController: NavController, viewModel: PruebaViewModel, indice: Int) {
    val estado by viewModel.estado.collectAsState()
    val asignatura = asignaturas[indice]
    //  la prueba empieza sin respuestas
    LaunchedEffect(indice) { viewModel.reiniciar() }
    Pantalla(titulo = asignatura.nombre, onVolver = { navController.popBackStack() }) {
        Text("Prueba final · ${asignatura.oa}", color = Gris)
        asignatura.preguntas.forEachIndexed { n, pregunta ->
            Tarjeta {
                Text("${n + 1}. ${pregunta.texto}", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                if (pregunta.opciones.any { it.length > 8 }) {
                    pregunta.opciones.forEach { op ->
                        Opcion(op, estado.elegidas[n] == op, Modifier.fillMaxWidth()) { viewModel.elegir(n, op) }
                    }
                } else {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        pregunta.opciones.forEach { op ->
                            Opcion(op, estado.elegidas[n] == op, Modifier.weight(1f)) { viewModel.elegir(n, op) }
                        }
                    }
                }
            }
        }
        // Se activara solo cuando se responda todas
        BotonPrincipal("Responder", habilitado = estado.elegidas.size == asignatura.preguntas.size) {
            viewModel.guardarPuntaje(indice)
            navController.navigate("resultado/$indice") { popUpTo("prueba/{indice}") { inclusive = true } }
        }
    }
}
//corrección
@Composable
fun ResultadoScreen(navController: NavController, viewModel: PruebaViewModel, indice: Int) {
    val estado by viewModel.estado.collectAsState()
    val asignatura = asignaturas[indice]
    val buenas = viewModel.correctas(indice)
    val total = asignatura.preguntas.size
    Pantalla(titulo = "Resultado · ${asignatura.nombre}") {
        Text(
            "$buenas/$total",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 64.sp, fontWeight = FontWeight.ExtraBold,
            color = if (buenas == total) Verde else Color(0xFFB04A0C)
        )
        Text(
            if (buenas == total) "¡Excelente!" else "¡Muy bien!",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 28.sp, fontWeight = FontWeight.Bold
        )
        asignatura.preguntas.forEachIndexed { n, pregunta ->
            val elegida = estado.elegidas[n] ?: ""
            val bien = elegida == pregunta.correcta
            Tarjeta(if (bien) VerdeClaro else RojoClaro) {
                Text("${if (bien) "✅" else "❌"} ${n + 1}. ${pregunta.texto}", fontWeight = FontWeight.Bold)
                Text(
                    if (bien) "Tu respuesta: $elegida" else "Tú: $elegida · Era: ${pregunta.correcta}",
                    color = if (bien) Gris else Rojo
                )
            }
        }
        if (buenas < total) {
            OutlinedButton(
                onClick = { navController.navigate("prueba/$indice") { popUpTo("resultado/{indice}") { inclusive = true } } },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Volver a intentar") }
        }
        BotonPrincipal("Ir al inicio") { navController.popBackStack("inicio", inclusive = false) }
    }
}
