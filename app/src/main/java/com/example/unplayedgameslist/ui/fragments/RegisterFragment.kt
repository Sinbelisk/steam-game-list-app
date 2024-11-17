package com.example.unplayedgameslist.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.unplayedgameslist.R
import com.example.unplayedgameslist.databinding.FragmentRegisterBinding
import com.example.unplayedgameslist.ui.MainActivity
import com.example.unplayedgameslist.ui.viewmodels.RegisterViewModel
/**
 * Fragment for user registration where the user inputs SteamID, password, and API key.
 * Observes the registration status to show success or error messages.
 */
class RegisterFragment : Fragment(R.layout.fragment_register) {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel

    /**
     * Initializes the fragment's view, ViewModel, and observes the registration status.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)

        // Instantiate the ViewModel
        registerViewModel = ViewModelProvider(this)[RegisterViewModel::class.java]

        // Observe registration status
        registerViewModel.registerStatus.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                Toast.makeText(requireContext(), "Registración exitosa", Toast.LENGTH_SHORT).show()
                // Navigate to login fragment after successful registration
                (activity as MainActivity).changeFragment(LoginFragment())
            } else {
                Toast.makeText(requireContext(), "Error en la registración, comprueba que la SteamID y la clave APi sean validas", Toast.LENGTH_SHORT).show()
            }
        }

        // Register button click action
        binding.btnRegisterRegister.setOnClickListener {
            val steamID = binding.etSteamIDRegister.text.toString()
            val password = binding.etPasswordRegister.text.toString()
            val apiKey = binding.etSteamApi.text.toString()

            // Call the ViewModel to perform registration
            registerViewModel.register(steamID, password, apiKey)
        }
    }
}
