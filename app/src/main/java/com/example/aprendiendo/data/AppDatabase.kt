package com.example.aprendiendo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// DATABASE: la base de datos completa (archivo "mpaz.db" guardado en el teléfono)
@Database(entities = [Usuario::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao

    companion object {
        private var instancia: AppDatabase? = null

        // Crea la base de datos una sola vez y después la reutiliza
        fun obtener(context: Context): AppDatabase {
            if (instancia == null) {
                instancia = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "mpaz.db"
                ).build()
            }
            return instancia!!
        }
    }
}
