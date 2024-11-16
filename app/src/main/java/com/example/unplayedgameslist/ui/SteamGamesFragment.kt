package com.example.unplayedgameslist.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unplayedgameslist.App
import com.example.unplayedgameslist.R
import com.example.unplayedgameslist.databinding.FragmentSteamGamesBinding

class SteamGamesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var gameAdapter: GameAdapter
    private lateinit var gameViewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla el layout del fragmento
        val binding = FragmentSteamGamesBinding.inflate(inflater, container, false)

        // Configura el RecyclerView
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context) // Set the LayoutManager
        gameAdapter = GameAdapter(emptyList())
        recyclerView.adapter = gameAdapter

        // Configura el ViewModel
        gameViewModel = ViewModelProvider(this)[GameViewModel::class.java]

        // Observar los cambios en la lista de juegos
        gameViewModel.gamesLiveData.observe(viewLifecycleOwner) { games ->
            gameAdapter.updateGames(games)  // Actualiza el adaptador con los nuevos juegos
        }

        // Cargar juegos desde la API
        val prefs = App.prefsManager
        val steamAPI = prefs.getSteamAPI()
        val steamID = prefs.getSteamID()

        if (steamAPI != null && steamID != null) {
            gameViewModel.loadGames(steamAPI, steamID)
        } else {
            Log.e("SteamGamesFragment", "SteamAPI o SteamID no encontrados en preferencias.")
        }

        return binding.root
    }
}
