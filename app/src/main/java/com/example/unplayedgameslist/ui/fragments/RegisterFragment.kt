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

class RegisterFragment : Fragment(R.layout.fragment_register) {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)

        registerViewModel = ViewModelProvider(this)[RegisterViewModel::class.java]

        // Observar el estado de la registración
        registerViewModel.registerStatus.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                Toast.makeText(requireContext(), "Registración exitosa", Toast.LENGTH_SHORT).show()
                (activity as MainActivity).changeFragment(LoginFragment())  // Cambia a LoginFragment
            } else {
                Toast.makeText(requireContext(), "Error en la registración, comprueba que la SteamID y la clave APi sean validas", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnRegisterRegister.setOnClickListener {
            val steamID = binding.etSteamIDRegister.text.toString()
            val password = binding.etPasswordRegister.text.toString()
            val apiKey = binding.etSteamApi.text.toString()

            // Llamar al ViewModel para realizar la registración
            registerViewModel.register(steamID, password, apiKey)
        }
    }
}
