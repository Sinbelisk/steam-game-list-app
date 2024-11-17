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
import kotlin.collections.joinToString
import com.example.unplayedgameslist.R
import com.example.unplayedgameslist.data.db.GameDetailEntity
import com.example.unplayedgameslist.data.db.GameEntity
import com.example.unplayedgameslist.databinding.FragmentGameDetailDialogBinding
import com.example.unplayedgameslist.ui.viewmodels.GameDetailViewModel

class GameDetailDialogFragment(private val gameEntity: GameEntity) : DialogFragment() {

    private lateinit var binding: FragmentGameDetailDialogBinding
    private lateinit var viewModel: GameDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameDetailDialogBinding.inflate(inflater, container, false)

        // Instanciar el ViewModel
        viewModel = ViewModelProvider(this, GameDetailViewModelFactory(gameEntity))[GameDetailViewModel::class.java]

        // Configurar observadores
        setupObservers()

        // Configurar botón de cierre
        binding.closeButton.setOnClickListener { dismiss() }

        return binding.root
    }

    /**
     * Configura los observadores para los datos del ViewModel.
     */
    private fun setupObservers() {
        viewModel.gameDetails.observe(viewLifecycleOwner) { details ->
            if (details != null) {
                displayGameDetails(details, gameEntity)
            } else {
                Log.e(TAG, "Error al cargar los detalles del juego")
            }
        }

        viewModel.selectedOption.observe(viewLifecycleOwner) { selectedOption ->
            Log.d(TAG, "Opción seleccionada: $selectedOption")
        }
    }

    /**
     * Muestra los detalles del juego en los elementos de la UI.
     */
    private fun displayGameDetails(details: GameDetailEntity, game: GameEntity) {
        binding.gameName.text = details.name
        binding.gameReleaseDate.text = game.releaseDate ?: getString(R.string.unknown_release_date)
        //binding.gameGenre.text = details.genres ?: getString(R.string.unknown_genre)

        // Muestra los desarrolladores y publicadores si están disponibles
        binding.gameDevelopers.text =
            getString(R.string.developers, details.developers)

        binding.gamePublishers.text =
            getString(R.string.publishers, details.publishers)

        binding.gameDescription.text = details.shortDescription

        Glide.with(requireContext())
            .load(details.header)
            .placeholder(R.drawable.image_placeholder)
            .error(R.drawable.image_error)
            .into(binding.gameImage)
    }


    /**
     * Fábrica para instanciar el ViewModel con el GameEntity necesario.
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

        fun newInstance(gameEntity: GameEntity): GameDetailDialogFragment {
            return GameDetailDialogFragment(gameEntity)
        }
    }
}
