package com.tommy.universidadesargentinaapi

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class LoginActivity : AppCompatActivity() {
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

        // id del textView
        val registerTextView = findViewById<TextView>(R.id.registerTextView)

        // evento para abrir la pantalla de registro
        registerTextView.setOnClickListener {
            // intent que iniciará la pantalla de registro
            val intent = Intent(this, RegistroActivity::class.java)
            // inicio
            startActivity(intent)
        }
    }
}
