package com.example.aprendiendo.data

import androidx.room.Entity
import androidx.room.PrimaryKey

// ENTITY: la tabla "usuarios" de la base de datos. Cada propiedad es una columna.
@Entity(tableName = "usuarios")
data class Usuario(
    @PrimaryKey val correo: String,   // el correo no se puede repetir
    val nombre: String,
    val clave: String,
    val perfil: String                // "Estudiante", "Docente" o "UTP"
)
