package com.tommy.universidadesargentinaapi

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class DatosUniversidad : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_datos_universidad)

        val universidad = intent.getSerializableExtra("universidad") as? Universidad
        universidad?.let {
            findViewById<TextView>(R.id.txtNombreUni).text = "Nombre: ${it.name}"
            findViewById<TextView>(R.id.txtPais).text = "País: ${it.country}"
            findViewById<TextView>(R.id.txtProvincia).text = "Estado: ${it.stateProvince ?: "N/A"}"
            findViewById<TextView>(R.id.txtAlphaCode).text = "Alpha Code: ${it.alphaCode}"
            val webPagesText = if (it.webPages.isNullOrEmpty()) "N/A" else it.webPages.joinToString(", ")
            findViewById<TextView>(R.id.txtWebPages).text = "Páginas Web: $webPagesText"
            val dominiosText = if (it.domains.isNullOrEmpty()) "N/A" else it.domains.joinToString(", ")
            findViewById<TextView>(R.id.txtDomains).text = "Dominios: $dominiosText"
        }

        val botonRegresar: Button = findViewById(R.id.botonVolver)

        botonRegresar.setOnClickListener {
            val intent = Intent(this@DatosUniversidad , MainActivity::class.java)

            startActivity(intent)

            finish()
        }
    }
}