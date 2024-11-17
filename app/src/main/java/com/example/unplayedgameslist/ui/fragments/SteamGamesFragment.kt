package com.example.unplayedgameslist.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unplayedgameslist.App
import com.example.unplayedgameslist.databinding.FragmentSteamGamesBinding
import com.example.unplayedgameslist.ui.SortType
import com.example.unplayedgameslist.ui.adapters.GameAdapter
import com.example.unplayedgameslist.ui.viewmodels.GameViewModel
import com.example.unplayedgameslist.ui.viewmodels.SettingsDialogViewModel

class SteamGamesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var gameAdapter: GameAdapter
    private lateinit var gameViewModel: GameViewModel
    private lateinit var settingsDialogViewModel: SettingsDialogViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSteamGamesBinding.inflate(inflater, container, false)

        // Inicialización de ViewModels
        initViewModels()

        // Inicialización de RecyclerView y Adapter
        initRecyclerView(binding)

        // Observamos los cambios en las preferencias
        observeSettingsChanges()

        return binding.root
    }

    private fun initViewModels() {
        gameViewModel = ViewModelProvider(this)[GameViewModel::class.java]
        settingsDialogViewModel = ViewModelProvider(requireActivity()).get(SettingsDialogViewModel::class.java)
    }

    private fun initRecyclerView(binding: FragmentSteamGamesBinding) {
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        gameAdapter = GameAdapter(emptyList(), requireContext())  // Lista vacía inicialmente
        recyclerView.adapter = gameAdapter
    }

    private fun observeSettingsChanges() {
        settingsDialogViewModel.sortOption.observe(viewLifecycleOwner) { sortOption ->
            val excludePlayed = settingsDialogViewModel.excludePlayed.value ?: false
            loadGames(sortOption, excludePlayed)
        }

        settingsDialogViewModel.excludePlayed.observe(viewLifecycleOwner) { excludePlayed ->
            val sortOption = settingsDialogViewModel.sortOption.value ?: SortType.DESC
            loadGames(sortOption, excludePlayed)
        }

        // Observamos la LiveData de juegos de GameViewModel
        gameViewModel.gamesLiveData.observe(viewLifecycleOwner) { games ->
            games?.let { gameAdapter.updateGames(it) }
                ?: Log.w("SteamGamesFragment", "La lista de juegos está vacía.")
        }
    }

    private fun loadGames(sortOption: SortType, excludePlayed: Boolean) {
        gameViewModel.loadGames(sortOption, excludePlayed)  // Cargamos juegos con la nueva configuración
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Carga inicial de juegos según la configuración actual
        val sortOption = settingsDialogViewModel.sortOption.value ?: SortType.DESC
        val excludePlayed = settingsDialogViewModel.excludePlayed.value ?: false
        loadGames(sortOption, excludePlayed)
    }
}

