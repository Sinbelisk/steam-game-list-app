package com.example.unplayedgameslist.ui.fragments

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.unplayedgameslist.R
import com.example.unplayedgameslist.databinding.FragmentSettingsBarBinding
import com.example.unplayedgameslist.databinding.FragmentSettingsDialogBinding
import com.example.unplayedgameslist.ui.MainActivity
import com.example.unplayedgameslist.ui.viewmodels.NavigationMenuViewModel

class NavigationMenuFragment : Fragment() {

    companion object {
        fun newInstance() = NavigationMenuFragment()
    }

    private val viewModel: NavigationMenuViewModel by viewModels()

    // Referencias a los botones
    private lateinit var logoutButton: Button
    private lateinit var optionsButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSettingsBarBinding.inflate(inflater, container, false)

        // Inicializar los botones
        logoutButton = binding.logoutButton
        optionsButton = binding.optionsButton

        // Configurar las acciones de los botones
        logoutButton.setOnClickListener {
            viewModel.onLogoutClicked()

            (activity as MainActivity).changeFragment(LoginFragment())
        }

        optionsButton.setOnClickListener {
            showSettingsDialog()
        }

        return binding.root
    }

    private fun showSettingsDialog() {
        val settingsFragment = SettingsDialogFragment.newInstance()
        settingsFragment.show(parentFragmentManager, "settingsDialog")
    }
}
