package com.example.unplayedgameslist.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.unplayedgameslist.App
import com.example.unplayedgameslist.R
import com.example.unplayedgameslist.data.PreferencesManager
import com.example.unplayedgameslist.databinding.FragmentLoginBinding
import com.example.unplayedgameslist.ui.MainActivity
import com.example.unplayedgameslist.ui.viewmodels.LoginViewModel

class LoginFragment : Fragment(R.layout.fragment_login) {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

        // asignación del viewmodel
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        setupActions()
    }

    private fun setupActions() {
        binding.btnLogin.setOnClickListener {
            // Si no hay credenciales guardadas, necesita iniciar sesion
            if(!loginViewModel.tryAutoLogin()){
                val steamId = binding.etSteamID.text.toString()
                val password = binding.etPassword.text.toString()
                loginViewModel.login(steamId, password)
            }
        }

        // Observar el estado del login
        loginViewModel.loginStatus.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                Toast.makeText(requireContext(), "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                (activity as MainActivity).changeFragment(SteamGamesFragment())
            } else {
                Toast.makeText(requireContext(), "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnRegister.setOnClickListener {
            (activity as MainActivity).changeFragment(RegisterFragment())
        }
    }
}
