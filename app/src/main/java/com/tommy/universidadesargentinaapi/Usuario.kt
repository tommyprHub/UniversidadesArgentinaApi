package com.tommy.universidadesargentinaapi

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("usuarios")
data class Usuario(
    @PrimaryKey var nombre: String,
    @ColumnInfo(name = "pais") val correo: String,
    @ColumnInfo(name = "contrasenia")val passwd: String
)