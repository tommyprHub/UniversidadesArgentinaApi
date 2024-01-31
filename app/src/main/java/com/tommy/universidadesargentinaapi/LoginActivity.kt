package com.tommy.universidadesargentinaapi

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import kotlinx.coroutines.launch



class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(androidx.appcompat.R.style.Theme_AppCompat)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //variables de los componentes visuales
        val usernameEditText = findViewById<EditText>(R.id.usernameEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val showPasswordCheckBox = findViewById<CheckBox>(R.id.showPasswordCheckBox)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val registerTextView = findViewById<TextView>(R.id.registerTextView)

        val db = Room.databaseBuilder(applicationContext, DBPrueba::class.java, "dbPruebas").build()



        // ---------------- EVENTO PARA QUE SE MUESTRE LA CONTRASEÑA CHECKBOX ---------------
        showPasswordCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                passwordEditText.transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else {
                passwordEditText.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }



        //------------- ABRIR FORMULARIO DE REGISTRO ------------------

        registerTextView.setOnClickListener {
            startActivity(Intent(this, RegistroActivity::class.java))
        }



        //------------- ABRIR FORMULARIO DE OPCIONES CUANDO EL USUARIO Y LA CONTRASEÑA COINCIDAN CON LAS QUE HAY EN LA BASE DE DATOS ------------------
        loginButton.setOnClickListener {
            val nombre = usernameEditText.text.toString()
            val contrasenia = passwordEditText.text.toString()

            if (nombre.isNotEmpty() && contrasenia.isNotEmpty()) {
                lifecycleScope.launch {
                    val usuario = db.daoUsuario().buscarUsuario(nombre, contrasenia)
                    if (usuario != null) {
                        // Usuario encontrado, iniciar actividad de opciones
                        val intent = Intent(this@LoginActivity, OpcionesActivity::class.java)
                        intent.putExtra("nombreUsuario",nombre)
                        startActivity(intent)
                    } else {
                        // Usuario no encontrado, mostrar mensaje de error
                        runOnUiThread {
                            Toast.makeText(applicationContext, "Usuario o contraseña incorrectos", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            } else {
                Toast.makeText(applicationContext, "Por favor, ingrese usuario y contraseña", Toast.LENGTH_LONG).show()
            }
        }

    }
}
