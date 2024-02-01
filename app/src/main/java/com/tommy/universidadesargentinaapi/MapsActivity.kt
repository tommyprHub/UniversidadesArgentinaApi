package com.tommy.universidadesargentinaapi

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.tommy.universidadesargentinaapi.APIService
import com.tommy.universidadesargentinaapi.GoogleGeocodingService
import com.tommy.universidadesargentinaapi.R
import com.tommy.universidadesargentinaapi.databinding.ActivityMapsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        loadAllUniversities()
    }

    private fun loadAllUniversities() {
        CoroutineScope(Dispatchers.IO).launch {
            val retrofitUniversities = Retrofit.Builder()
                .baseUrl("http://universities.hipolabs.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val universitiesService = retrofitUniversities.create(APIService::class.java)
            val universitiesResponse = universitiesService.getAllUniversities("Argentina").execute()

            if (universitiesResponse.isSuccessful) {
                universitiesResponse.body()?.let { universities ->
                    universities.forEach { university ->
                        val detailedAddress = "${university.name}, Argentina"
                        val retrofitGeocoding = Retrofit.Builder()
                            .baseUrl("https://maps.googleapis.com/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()

                        val geocodingService = retrofitGeocoding.create(GoogleGeocodingService::class.java)
                        val geocodingResponse = geocodingService.getCoordinates(detailedAddress, "AIzaSyDEhe3TKPMbvp6NFldu0FdyLEJ5tlg5tM8").execute()

                        if (geocodingResponse.isSuccessful) {
                            geocodingResponse.body()?.results?.firstOrNull()?.let { result ->
                                val location = result.geometry.location
                                Log.d("MapsActivity", "Ubicación encontrada: ${location.lat}, ${location.lng}")
                                withContext(Dispatchers.Main) {
                                    val latLng = LatLng(location.lat, location.lng)
                                    mMap.addMarker(MarkerOptions().position(latLng).title(university.name))
                                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 1f))
                                }
                            } ?: Log.d("MapsActivity", "No se encontraron resultados para ${university.name}")
                        }  else {
                            val errorBody = geocodingResponse.errorBody()?.string()
                            Log.d("MapsActivity", "Error en Geocoding API para ${university.name}: $errorBody")
                        }
                    }
                }
            }
        }
    }
}
