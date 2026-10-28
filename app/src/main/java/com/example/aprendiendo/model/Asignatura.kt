package com.example.aprendiendo.model

import androidx.compose.ui.graphics.Color

// Una pregunta de la prueba
data class Pregunta(
    val texto: String,
    val opciones: List<String>,
    val correcta: String
)

// Una asignatura con su contenido y su prueba
data class Asignatura(
    val nombre: String,
    val emoji: String,
    val color: Color,
    val oa: String,
    val objetivos: List<String>,
    val explicacion: String,
    val ejemplo: String,
    val preguntas: List<Pregunta>,
    val estadoUtp: String          // cómo la ve Jefatura UTP
)

// ---------------- DATOS DE EJEMPLO (ficticios) ----------------

val asignaturas = listOf(
    Asignatura(
        "Matemáticas", "🔢", Color(0xFFDDE7FD), "OA 7 · Sumar y restar hasta 20",
        listOf("✅ OA 1 · Contar del 0 al 100", "▶️ OA 7 · Sumar y restar hasta 20", "⏳ OA 2 · Números ordinales"),
        "Sumar es juntar. Tienes 3 manzanas y te regalan 2. ¡Ahora tienes 5!", "3 + 2 = 5",
        listOf(
            Pregunta("¿Cuánto es 3 + 6?", listOf("9", "8", "10"), "9"),
            Pregunta("Si tienes 7 lápices y pierdes 2, ¿cuántos te quedan?", listOf("5", "9", "4"), "5"),
            Pregunta("¿Cuánto es 12 − 3?", listOf("9", "8", "15"), "9")
        ),
        "Publicado"
    ),
    Asignatura(
        "Lenguaje", "📖", Color(0xFFFFE9D6), "Escritura de palabras",
        listOf("▶️ Escritura de palabras"),
        "La H no suena, pero se escribe: helado, hola. Los nombres de personas empiezan con mayúscula: Sofía.", "helado · Sofía",
        listOf(
            Pregunta("¿\"Helado\" se escribe con H?", listOf("Sí", "No"), "Sí"),
            Pregunta("¿El nombre de una persona empieza con mayúscula?", listOf("Sí", "No"), "Sí"),
            Pregunta("¿La \"ola\" del mar lleva H?", listOf("Sí", "No"), "No")
        ),
        "En revisión"
    ),
    Asignatura(
        "Ciencias", "🌱", Color(0xFFDCF2E3), "OA 2, 3 y 7 · Animales, plantas y hábitos",
        listOf("✅ OA 2 · Animales", "▶️ OA 3 · Partes de las plantas", "⏳ OA 7 · Hábitos saludables"),
        "Las plantas tienen raíz, tallo, hojas y flores. La raíz está bajo la tierra.", "raíz · tallo · hoja",
        listOf(
            Pregunta("¿Qué parte de la planta está bajo la tierra?", listOf("Raíz", "Flor", "Hoja"), "Raíz"),
            Pregunta("¿Qué animal tiene plumas?", listOf("Gallina", "Perro", "Pez"), "Gallina"),
            Pregunta("¿Cuál es un hábito saludable?", listOf("Lavarse las manos", "Comer muchos dulces", "Dormir muy poco"), "Lavarse las manos")
        ),
        "En revisión"
    ),
    Asignatura(
        "Historia y Geografía", "🌎", Color(0xFFF1E2F8), "OA 1 y 5 · Calendario y símbolos de Chile",
        listOf("▶️ OA 1 · Días y meses", "⏳ OA 5 · Símbolos de Chile"),
        "La semana tiene 7 días y el año tiene 12 meses. La bandera de Chile es blanca, azul y roja.", "7 días · 12 meses",
        listOf(
            Pregunta("¿Qué día viene después del lunes?", listOf("Martes", "Domingo", "Jueves"), "Martes"),
            Pregunta("¿Cuántos meses tiene un año?", listOf("12", "7", "10"), "12"),
            Pregunta("¿Cuál es la bandera de Chile?", listOf("🇦🇷", "🇨🇱", "🇵🇪"), "🇨🇱")
        ),
        "Autorizado"
    )
)

// Puntajes de ejemplo que ve el docente (prueba de Matemáticas)
val puntajesCurso = listOf(
    "Sofía P." to "3/3",
    "Tomás R." to "2/3",
    "Valentina M." to "3/3",
    "Benjamín S." to "Pendiente"
)
