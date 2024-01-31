package com.tommy.universidadesargentinaapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
class SharedPreferencesActivity : AppCompatActivity() {

    var numero = 0
    var animalEscogido: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shared_preferences)

        // Obtener el nombre de usuario actual
        val nombreDeUsuario = intent.getStringExtra("nombreUsuario") ?: "UsuarioPorDefecto"

        // Cojo las shared preferences del usuario que ha iniciado sesión
        val pref = applicationContext.getSharedPreferences("datos_$nombreDeUsuario", MODE_PRIVATE)

        // variables por defecto
        numero = pref.getInt("contador", 0)
        animalEscogido = pref.getString("animal", "") ?: ""

        // Boton +1
        val pressHereButton = findViewById<Button>(R.id.pressHereButton)
        pressHereButton.setOnClickListener {
            incrementarNumero()
        }

        // botón para guardar las preferencias
        val buttonGuardarPrefs = findViewById<Button>(R.id.buttonGuardarPrefs)
        buttonGuardarPrefs.setOnClickListener {
            guardaPrefs(nombreDeUsuario) // Pasar el nombre de usuario a guardaPrefs
        }

        var numTextView = findViewById<TextView>(R.id.numTextView)
        val animalFavTextView = findViewById<TextView>(R.id.animalFavTextView)
        numTextView.text = numero.toString()
        animalFavTextView.text = animalEscogido
    }

    private fun incrementarNumero() {
        numero += 1
        val numTextView = findViewById<TextView>(R.id.numTextView)
        numTextView.text = numero.toString()
    }

    //Método para guardar preferencias dependiendo del usuario
    private fun guardaPrefs(nombreUsuario: String) {
        val pref = applicationContext.getSharedPreferences("datos_$nombreUsuario", MODE_PRIVATE)
        val editor = pref.edit()

        val favoriteAnimalEditText = findViewById<EditText>(R.id.favoriteAnimalEditText)
        animalEscogido = favoriteAnimalEditText.text.toString()

        editor.putInt("contador", numero)
        editor.putString("animal", "Último animal favorito -> $animalEscogido")

        editor.apply()
        Toast.makeText(applicationContext, "Preferencias guardadas para $nombreUsuario", Toast.LENGTH_LONG).show()
    }
}
