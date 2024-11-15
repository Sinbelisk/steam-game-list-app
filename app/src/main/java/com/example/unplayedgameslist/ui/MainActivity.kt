package com.example.unplayedgameslist.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.unplayedgameslist.App
import com.example.unplayedgameslist.R
import com.example.unplayedgameslist.databinding.ActivityMainBinding
import java.util.Objects

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private val prefs : PrefernecesManager = App.prefsManager

    companion object{
        private const val SUCCESS_LOGIN = "Inicio de sesión exitoso"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActions()
    }

    private fun setupActions(){
        val loginBtn = binding.btnLogin
        loginBtn.setOnClickListener{
            login()
        }

        val registerBtn = binding.btnRegister
        registerBtn.setOnClickListener{
            changeActivity(RegisterActivity::class.java)
        }
    }

    private fun login() {
        val etSteamId = binding.etSteamID
        val etPassword = binding.etPassword

        // Recuperar los datos guardados
        val savedSteamId = prefs.getSteamID()
        val savedPassword = prefs.getPassword()

        // Verificar si los valores recuperados son nulos o vacíos
        if (savedSteamId.isNullOrEmpty() || savedPassword.isNullOrEmpty()) {
            Toast.makeText(this, "Error, datos no encontrados", Toast.LENGTH_SHORT).show()
            return
        }

        // Comparar los datos ingresados con los almacenados
        if (etSteamId.text.toString() != savedSteamId) {
            Toast.makeText(this, "Error, usuario no encontrado", Toast.LENGTH_SHORT).show()
            return
        }

        if (etPassword.text.toString() != savedPassword) {
            Toast.makeText(this, "Error, contraseña incorrecta", Toast.LENGTH_SHORT).show()
            return
        }

        Toast.makeText(this, SUCCESS_LOGIN, Toast.LENGTH_SHORT).show()
        changeActivity(SteamGamesActivity::class.java)
    }


    // funcion para cambiar de actividad
    private fun <T : Activity> changeActivity(activity: Class<T>) {
        val intent = Intent(this, activity)
        startActivity(intent)
    }
}