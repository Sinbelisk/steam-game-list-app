package com.example.unplayedgameslist.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import com.example.unplayedgameslist.App
import com.example.unplayedgameslist.R
import com.example.unplayedgameslist.data.PreferencesManager
import com.example.unplayedgameslist.databinding.FragmentLoginBinding
import com.example.unplayedgameslist.ui.MainActivity

class LoginFragment : Fragment(R.layout.fragment_login) {
    private lateinit var binding: FragmentLoginBinding
    private val prefs: PreferencesManager = App.prefsManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

        setupActions()
    }

    private fun setupActions() {
        binding.btnLogin.setOnClickListener {
            login()
        }

        binding.btnRegister.setOnClickListener {
            (activity as MainActivity).changeFragment(RegisterFragment()) // Cambia a RegisterFragment
        }
    }

    private fun login() {
        val savedSteamId = prefs.getSteamID()
        val savedPassword = prefs.getPassword()

        // Verificación de los datos almacenados
        // ...
        Toast.makeText(requireContext(), "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
        (activity as MainActivity).changeFragment(SteamGamesFragment())  // Cambia a SteamGamesFragment
    }
}