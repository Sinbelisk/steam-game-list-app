package com.example.unplayedgameslist.ui.fragments

import android.R
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.Spinner
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.unplayedgameslist.databinding.FragmentSettingsDialogBinding
import com.example.unplayedgameslist.ui.SortType
import com.example.unplayedgameslist.ui.viewmodels.SettingsDialogViewModel

/**
 * Fragment representing a settings dialog where the user can modify sorting options and the filter for excluding played games.
 */
class SettingsDialogFragment : DialogFragment() {

    private lateinit var sortSpinner: Spinner
    private lateinit var excludePlayedCheckbox: CheckBox
    private lateinit var saveButton: Button
    private lateinit var cancelButton: Button

    // ViewModel shared across fragments
    private lateinit var settingsViewModel: SettingsDialogViewModel

    companion object {
        // Factory method for creating a new instance of the dialog
        fun newInstance() = SettingsDialogFragment()
    }

    /**
     * Inflates the dialog's view, sets up the spinner and checkbox, and observes the ViewModel for data changes.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSettingsDialogBinding.inflate(inflater, container, false)

        // Initialize ViewModel
        settingsViewModel = ViewModelProvider(requireActivity()).get(SettingsDialogViewModel::class.java)

        // Bind UI elements
        sortSpinner = binding.sortSpinner
        excludePlayedCheckbox = binding.excludePlayedCheckbox
        saveButton = binding.saveButton
        cancelButton = binding.cancelButton

        // Configure the spinner for sorting options
        setupSortSpinner()

        // Observe changes in the ViewModel
        observeViewModel()

        // Set click listeners for the buttons
        saveButton.setOnClickListener { onSaveClicked() }
        cancelButton.setOnClickListener { onCancelClicked() }

        return binding.root
    }

    /**
     * Sets up the spinner with available sorting options from the `SortType` enum.
     */
    private fun setupSortSpinner() {
        val sortOptions = SortType.entries.map { it.label }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, sortOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sortSpinner.adapter = adapter
    }

    /**
     * Observes changes in the ViewModel and updates the UI elements accordingly.
     */
    private fun observeViewModel() {
        settingsViewModel.sortOption.observe(viewLifecycleOwner) { selectedOption ->
            val position = SortType.values().indexOf(selectedOption)
            sortSpinner.setSelection(position)
        }

        settingsViewModel.excludePlayed.observe(viewLifecycleOwner) { exclude ->
            excludePlayedCheckbox.isChecked = exclude
        }
    }

    /**
     * Handles saving the user's settings, updating the ViewModel, and dismissing the dialog.
     */
    private fun onSaveClicked() {
        val selectedSortLabel = sortSpinner.selectedItem.toString()
        val selectedSortOption = SortType.values().first { it.label == selectedSortLabel }
        val excludePlayed = excludePlayedCheckbox.isChecked

        // Update ViewModel with selected settings
        settingsViewModel.apply {
            setSortOption(selectedSortOption)
            setExcludePlayed(excludePlayed)
        }

        // Save the settings (you can add logic for persistent storage here)
        settingsViewModel.saveSettings()

        dismiss()
    }

    /**
     * Handles the cancel button click, resetting settings to defaults and dismissing the dialog.
     */
    private fun onCancelClicked() {
        settingsViewModel.resetSettings()
        dismiss()
    }

    /**
     * Creates and customizes the dialog, including adjusting its size.
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)

        // Adjust dialog size
        val window = dialog.window
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(window?.attributes)
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        window?.attributes = layoutParams

        return dialog
    }
}
