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
import com.example.unplayedgameslist.ui.adapters.GameAdapter
import com.example.unplayedgameslist.ui.viewmodels.GameViewModel
import com.example.unplayedgameslist.ui.viewmodels.SettingsDialogViewModel

class SteamGamesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var gameAdapter: GameAdapter
    private lateinit var gameViewModel: GameViewModel
    private lateinit var settingsViewModel: SettingsDialogViewModel  // Añadir el ViewModel de configuraciones

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSteamGamesBinding.inflate(inflater, container, false)

        // Configura el RecyclerView
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        gameAdapter = GameAdapter(emptyList(), requireContext())
        recyclerView.adapter = gameAdapter

        // Configura el ViewModel
        gameViewModel = ViewModelProvider(this)[GameViewModel::class.java]
        settingsViewModel = ViewModelProvider(this)[SettingsDialogViewModel::class.java] // Inicializa el ViewModel de configuraciones

        // Observa cambios en la lista de juegos
        gameViewModel.gamesLiveData.observe(viewLifecycleOwner) { games ->
            // Verifica si la lista ha cambiado y actualiza el adaptador
            if (games != null) {
                gameAdapter.updateGames(games)
            }
        }

        settingsViewModel.sortOption.observe(viewLifecycleOwner) {
            gameViewModel.loadGames()  // Recargar juegos con la nueva configuración de orden
        }

        settingsViewModel.excludePlayed.observe(viewLifecycleOwner) {
            gameViewModel.loadGames()  // Recargar juegos con la nueva configuración de exclusión de jugados
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Cargar los juegos
        gameViewModel.loadGames()

        // Agregar un callback para que las configuraciones se recarguen al cambiar
        App.prefsManager.apply {
            getSortType()?.let { gameViewModel.loadGames() }
            getHidePlayed().let { gameViewModel.loadGames() }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            // Aquí puedes manejar el evento de la tecla "Atrás"
        }
    }
}