package com.tommy.universidadesargentinaapi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleGeocodingService {
    @GET("maps/api/geocode/json")
    fun getCoordinates(
        @Query("address") address: String,
        @Query("key") apiKey: String
    ): Call<GeocodingResponse>
}
