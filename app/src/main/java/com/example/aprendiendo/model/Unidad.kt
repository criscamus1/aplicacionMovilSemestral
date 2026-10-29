package com.example.aprendiendo.model

data class Unidad(
    val numero: Int,
    val oa: String,
    val estado: String    // completada y si no lo esta
)
// Las 3 unidades de cada asignatura
val unidades = listOf(
    // 0 · Matemáticas
    listOf(
        Unidad(1, "OA 1 · Contar del 0 al 100", "Completada"),
        Unidad(2, "OA 7 · Sumar y restar hasta 20", "En curso"),
        Unidad(3, "OA 2 · Números ordinales", "No asignada")
    ),
    // 1 · Lenguaje
    listOf(
        Unidad(1, "Escritura de palabras", "En curso"),
        Unidad(2, "Palabras con H", "No asignada"),
        Unidad(3, "Uso de mayúsculas", "No asignada")
    ),
    // 2 · Ciencias
    listOf(
        Unidad(1, "OA 2 · Animales", "Completada"),
        Unidad(2, "OA 3 · Partes de las plantas", "En curso"),
        Unidad(3, "OA 7 · Hábitos saludables", "No asignada")
    ),
    // 3 · Historia y Geografía
    listOf(
        Unidad(1, "OA 1 · Días de la semana", "En curso"),
        Unidad(2, "OA 1 · Meses del año", "No asignada"),
        Unidad(3, "OA 5 · Símbolos de Chile", "No asignada")
    )
)