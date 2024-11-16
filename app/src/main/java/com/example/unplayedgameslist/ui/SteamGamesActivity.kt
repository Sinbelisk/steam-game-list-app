package com.example.unplayedgameslist.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unplayedgameslist.R

class SteamGamesActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var gameAdapter: GameAdapter
    private lateinit var gameViewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_steam_games)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Configurar el ViewModel
        gameViewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        // Observar los juegos
        gameViewModel.gamesLiveData.observe(this, Observer { games ->
            // Aquí actualizas el adaptador con los juegos obtenidos
            gameAdapter = GameAdapter(games)
            recyclerView.adapter = gameAdapter
        })

        // Cargar juegos desde la API o base de datos
        gameViewModel.loadGames()
    }
}