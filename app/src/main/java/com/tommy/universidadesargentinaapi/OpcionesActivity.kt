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


        val btnVerMapa = findViewById<Button>(R.id.btnVerMapa)
        // evento para abrir la pantalla de registro
        btnVerMapa.setOnClickListener {
            // intent que iniciará la pantalla de registro
            val intent = Intent(this, MapsActivity::class.java)
            // inicio
            startActivity(intent)
        }
    }
}