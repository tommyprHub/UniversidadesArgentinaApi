package com.tommy.universidadesargentinaapi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    // Método para obtener todas las universidades de argentina
    @GET("search")
    fun getAllUniversities(@Query("country") country: String): Call<List<Universidad>>

    //mostrar las de la busqueda
    @GET("search")
    fun getUniByName(@Query("country") country: String, @Query("name") name: String): Call<List<Universidad>>
}