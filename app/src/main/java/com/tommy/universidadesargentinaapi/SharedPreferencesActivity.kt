package com.tommy.universidadesargentinaapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class SharedPreferencesActivity : AppCompatActivity() {

    //numero que será incrementado y guardado como shared preferences
    var numero = 0;
    // Declaración de la variable para almacenar el animal escogido
    var animalEscogido: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shared_preferences)

        //------------- SHARED PREFERENCES ------------------

        //botón para incrementar el número
        val pressHereButton = findViewById<Button>(R.id.pressHereButton)

        // Evento del botón "Pulsa aquí"
        pressHereButton.setOnClickListener {
            incrementarNumero()
        }

        //CARGAR LOS DATOS
        val pref = applicationContext.getSharedPreferences(
            "datos",0)

        numero = pref.getInt("contador",numero)
        animalEscogido = pref.getString("animal", animalEscogido).toString()

        //variables de los componentes donde deben de aparecer los valores
        var numTextView = findViewById<TextView>(R.id.numTextView)
        val animalFavTextView = findViewById<TextView>(R.id.animalFavTextView)
        numTextView.text = numero.toString()
        animalFavTextView.text = animalEscogido.toString()


        //botón para guardar preferencias
        val buttonGuardarPrefs = findViewById<Button>(R.id.buttonGuardarPrefs)
        buttonGuardarPrefs.setOnClickListener {
            guardaPrefs()
        }
    }

    // Método para incrementar la variable 'numero'
    private fun incrementarNumero() {
        numero += 1
        var numTextView = findViewById<TextView>(R.id.numTextView)
        numTextView.text = numero.toString()
    }
    //Método para guardar preferencias
    private fun guardaPrefs(){
        //shared preferences
        val pref = applicationContext.getSharedPreferences(
            "datos",0)

        val editor = pref.edit()
        val favoriteAnimalEditText = findViewById<EditText>(R.id.favoriteAnimalEditText) //debo coger el texto del animal favorito
        animalEscogido = favoriteAnimalEditText.text.toString()

        editor.putInt("contador", numero)
        editor.putString("animal", animalEscogido)

        editor.apply()
    }
}