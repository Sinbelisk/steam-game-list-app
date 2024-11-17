package com.example.unplayedgameslist.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.unplayedgameslist.R
import com.example.unplayedgameslist.data.db.GameDetailEntity
import com.example.unplayedgameslist.data.db.GameEntity
import com.example.unplayedgameslist.databinding.FragmentGameDetailDialogBinding
import com.example.unplayedgameslist.ui.viewmodels.GameDetailViewModel
/**
 * Fragment that displays detailed information about a specific game.
 * It is responsible for fetching and displaying the game's details,
 * including its name, release date, developers, publishers, description, and image.
 */
class GameDetailDialogFragment(private val gameEntity: GameEntity) : DialogFragment() {

    private lateinit var binding: FragmentGameDetailDialogBinding
    private lateinit var viewModel: GameDetailViewModel

    /**
     * Initializes the view and ViewModel, and sets up observers.
     * Also configures the close button for dismissing the dialog.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameDetailDialogBinding.inflate(inflater, container, false)

        // Instantiate the ViewModel with the gameEntity data
        viewModel = ViewModelProvider(this, GameDetailViewModelFactory(gameEntity))[GameDetailViewModel::class.java]

        // Set up observers for the LiveData in the ViewModel
        setupObservers()

        // Set up the close button to dismiss the fragment
        binding.closeButton.setOnClickListener { dismiss() }

        return binding.root
    }

    /**
     * Sets up the observers for the LiveData in the ViewModel.
     * Observes changes to the game details and selected options.
     */
    private fun setupObservers() {
        viewModel.gameDetails.observe(viewLifecycleOwner) { details ->
            if (details != null) {
                // Display the game details if available
                displayGameDetails(details, gameEntity)
            } else {
                // Log an error if details could not be loaded
                Log.e(TAG, "Error al cargar los detalles del juego")
            }
        }

        // Observe the selected option (if applicable)
        viewModel.selectedOption.observe(viewLifecycleOwner) { selectedOption ->
            Log.d(TAG, "Opción seleccionada: $selectedOption")
        }
    }

    /**
     * Displays the game's details in the UI.
     * Updates the UI elements with the game's name, release date, developers, publishers, description, and image.
     */
    private fun displayGameDetails(details: GameDetailEntity, game: GameEntity) {
        // Set game name, release date, and other details
        binding.gameName.text = details.name
        binding.gameReleaseDate.text = game.releaseDate ?: getString(R.string.unknown_release_date)

        // Developers and publishers
        binding.gameDevelopers.text = getString(R.string.developers, details.developers)
        binding.gamePublishers.text = getString(R.string.publishers, details.publishers)

        // Short description of the game
        binding.gameDescription.text = details.shortDescription

        // Load and display the game's image using Glide
        Glide.with(requireContext())
            .load(details.header)
            .placeholder(R.drawable.image_placeholder)
            .error(R.drawable.image_error)
            .into(binding.gameImage)
    }

    /**
     * ViewModel factory to instantiate the GameDetailViewModel with the necessary GameEntity.
     */
    inner class GameDetailViewModelFactory(private val gameEntity: GameEntity) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(GameDetailViewModel::class.java)) {
                return GameDetailViewModel(gameEntity) as T
            }
            throw IllegalArgumentException("Clase ViewModel desconocida")
        }
    }

    companion object {
        private const val TAG = "GameDetailDialogFragment"

        /**
         * Factory method to create a new instance of the fragment with a specific GameEntity.
         */
        fun newInstance(gameEntity: GameEntity): GameDetailDialogFragment {
            return GameDetailDialogFragment(gameEntity)
        }
    }
}

