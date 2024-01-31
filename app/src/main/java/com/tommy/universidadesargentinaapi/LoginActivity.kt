package com.tommy.universidadesargentinaapi

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Button
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity



class LoginActivity : AppCompatActivity() {

    //numero que será incrementado y guardado como shared preferences
    var numero = 0;
    // Declaración de la variable para almacenar el animal escogido
    var animalEscogido: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(androidx.appcompat.R.style.Theme_AppCompat)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //variables de los componentes visuales
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val showPasswordCheckBox = findViewById<CheckBox>(R.id.showPasswordCheckBox)

        //evento al marcar o desmarcar el checkbox
        showPasswordCheckBox.setOnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
            // Si el CheckBox está marcado, mostrar la contraseña
            if (isChecked) {
                // se ve la contraseña
                passwordEditText.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
            } else {
                // no se ve
                passwordEditText.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }

        // id del textView para abrir el form registro
        val registerTextView = findViewById<TextView>(R.id.registerTextView)

        // evento para abrir la pantalla de registro
        registerTextView.setOnClickListener {
            // intent que iniciará la pantalla de registro
            val intent = Intent(this, RegistroActivity::class.java)
            // inicio
            startActivity(intent)
        }

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
