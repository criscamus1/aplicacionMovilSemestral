package com.example.aprendiendo.model

// Estado de la prueba que está respondiendo el estudiante
data class PruebaUiState(
    val elegidas: Map<Int, String> = emptyMap(),  // número de pregunta -> alternativa elegida
    val puntajes: Map<Int, Int> = emptyMap()      // número de asignatura -> mejor puntaje
)
