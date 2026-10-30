package com.example.aprendiendo.model

// utp mira
data class Actividad(
    val titulo: String,
    val asignatura: Int,
    val unidad: Int,
    val pregunta: Pregunta,
    val estado: String
)

val actividades = listOf(
    Actividad("Sumas con lápices", 0, 2,
        Pregunta("Si tienes 7 lápices y pierdes 2, ¿cuántos te quedan?", listOf("5", "9", "4"), "5"), "En revisión"),
    Actividad("Palabras con H", 1, 2,
        Pregunta("¿\"Helado\" se escribe con H?", listOf("Sí", "No"), "Sí"), "En revisión"),
    Actividad("Partes de la planta", 2, 2,
        Pregunta("¿Qué parte de la planta está bajo la tierra?", listOf("Raíz", "Flor", "Hoja"), "Raíz"), "Aprobada"),
    Actividad("Contar hasta 100", 0, 1,
        Pregunta("¿Qué número viene después del 99?", listOf("100", "90", "98"), "100"), "Publicada"),
    Actividad("Días de la semana", 3, 1,
        Pregunta("¿Qué día viene después del lunes?", listOf("Martes", "Domingo", "Jueves"), "Martes"), "Rechazada")
)

// el docente ve ele informe
data class Estudiante(
    val nombre: String,
    val respuestas: List<String>?   // respuestas en Matemáticas · null = aún no rinde
)

val estudiantes = listOf(
    Estudiante("Sofía P.", listOf("9", "5", "9")),
    Estudiante("Tomás R.", listOf("9", "5", "8")),
    Estudiante("Valentina M.", listOf("9", "5", "9")),
    Estudiante("Benjamín S.", null)
)

fun puntajeMatematicas(respuestas: List<String>): Int {
    val preguntas = asignaturas[0].preguntas
    return preguntas.indices.count { respuestas.getOrNull(it) == preguntas[it].correcta }
}

// la guia que mandara el docente a utp
data class Solicitud(
    val titulo: String,
    val docente: String,
    val asignatura: Int,
    val unidad: Int,
    val fecha: String,
    val descripcion: String,
    val archivo: String,
    val estado: String
)

val solicitudes = listOf(
    Solicitud("Guía de sumas y restas", "Prof. Andrea R.", 0, 2, "27-10-2026",
        "Ejercicios de sumas y restas hasta 20 para reforzar en clases.", "guia_sumas.pdf", "Pendiente"),
    Solicitud("Guía de palabras con H", "Prof. Andrea R.", 1, 2, "26-10-2026",
        "Palabras que se escriben con H para practicar escritura.", "guia_h.pdf", "Pendiente"),
    Solicitud("Guía de animales", "Prof. Andrea R.", 2, 1, "22-10-2026",
        "Dibujos de animales para reconocer sus características.", "guia_animales.pdf", "Aceptada"),
    Solicitud("Guía de símbolos patrios", "Prof. Andrea R.", 3, 3, "20-10-2026",
        "Bandera, escudo e himno de Chile.", "guiaApoyo.pdf", "Rechazada")
)
// solamente es el historial
data class Cambio(
    val fecha: String,
    val accion: String,
    val que: String,
    val quien: String,
    val observacion: String = ""
)
val historial = listOf(
    Cambio("28-10-2026", "Publicada", "Actividad · Contar hasta 100", "Jefatura UTP"),
    Cambio("27-10-2026", "Guía aceptada", "Guía de animales", "Jefatura UTP"),
    Cambio("26-10-2026", "Rechazada", "Actividad · Días de la semana", "Jefatura UTP", "Agregar una imagen de apoyo"),
    Cambio("25-10-2026", "Guía enviada", "Guía de sumas y restas", "Prof. Andrea R."),
    Cambio("24-10-2026", "Deshabilitada", "Actividad · Números ordinales", "Jefatura UTP", "Revisar el ejemplo guiado")
)