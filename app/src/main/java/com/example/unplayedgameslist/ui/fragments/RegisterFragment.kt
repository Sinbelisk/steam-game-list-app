package com.example.unplayedgameslist.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import com.example.unplayedgameslist.App
import com.example.unplayedgameslist.R
import com.example.unplayedgameslist.data.PreferencesManager
import com.example.unplayedgameslist.databinding.FragmentRegisterBinding
import com.example.unplayedgameslist.ui.MainActivity

class RegisterFragment : Fragment(R.layout.fragment_register) {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var prefs: PreferencesManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)

        prefs = App.prefsManager

        binding.btnRegisterRegister.setOnClickListener {
            registerAction()
        }
    }

    private fun registerAction() {
        // Lógica de validación y guardado de datos
        prefs.saveData(
            binding.etSteamIDRegister.text.toString(),
            binding.etPasswordRegister.text.toString(),
            binding.etSteamApi.text.toString()
        )
        Toast.makeText(requireContext(), "Registración exitosa", Toast.LENGTH_SHORT).show()
        (activity as MainActivity).changeFragment(LoginFragment())  // Cambia de vuelta a LoginFragment
    }
}