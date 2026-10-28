package com.example.aprendiendo.model

// Modelo principal que representa el estado del formulario de login y registro
data class UsuarioUiState(
    val nombre: String = "",                        // Nombre del usuario (solo en registro)
    val correo: String = "",                        // Correo electrónico
    val clave: String = "",                         // Contraseña
    val perfil: String = "Estudiante",              // Perfil elegido al registrarse
    val errores: UsuarioErrores = UsuarioErrores(), // Errores de cada campo
    val mensaje: String? = null,                    // Mensaje general (ej: datos incorrectos)
    val cargando: Boolean = false,                  // true mientras "carga" el login
    val nombreSesion: String = "",                  // Nombre de quien inició sesión
    val perfilSesion: String = ""                   // Perfil de quien inició sesión
)

// Modelo que almacena los posibles errores individuales del formulario
data class UsuarioErrores(
    val nombre: String? = null,
    val correo: String? = null,
    val clave: String? = null
)
