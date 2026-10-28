package com.example.aprendiendo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.aprendiendo.data.AppDatabase
import com.example.aprendiendo.data.Usuario
import com.example.aprendiendo.model.UsuarioErrores
import com.example.aprendiendo.model.UsuarioUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// AndroidViewModel porque Room necesita el contexto de la aplicación
class UsuarioViewModel(application: Application) : AndroidViewModel(application) {

    // Acceso a la tabla usuarios (Room)
    private val dao = AppDatabase.obtener(application).usuarioDao()

    // Estado interno mutable
    private val _estado = MutableStateFlow(UsuarioUiState())

    // Estado expuesto para la UI
    val estado: StateFlow<UsuarioUiState> = _estado

    init {
        // Al iniciar, lanzamos la creación de las cuentas de prueba (si ya existen, se ignoran)
        viewModelScope.launch {
            dao.registrar(Usuario("estudiante@prueba.com", "Sofía Pérez", "123456", "Estudiante"))
            dao.registrar(Usuario("docente@prueba.com", "Andrea Rojas", "123456", "Docente"))
            dao.registrar(Usuario("utp@prueba.com", "Jefatura UTP", "123456", "UTP"))
        }
    }

    // Actualiza el campo nombre y limpia su error
    fun onNombreChange(valor: String) {
        _estado.update { it.copy(nombre = valor, errores = it.errores.copy(nombre = null)) }
    }

    // Actualiza el campo correo
    fun onCorreoChange(valor: String) {
        _estado.update { it.copy(correo = valor, errores = it.errores.copy(correo = null), mensaje = null) }
    }

    // Actualiza el campo clave
    fun onClaveChange(valor: String) {
        _estado.update { it.copy(clave = valor, errores = it.errores.copy(clave = null), mensaje = null) }
    }

    // Actualiza el perfil elegido
    fun onPerfilChange(valor: String) {
        _estado.update { it.copy(perfil = valor) }
    }

    // Validación global del formulario (conNombre = true en registro)
    fun validarFormulario(conNombre: Boolean): Boolean {
        val estadoActual = _estado.value
        val errores = UsuarioErrores(
            nombre = if (conNombre && estadoActual.nombre.isBlank()) "Campo obligatorio" else null,
            correo = if (!estadoActual.correo.contains("@")) "Correo inválido" else null,
            clave = if (estadoActual.clave.length < 6) "Debe tener al menos 6 caracteres" else null
        )
        val hayErrores = listOfNotNull(errores.nombre, errores.correo, errores.clave).isNotEmpty()
        _estado.update { it.copy(errores = errores) }
        return !hayErrores
    }

    // LOGIN: simula una carga (como cargarDatos) y busca el usuario en Room
    fun login(onExito: (perfil: String) -> Unit) {
        if (!validarFormulario(conNombre = false)) return
        viewModelScope.launch {
            _estado.update { it.copy(cargando = true, mensaje = null) }
            cargarDatos()   // función suspendida con delay
            val usuario = dao.login(_estado.value.correo.trim(), _estado.value.clave)
            if (usuario == null) {
                _estado.update { it.copy(cargando = false, mensaje = "Correo o contraseña incorrectos") }
            } else {
                _estado.update {
                    it.copy(cargando = false, clave = "", nombreSesion = usuario.nombre, perfilSesion = usuario.perfil)
                }
                onExito(usuario.perfil)
            }
        }
    }

    // Función con tiempo de carga: simula una solicitud de datos a una API
    private suspend fun cargarDatos() {
        delay(1500L)
    }

    // REGISTRO: guarda el usuario en Room
    fun registrar(onExito: () -> Unit) {
        if (!validarFormulario(conNombre = true)) return
        val e = _estado.value
        viewModelScope.launch {
            val resultado = dao.registrar(Usuario(e.correo.trim(), e.nombre.trim(), e.clave, e.perfil))
            if (resultado == -1L) {
                _estado.update { it.copy(errores = it.errores.copy(correo = "Ese correo ya está registrado")) }
            } else {
                _estado.value = UsuarioUiState(correo = e.correo.trim())   // deja el correo listo para el login
                onExito()
            }
        }
    }

    // Limpia el formulario (al ir a registro o al cerrar sesión)
    fun limpiar() {
        _estado.value = UsuarioUiState()
    }
}
