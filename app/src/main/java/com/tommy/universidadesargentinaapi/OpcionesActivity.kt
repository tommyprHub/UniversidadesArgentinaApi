package com.tommy.universidadesargentinaapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class OpcionesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opciones)

        val nombreDeUsuario = intent.getStringExtra("nombreUsuario") ?: "UsuarioPorDefecto"

        //MÉTODO PARA ABRIR LA LISTA DE UNIVERSIDADES
        val btnListaUniversidades = findViewById<Button>(R.id.btnListaUniversidades)

        btnListaUniversidades.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)

            startActivity(intent)
        }



        // MÉTODO PARA ABRIR EL MAPA
        val btnVerMapa = findViewById<Button>(R.id.btnVerMapa)

        btnVerMapa.setOnClickListener {

            val intent = Intent(this, MapsActivity::class.java)

            startActivity(intent)
        }



        // MÉTODO PARA ABRIR LAS SHARED PREFERENCES
        val btnSharedPreferences = findViewById<Button>(R.id.btnSharedPreferences)
        // evento para abrir la pantalla de registro
        btnSharedPreferences.setOnClickListener {

            val intent = Intent(this, SharedPreferencesActivity::class.java)
            intent.putExtra("nombreUsuario", nombreDeUsuario)
            startActivity(intent)
        }


    }
}