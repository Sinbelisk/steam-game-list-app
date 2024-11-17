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

/**
 * Fragment representing a navigation menu with options for logging out and accessing settings.
 * Contains buttons for logout and settings, each with corresponding actions.
 */
class NavigationMenuFragment : Fragment() {

    companion object {
        // Factory method for creating a new instance of the fragment.
        fun newInstance() = NavigationMenuFragment()
    }

    private val viewModel: NavigationMenuViewModel by viewModels()

    // Button references
    private lateinit var logoutButton: Button
    private lateinit var optionsButton: Button

    /**
     * Inflates the fragment's view, initializes buttons, and sets up click listeners for actions.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSettingsBarBinding.inflate(inflater, container, false)

        // Initialize buttons
        logoutButton = binding.logoutButton
        optionsButton = binding.optionsButton

        // Configure button actions
        logoutButton.setOnClickListener {
            // Triggers logout in the ViewModel
            viewModel.onLogoutClicked()

            // Navigate to the login screen
            (activity as MainActivity).changeFragment(LoginFragment())
        }

        optionsButton.setOnClickListener {
            // Show the settings dialog
            showSettingsDialog()
        }

        return binding.root
    }

    /**
     * Displays the settings dialog.
     */
    private fun showSettingsDialog() {
        val settingsFragment = SettingsDialogFragment.newInstance()
        settingsFragment.show(parentFragmentManager, "settingsDialog")
    }
}
