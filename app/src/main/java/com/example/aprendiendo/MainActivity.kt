package com.example.aprendiendo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// =================================================================
// 1. COLORES DE LA APP (los mismos del mockup)
//    0xFF + el código del color.  Ej: 2F5BD3  ->  Color(0xFF2F5BD3)
// =================================================================
val AzulPrimario = Color(0xFF2F5BD3)    // botones, logo
val AzulClaro = Color(0xFFDDE7FD)       // fondo de lo seleccionado
val TextoPrincipal = Color(0xFF1C2333)  // títulos
val TextoSecundario = Color(0xFF5B6475) // subtítulos y ayudas
val GrisBorde = Color(0xFF79808F)       // borde de los campos

// Tema: le dice a todos los componentes que el color principal es el azul
// (así los campos de texto se marcan en azul cuando escribes en ellos).
@Composable
fun MpazTema(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = AzulPrimario,
            background = Color.White,
            surface = Color.White
        ),
        content = content
    )
}

// =================================================================
// 2. PUNTO DE PARTIDA: Android abre esta Activity al tocar el ícono.
// =================================================================
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MpazTema {          // colores de la app
                AppNavegacion() // el "GPS" de la app
            }
        }
    }
}

// =================================================================
// 3. NAVEGACIÓN: aquí se registran las pantallas con un nombre (ruta).
//    navigate("registro")  -> va a Crear cuenta (pone un "plato" encima)
//    popBackStack()        -> vuelve a la pantalla anterior (saca el plato)
// =================================================================
@Composable
fun AppNavegacion() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {

        composable("login") {
            LoginScreen(
                onIngresar = {
                    // Más adelante: validar el usuario con Room e ir a la pantalla Inicio.
                },
                onIrARegistro = { navController.navigate("registro") }
            )
        }

        composable("registro") {
            RegistroScreen(
                onVolver = { navController.popBackStack() },
                onRegistrarme = {
                    // Más adelante: guardar el usuario con Room.
                    // Por ahora solo vuelve al Login.
                    navController.popBackStack()
                }
            )
        }
    }
}

// =================================================================
// 4. PANTALLA LOGIN
//    Recibe dos funciones (lambdas): la pantalla NO navega sola,
//    solo avisa "tocaron Ingresar" o "tocaron Regístrate".
// =================================================================
@Composable
fun LoginScreen(
    onIngresar: () -> Unit,
    onIrARegistro: () -> Unit
) {
    // ESTADO: la "memoria" de la pantalla. Guarda lo que el usuario escribe.
    var correo by remember { mutableStateOf("") }
    var clave by remember { mutableStateOf("") }

    // Marco principal = Column (en Figma: Flujo vertical ↓)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .systemBarsPadding()  // no se mete bajo la barra de la hora/batería
            .padding(start = 28.dp, end = 28.dp, top = 48.dp, bottom = 24.dp), // = Espaciado
        horizontalAlignment = Alignment.CenterHorizontally,                    // = Alineación al centro
        verticalArrangement = Arrangement.spacedBy(28.dp)                      // = Espacio 28
    ) {

        // ---- Grupo del logo (Espacio 12) ----
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Cuadrado azul 96 x 96 con radio 28 y la "M" al centro
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .background(AzulPrimario, RoundedCornerShape(28.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text("M", color = Color.White, fontSize = 46.sp, fontWeight = FontWeight.ExtraBold)
            }
            Text("MPAZ PRO 1.º", fontSize = 32.sp, fontWeight = FontWeight.ExtraBold, color = TextoPrincipal)
            Text("Apoyo didáctico · 1.º Básico", fontSize = 17.sp, color = TextoSecundario)
        }

        // ---- Campo Correo ----
        OutlinedTextField(
            value = correo,                     // muestra lo que hay en "correo"
            onValueChange = { correo = it },    // al escribir, actualiza "correo"
            label = { Text("Correo") },
            placeholder = { Text("nombre@correo.cl") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth()  // = W Rellenar
        )

        // ---- Campo Contraseña ----
        OutlinedTextField(
            value = clave,
            onValueChange = { clave = it },
            label = { Text("Contraseña") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(), // muestra •••••
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth()
        )

        // ---- Botón Ingresar ----
        Button(
            onClick = onIngresar,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AzulPrimario)
        ) {
            Text("Ingresar", fontSize = 19.sp, fontWeight = FontWeight.Bold)
        }

        // ---- ¿No tienes cuenta? Regístrate ----
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("¿No tienes cuenta?", fontSize = 16.sp, color = TextoSecundario)
            TextButton(onClick = onIrARegistro) {   // <- este lleva a Crear cuenta
                Text("Regístrate", fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, color = AzulPrimario)
            }
        }

        // ---- Espaciador: empuja el pie hacia abajo (en Figma: H Rellenar) ----
        Spacer(modifier = Modifier.weight(1f))

        // ---- Pie de página ----
        Text("Escuela Escritora Marcela Paz · datos ficticios", fontSize = 13.sp, color = TextoSecundario)
    }
}

// =================================================================
// 5. PANTALLA CREAR CUENTA (Registro)
//    Por ahora solo es la interfaz: no guarda nada (eso será con Room).
// =================================================================
@Composable
fun RegistroScreen(
    onVolver: () -> Unit,
    onRegistrarme: () -> Unit
) {
    // ESTADO de cada campo
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var clave by remember { mutableStateOf("") }
    var repetirClave by remember { mutableStateOf("") }
    var perfil by remember { mutableStateOf("Estudiante") } // perfil elegido

    // Validación simple: si ya escribió algo en "Repetir" y no es igual -> error
    val clavesNoCoinciden = repetirClave.isNotEmpty() && clave != repetirClave

    // Los 3 perfiles del caso
    val perfiles = listOf("Estudiante", "Docente", "UTP")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .systemBarsPadding()
            .verticalScroll(rememberScrollState()) // permite bajar si no cabe (ej: con el teclado abierto)
            .padding(start = 28.dp, end = 28.dp, top = 16.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        // ---- Barra de título: flecha + "Crear cuenta" (en Figma: Flujo horizontal →) ----
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onVolver) {   // <- vuelve al Login
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver",
                    tint = TextoPrincipal
                )
            }
            Text("Crear cuenta", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = TextoPrincipal)
        }

        // ---- Nombre ----
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            placeholder = { Text("Sofía Pérez") },
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth()
        )

        // ---- Correo ----
        OutlinedTextField(
            value = correo,
            onValueChange = { correo = it },
            label = { Text("Correo") },
            placeholder = { Text("nombre@correo.cl") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth()
        )

        // ---- Contraseña (con texto de ayuda debajo) ----
        OutlinedTextField(
            value = clave,
            onValueChange = { clave = it },
            label = { Text("Contraseña") },
            supportingText = { Text("Mínimo 6 caracteres") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth()
        )

        // ---- Repetir contraseña (se pone ROJO si no coincide) ----
        OutlinedTextField(
            value = repetirClave,
            onValueChange = { repetirClave = it },
            label = { Text("Repetir contraseña") },
            isError = clavesNoCoinciden,   // true -> borde y etiqueta en rojo
            supportingText = if (clavesNoCoinciden) {
                { Text("Las contraseñas no coinciden") }
            } else null,
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth()
        )

        // ---- Perfil: 3 botones, solo uno elegido ----
        Text("Perfil", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextoPrincipal)

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            perfiles.forEach { opcion ->
                val elegido = (perfil == opcion)
                OutlinedButton(
                    onClick = { perfil = opcion },   // al tocarlo, pasa a ser el elegido
                    modifier = Modifier
                        .weight(1f)                  // los 3 del mismo ancho
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(
                        width = if (elegido) 2.dp else 1.5.dp,
                        color = if (elegido) AzulPrimario else GrisBorde
                    ),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (elegido) AzulClaro else Color.White,
                        contentColor = TextoPrincipal
                    ),
                    contentPadding = PaddingValues(horizontal = 4.dp)
                ) {
                    Text(
                        opcion,
                        fontSize = 15.sp,
                        fontWeight = if (elegido) FontWeight.ExtraBold else FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // ---- Botón Registrarme ----
        Button(
            onClick = onRegistrarme,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AzulPrimario)
        ) {
            Text("Registrarme", fontSize = 19.sp, fontWeight = FontWeight.Bold)
        }
    }
}

// =================================================================
// 6. VISTAS PREVIAS: para ver las pantallas en Android Studio SIN
//    ejecutar la app (botón "Split" arriba a la derecha del editor).
// =================================================================
@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun LoginScreenPreview() {
    MpazTema {
        LoginScreen(onIngresar = {}, onIrARegistro = {})
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun RegistroScreenPreview() {
    MpazTema {
        RegistroScreen(onVolver = {}, onRegistrarme = {})
    }
}
