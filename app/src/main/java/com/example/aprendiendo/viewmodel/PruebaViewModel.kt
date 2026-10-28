package com.example.aprendiendo.viewmodel

import androidx.lifecycle.ViewModel
import com.example.aprendiendo.model.PruebaUiState
import com.example.aprendiendo.model.asignaturas
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class PruebaViewModel : ViewModel() {

    // Estado interno mutable
    private val _estado = MutableStateFlow(PruebaUiState())

    // Estado expuesto para la UI
    val estado: StateFlow<PruebaUiState> = _estado

    // Guarda la alternativa que eligió el estudiante en una pregunta
    fun elegir(pregunta: Int, opcion: String) {
        _estado.update { it.copy(elegidas = it.elegidas + (pregunta to opcion)) }
    }

    // Borra las respuestas para empezar la prueba de nuevo
    fun reiniciar() {
        _estado.update { it.copy(elegidas = emptyMap()) }
    }

    // EVALUACIÓN AUTOMÁTICA: cuenta cuántas respuestas son iguales a la correcta
    fun correctas(indice: Int): Int {
        val preguntas = asignaturas[indice].preguntas
        return preguntas.indices.count { _estado.value.elegidas[it] == preguntas[it].correcta }
    }

    // Guarda el mejor puntaje de esa asignatura
    fun guardarPuntaje(indice: Int) {
        val nota = correctas(indice)
        _estado.update {
            it.copy(puntajes = it.puntajes + (indice to maxOf(nota, it.puntajes[indice] ?: 0)))
        }
    }

    // Al cerrar sesión se borra todo
    fun limpiar() {
        _estado.value = PruebaUiState()
    }
}
