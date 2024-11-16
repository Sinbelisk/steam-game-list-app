package com.example.unplayedgameslist.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.unplayedgameslist.App
import com.example.unplayedgameslist.R
import com.example.unplayedgameslist.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRegisterBinding
    private lateinit var prefs : PreferencesManager

    // Constantes globales
    companion object {
        private const val INCORRECT_USER_MESSAGE: String = "ERROR: el usuario ya existe"
        private const val INCORRECT_API_FORMAT: String = "ERROR: formato de clave API invalido, debe tener 32 carácteres de longitud y carácteres alfanuméricos"
        private const val REGISTER_SUCCESSFUL: String = "Registración de SteamID y API KEY Completados"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefs = App.prefsManager

        val registerBtn = binding.btnRegisterRegister
        registerBtn.setOnClickListener{
            registerAction()
        }
    }

    private fun registerAction(){
        // Le he puesto 2 veces register para diferenciarlo de la otra... no muy original.
        val etSteamId = binding.etSteamIDRegister
        val etSteamPassword = binding.etPasswordRegister
        val etApiKey = binding.etSteamApi

        val steamId = etSteamId.text.toString()
        if (steamId.isEmpty()) {
            Toast.makeText(this, "SteamID no puede estar vacío", Toast.LENGTH_SHORT).show()
            return
        }

        // Validar API Key
        val apiKey = etApiKey.text.toString()
        if (!isValidApiKey(apiKey)) {
            Toast.makeText(this, INCORRECT_API_FORMAT, Toast.LENGTH_SHORT).show()
            return // Detener la ejecución si la API Key es incorrecta
        }

        prefs.saveData(
            etSteamId.text.toString(),
            etSteamPassword.text.toString(),
            etApiKey.text.toString())

        Toast.makeText(this, REGISTER_SUCCESSFUL, Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun isValidApiKey(apiKey : String) :Boolean {
        // Patron regex para validar la api de Steam:
        // 32 caracteres de longitud y caracteres alfanuméricos
        val apiKeyPattern = Regex("^[a-zA-Z0-9]{32}\$")
        return apiKeyPattern.matches(apiKey)
    }
}