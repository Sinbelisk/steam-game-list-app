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
import com.example.unplayedgameslist.R
import com.example.unplayedgameslist.databinding.FragmentSteamGamesBinding
import com.example.unplayedgameslist.ui.SortType
import com.example.unplayedgameslist.ui.adapters.GameAdapter
import com.example.unplayedgameslist.ui.viewmodels.GameViewModel
import com.example.unplayedgameslist.ui.viewmodels.SettingsDialogViewModel

/**
 * Fragment that displays a list of Steam games in a RecyclerView.
 * It allows users to interact with the list and displays game details in a dialog.
 */
class SteamGamesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var gameAdapter: GameAdapter
    private lateinit var gameViewModel: GameViewModel
    private lateinit var settingsDialogViewModel: SettingsDialogViewModel

    /**
     * Inflates the view and initializes the necessary ViewModels and RecyclerView.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSteamGamesBinding.inflate(inflater, container, false)

        // Initialize ViewModels
        initViewModels()

        // Initialize RecyclerView and Adapter
        initRecyclerView(binding)

        // Observe changes in the settings
        observeSettingsChanges()

        return binding.root
    }

    /**
     * Initializes the ViewModels used in this fragment.
     */
    private fun initViewModels() {
        gameViewModel = ViewModelProvider(this)[GameViewModel::class.java]
        settingsDialogViewModel = ViewModelProvider(requireActivity()).get(SettingsDialogViewModel::class.java)
    }

    /**
     * Initializes the RecyclerView and the GameAdapter to display the games list.
     */
    private fun initRecyclerView(binding: FragmentSteamGamesBinding) {
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        gameAdapter = GameAdapter(emptyList(), requireContext()) { game ->
            val gameDetailFragment = GameDetailDialogFragment(game)
            gameDetailFragment.show(requireActivity().supportFragmentManager, "game_detail")
        }

        recyclerView.adapter = gameAdapter
    }

    /**
     * Observes changes to the settings, including sorting options and the exclude played filter.
     */
    private fun observeSettingsChanges() {
        settingsDialogViewModel.sortOption.observe(viewLifecycleOwner) { sortOption ->
            val excludePlayed = settingsDialogViewModel.excludePlayed.value ?: false
            loadGames(sortOption, excludePlayed)
        }

        settingsDialogViewModel.excludePlayed.observe(viewLifecycleOwner) { excludePlayed ->
            val sortOption = settingsDialogViewModel.sortOption.value ?: SortType.DESC
            loadGames(sortOption, excludePlayed)
        }

        // Observes the LiveData for the games list
        gameViewModel.gamesLiveData.observe(viewLifecycleOwner) { games ->
            games?.let { gameAdapter.updateGames(it) }
                ?: Log.w("SteamGamesFragment", "La lista de juegos está vacía.")
        }
    }

    /**
     * Loads the games based on the current sorting option and exclude played filter.
     */
    private fun loadGames(sortOption: SortType, excludePlayed: Boolean) {
        gameViewModel.loadGames(sortOption, excludePlayed)  // Load games with the updated settings
    }

    /**
     * Called after the fragment's view is created. Sets up the settings bar and loads the games initially.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Load the settings bar fragment in the container
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.settings_bar_container, NavigationMenuFragment.newInstance())
            .commit()

        // Load initial games according to the current settings
        val sortOption = settingsDialogViewModel.sortOption.value ?: SortType.DESC
        val excludePlayed = settingsDialogViewModel.excludePlayed.value ?: false
        loadGames(sortOption, excludePlayed)

        // Disable back navigation
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {

        }
    }
}

