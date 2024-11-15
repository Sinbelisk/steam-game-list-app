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
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun login(){
        val etSteamId = binding.etSteamID
        val etPassword = binding.etPassword

        if(!etSteamId.text.equals(prefs.getSteamID())){
            Toast.makeText(this, "Error, usuario no encontrado", Toast.LENGTH_SHORT)
                .show()
            return
        }
        else if (!etPassword.text.equals(prefs.getPassword())){
            Toast.makeText(this, "Error, contraseña incorrecta", Toast.LENGTH_SHORT).show()
            return
        }

        changeActivity(SteamGamesActivity::class.java)
    }

    private fun <T : Activity> changeActivity(activity: Class<T>) {
        val intent = Intent(this, activity)
        startActivity(intent)
    }
}