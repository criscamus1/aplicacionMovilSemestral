package com.example.aprendiendo.model

data class Actividad(
    val titulo: String,
    val asignatura: Int,
    val unidad: Int,
    val pregunta: Pregunta,
    val estado: String
)

// solo son ejemplos
val actividades = listOf(
    Actividad("Sumas con lápices", 0, 2,
        Pregunta("Si tienes 7 lápices y pierdes 2, ¿cuántos te quedan?", listOf("5", "9", "4"), "5"), "En revisión"),
    Actividad("Palabras con H", 1, 2,
        Pregunta("¿\"Helado\" se escribe con H?", listOf("Sí", "No"), "Sí"), "En revisión"),
    Actividad("Partes de la planta", 2, 2,
        Pregunta("¿Qué parte de la planta está bajo la tierra?", listOf("Raíz", "Flor", "Hoja"), "Raíz"), "Aprobada"),
    Actividad("Días de la semana", 3, 1,
        Pregunta("¿Qué día viene después del lunes?", listOf("Martes", "Domingo", "Jueves"), "Martes"), "Rechazada")
)