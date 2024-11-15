package com.example.unplayedgameslist.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewbinding.ViewBinding
import com.example.unplayedgameslist.R
import com.example.unplayedgameslist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActions()
    }

    private fun setupActions() {
        val loginBtn = binding.btnLogin
        val registerBtn = binding.btnRegister

        registerBtn.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}