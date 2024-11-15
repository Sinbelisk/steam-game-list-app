package com.example.unplayedgameslist.ui

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.unplayedgameslist.App
import com.example.unplayedgameslist.R
import com.example.unplayedgameslist.databinding.ActivityMainBinding
import com.example.unplayedgameslist.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRegisterBinding
    private lateinit var prefs : PrefernecesManager

    // Algunas constantes globales para esta actividad
    private val INCORRECT_USER_MESSAGE : String = "ERROR: el usuario ya existe"
    private val INCORRECT_API_FORMAT : String =
        "ERROR: formato de clave API invalido, debe tener 32 carácteres de longitud y carácteres" +
                "alfanuméricos"


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
        val etApiKey = binding.etSteamApi

        if(!etSteamId.text.equals(prefs.getSteamID())){
            Toast.makeText(this, INCORRECT_USER_MESSAGE, Toast.LENGTH_SHORT).show()
            return
        }
        else if(!isValidApiKey(etApiKey.text.toString())){
            Toast.makeText(this, INCORRECT_API_FORMAT, Toast.LENGTH_SHORT).show()
        }
    }

    private fun isValidApiKey(apiKey : String) :Boolean {
        // Patron regex para validar la api de Steam:
        // 32 caracteres de longitud y caracteres alfanuméricos
        val apiKeyPattern = Regex("^[a-zA-Z0-9]{32}\$")
        return apiKeyPattern.matches(apiKey)
    }
}