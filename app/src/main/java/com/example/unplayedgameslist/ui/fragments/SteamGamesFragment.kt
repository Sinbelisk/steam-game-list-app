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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSteamGamesBinding.inflate(inflater, container, false)

        // Inicialización del ViewModel
        gameViewModel = ViewModelProvider(this)[GameViewModel::class.java]

        // Obtener la lista inicial desde el ViewModel
        val initialGames = gameViewModel.gamesLiveData.value ?: emptyList()

        // Configuración inicial del RecyclerView y adaptador
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        gameAdapter = GameAdapter(initialGames, requireContext())
        recyclerView.adapter = gameAdapter

        // Observa cambios en la lista de juegos y actualiza el adaptador
        gameViewModel.gamesLiveData.observe(viewLifecycleOwner) { games ->
            if (!games.isNullOrEmpty()) {
                gameAdapter.updateGames(games) // Actualiza el adaptador con los nuevos datos
            } else {
                Log.w("SteamGamesFragment", "La lista de juegos está vacía.")
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Carga inicial de juegos desde el ViewModel
        gameViewModel.loadGames()

        // Manejo del botón de retroceso
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {

        }
    }
}
