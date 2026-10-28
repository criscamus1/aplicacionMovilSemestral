package com.example.aprendiendo.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

// DAO: las consultas que se pueden hacer a la tabla "usuarios"
@Dao
interface UsuarioDao {

    // Guarda un usuario. Si el correo ya existe, no lo guarda y devuelve -1.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun registrar(usuario: Usuario): Long

    // Busca un usuario con ese correo y esa clave. Si no existe, devuelve null.
    @Query("SELECT * FROM usuarios WHERE correo = :correo AND clave = :clave LIMIT 1")
    suspend fun login(correo: String, clave: String): Usuario?
}
