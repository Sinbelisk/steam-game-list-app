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
        recyclerView.layoutManager = LinearLayoutManager(context)
        gameAdapter = GameAdapter(emptyList(), requireContext())
        recyclerView.adapter = gameAdapter

        // Configura el ViewModel
        gameViewModel = ViewModelProvider(this)[GameViewModel::class.java]

        // Observar los cambios en la lista de juegos
        gameViewModel.gamesLiveData.observe(viewLifecycleOwner) { games ->
            gameAdapter.updateGames(games)  // Actualiza el adaptador con los nuevos juegos
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //carga los juegos.
        gameViewModel.loadGames()
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){

        }
    }
}
