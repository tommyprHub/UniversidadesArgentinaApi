package com.tommy.universidadesargentinaapi

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import kotlinx.coroutines.launch

class RegistroActivity : AppCompatActivity() {

    private lateinit var room: DBPrueba

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        // Inicializar la base de datos
        room = Room.databaseBuilder(applicationContext, DBPrueba::class.java, "dbPruebas").build()

        // Referencias a los EditText
        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val etConfirmPassword = findViewById<EditText>(R.id.etConfirmPassword)

        // Referencia al botón y establecer el listener
        findViewById<Button>(R.id.btnGuardar).setOnClickListener {
            // Obtener los valores de los EditText
            val nombre = etNombre.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            val confirmPassword = etConfirmPassword.text.toString()

            // Verificar que las contraseñas coincidan y no estén vacías
            if (password == confirmPassword && nombre.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                // Crear una instancia de Usuario
                val usuario = Usuario(nombre, email, password)

                // Guardar el usuario en la base de datos
                addUsuario(room, usuario)
            } else {
                // Mostrar mensaje de error
                Toast.makeText(this, "Las contraseñas no coinciden o hay campos vacíos", Toast.LENGTH_SHORT).show()
            }
        }

        // Referencia al CheckBox y establecer el listener
        val showPasswordCheckBoxReg = findViewById<CheckBox>(R.id.showPasswordCheckBoxReg)

        showPasswordCheckBoxReg.setOnCheckedChangeListener { _, isChecked ->
            // Si el CheckBox está marcado, mostrar la contraseña
            if (isChecked) {
                etPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                etConfirmPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else {
                etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                etConfirmPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            }
            // Para mantener el cursor al final del texto
            etPassword.setSelection(etPassword.text.length)
            etConfirmPassword.setSelection(etConfirmPassword.text.length)
        }
    }

    private fun addUsuario(room: DBPrueba, usuario: Usuario) {
        lifecycleScope.launch {
            // Verificar si el nombre de usuario ya existe
            val usuarioExistente = room.daoUsuario().buscarPorNombre(usuario.nombre)
            if (usuarioExistente == null) {
                // Si no existe, procedemos a crear el nuevo usuario
                try {
                    room.daoUsuario().crearUsuario(usuario)
                    runOnUiThread {
                        Toast.makeText(applicationContext, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    runOnUiThread {
                        Toast.makeText(applicationContext, "Error al registrar el usuario: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                // Usuario ya existe
                runOnUiThread {
                    Toast.makeText(applicationContext, "El nombre de usuario ya existe, por favor elige otro", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
