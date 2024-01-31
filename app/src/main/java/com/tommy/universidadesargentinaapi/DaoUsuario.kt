package com.tommy.universidadesargentinaapi

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DaoUsuario {
    @Insert
    suspend fun crearUsuario(usuario: Usuario)

    // Búsqueda solo de el nombre para que al registrar, si ya existe ese nombre, no lo creará
    @Query("SELECT * FROM usuarios WHERE nombre = :nombre LIMIT 1")
    suspend fun buscarPorNombre(nombre: String): Usuario?

    //Función para hacer la consulta para el login (comprueba que existe el usuario)
    @Query("SELECT * FROM usuarios WHERE nombre = :nombre AND contrasenia = :contrasenia LIMIT 1")
    suspend fun buscarUsuario(nombre: String, contrasenia: String): Usuario?
}