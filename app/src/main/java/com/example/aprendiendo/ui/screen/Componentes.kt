package com.example.aprendiendo.ui.screen

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ---------------- COLORES Y TEMA ----------------
val Azul = Color(0xFF2F5BD3)
val AzulClaro = Color(0xFFDDE7FD)
val Gris = Color(0xFF5B6475)
val FondoGris = Color(0xFFF4F6FA)
val Verde = Color(0xFF1E7A43)
val VerdeClaro = Color(0xFFDCF2E3)
val Rojo = Color(0xFFB42318)
val RojoClaro = Color(0xFFFDECEA)

@Composable
fun MpazTema(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = lightColorScheme(primary = Azul), content = content)
}

// ---------------- PIEZAS QUE SE REPITEN EN TODAS LAS PANTALLAS ----------------

// Esqueleto de pantalla: título (con flecha para volver) + contenido que se puede bajar
@Composable
fun Pantalla(
    titulo: String,
    onVolver: (() -> Unit)? = null,
    contenido: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (onVolver != null) {
                IconButton(onClick = onVolver) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver") }
            }
            Text(titulo, modifier = Modifier.weight(1f), fontSize = 26.sp, fontWeight = FontWeight.ExtraBold)
        }
        contenido()
    }
}

// Campo de texto con su error debajo (isError + supportingText, como en la guía 11)
@Composable
fun CampoTexto(
    valor: String,
    onCambio: (String) -> Unit,
    etiqueta: String,
    error: String?,
    esClave: Boolean = false
) {
    OutlinedTextField(
        value = valor,
        onValueChange = onCambio,
        label = { Text(etiqueta) },
        singleLine = true,
        visualTransformation = if (esClave) PasswordVisualTransformation() else VisualTransformation.None,
        isError = error != null,
        supportingText = {
            error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
        },
        modifier = Modifier.fillMaxWidth()
    )
}

// Botón azul grande
@Composable
fun BotonPrincipal(texto: String, habilitado: Boolean = true, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        enabled = habilitado,
        modifier = Modifier.fillMaxWidth().height(54.dp),
        shape = RoundedCornerShape(27.dp)
    ) {
        Text(texto, fontSize = 18.sp, fontWeight = FontWeight.Bold)
    }
}

// Caja redondeada de color. Si recibe onClick, se puede tocar.
@Composable
fun Tarjeta(
    color: Color = FondoGris,
    onClick: (() -> Unit)? = null,
    contenido: @Composable ColumnScope.() -> Unit
) {
    var modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(18.dp)).background(color)
    if (onClick != null) modifier = modifier.clickable { onClick() }
    Column(modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp), content = contenido)
}

// Botón de alternativa: cambia de color con animación al elegirlo (guía 12)
@Composable
fun Opcion(texto: String, elegida: Boolean, modifier: Modifier, onClick: () -> Unit) {
    val colorFondo by animateColorAsState(
        targetValue = if (elegida) AzulClaro else Color.White,
        label = "colorOpcion"
    )
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(54.dp),
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(if (elegida) 3.dp else 1.dp, if (elegida) Azul else Color.LightGray),
        colors = ButtonDefaults.outlinedButtonColors(containerColor = colorFondo, contentColor = Color.Black),
        contentPadding = PaddingValues(4.dp)
    ) {
        Text(texto, fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}
