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

/**
 * Fragment that manages the user login process.
 * Handles login attempts by validating user credentials and automatically logging in the user if credentials are available.
 * Provides options for both login and registration.
 */
class LoginFragment : Fragment(R.layout.fragment_login) {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    /**
     * Initializes the fragment's view and view model, sets up UI actions,
     * and tries to auto-login the user if valid credentials exist.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

        // Instantiate the ViewModel
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        setupActions()

        // Automatically log in if credentials are saved
        if(loginViewModel.tryAutoLogin()){
            changeActivity()
        }
    }

    /**
     * Sets up actions for the login and register buttons.
     * Observes the login status from the ViewModel.
     */
    private fun setupActions() {
        // Login button click listener
        binding.btnLogin.setOnClickListener {
            val steamId = binding.etSteamID.text.toString()
            val password = binding.etPassword.text.toString()

            // Trigger the login process in the ViewModel
            loginViewModel.login(steamId, password)
        }

        // Observing the login status for success or failure
        loginViewModel.loginStatus.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                Toast.makeText(requireContext(), "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                changeActivity() // Navigate to the next screen on successful login
            } else {
                Toast.makeText(requireContext(), "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
            }
        }

        // Register button click listener to navigate to the registration fragment
        binding.btnRegister.setOnClickListener {
            (activity as MainActivity).changeFragment(RegisterFragment())
        }
    }

    /**
     * Navigates to the SteamGamesFragment after a successful login.
     */
    private fun changeActivity(){
        (activity as MainActivity).changeFragment(SteamGamesFragment())
    }
}

