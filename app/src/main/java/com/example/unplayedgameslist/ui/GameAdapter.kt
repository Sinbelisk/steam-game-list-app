package com.example.unplayedgameslist.ui


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.unplayedgameslist.R
import com.example.unplayedgameslist.data.db.GameEntity

class GameAdapter(private var games: List<GameEntity>) : RecyclerView.Adapter<GameAdapter.GameViewHolder>() {

    // ViewHolder para enlazar cada ítem con su vista
    inner class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val gameName: TextView = itemView.findViewById(R.id.gameName)
        private val gameGenre: TextView = itemView.findViewById(R.id.gameGenre)
        private val gameImage: ImageView = itemView.findViewById(R.id.gameImage)

        // "Bindear" a la lista los elementos
        fun bind(game: GameEntity) {
            gameName.text = game.name
            gameGenre.text = game.genre ?: "N/A" // Handle null genre
            loadImage(game.imageUrl)
        }

        private fun loadImage(imageUrl: String?) {
            Glide.with(itemView.context)
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(gameImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.game_list_item, parent, false)
        return GameViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = games[position]
        holder.bind(game)  // Asocia el juego de la lista con la vista
    }

    override fun getItemCount(): Int = games.size  // Devuelve el tamaño de la lista de juegos

    // Método para actualizar los datos del adaptador
    fun updateGames(newGames: List<GameEntity>) {
        games = newGames
        notifyDataSetChanged()  // Refreshes the adapter to show the updated list
    }
}
