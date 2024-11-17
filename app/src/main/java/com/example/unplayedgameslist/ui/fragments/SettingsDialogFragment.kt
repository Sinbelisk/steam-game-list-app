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

class SettingsDialogFragment : DialogFragment() {

    private lateinit var sortSpinner: Spinner
    private lateinit var excludePlayedCheckbox: CheckBox
    private lateinit var saveButton: Button
    private lateinit var cancelButton: Button

    // Cambia el acceso al ViewModel a través de la actividad
    private lateinit var settingsViewModel: SettingsDialogViewModel

    companion object {
        fun newInstance() = SettingsDialogFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSettingsDialogBinding.inflate(inflater, container, false)

        // Inicializamos el SettingsDialogViewModel para ser compartido entre fragmentos
        settingsViewModel = ViewModelProvider(requireActivity()).get(SettingsDialogViewModel::class.java)

        // Inicialización de los elementos de la vista
        sortSpinner = binding.sortSpinner
        excludePlayedCheckbox = binding.excludePlayedCheckbox
        saveButton = binding.saveButton
        cancelButton = binding.cancelButton

        // Configurar el Spinner con opciones de ordenación
        setupSortSpinner()

        // Observar cambios en el ViewModel
        observeViewModel()

        // Configuración de las acciones de los botones
        saveButton.setOnClickListener { onSaveClicked() }
        cancelButton.setOnClickListener { onCancelClicked() }

        return binding.root
    }

    private fun setupSortSpinner() {
        val sortOptions = SortType.entries.map { it.label }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, sortOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sortSpinner.adapter = adapter
    }

    private fun observeViewModel() {
        settingsViewModel.sortOption.observe(viewLifecycleOwner) { selectedOption ->
            val position = SortType.values().indexOf(selectedOption)
            sortSpinner.setSelection(position)
        }

        settingsViewModel.excludePlayed.observe(viewLifecycleOwner) { exclude ->
            excludePlayedCheckbox.isChecked = exclude
        }
    }

    private fun onSaveClicked() {
        val selectedSortLabel = sortSpinner.selectedItem.toString()
        val selectedSortOption = SortType.values().first { it.label == selectedSortLabel }
        val excludePlayed = excludePlayedCheckbox.isChecked

        // Actualizamos el ViewModel con los valores seleccionados
        settingsViewModel.apply {
            setSortOption(selectedSortOption)
            setExcludePlayed(excludePlayed)
        }

        // Guardamos la configuración (puedes agregar lógica de almacenamiento persistente aquí)
        settingsViewModel.saveSettings()

        dismiss()
    }

    private fun onCancelClicked() {
        // Restablecemos las configuraciones a los valores predeterminados
        settingsViewModel.resetSettings()
        dismiss()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)

        // Ajuste para el tamaño del diálogo
        val window = dialog.window
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(window?.attributes)
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        window?.attributes = layoutParams

        return dialog
    }
}
