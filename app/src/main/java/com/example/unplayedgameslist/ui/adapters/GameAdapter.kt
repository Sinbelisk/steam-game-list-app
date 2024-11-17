package com.example.unplayedgameslist.ui.adapters


import android.content.Context
import android.util.Log
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

class GameAdapter(private var games: List<GameEntity>, private val context: Context) : RecyclerView.Adapter<GameAdapter.GameViewHolder>() {

    inner class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val gameName: TextView = itemView.findViewById(R.id.gameName)
        private val gameTotalHours: TextView = itemView.findViewById(R.id.gamePlayedHours)
        private val gameImage: ImageView = itemView.findViewById(R.id.gameImage)

        fun bind(game: GameEntity) {
            gameName.text = getFormattedString(R.string.gameName, game.name)
            gameTotalHours.text = getFormattedString(R.string.playedHours, game.playtime / 60)
            loadImage(game.imageUrl)
        }

        private fun loadImage(imageUrl: String?) {
            Glide.with(itemView.context)
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(gameImage)
        }

        private fun getFormattedString(stringId: Int, value: Any): String {
            return when (value) {
                is String -> context.getString(stringId, value)
                is Int -> context.getString(stringId, value)
                is Double -> context.getString(stringId, value)
                else -> context.getString(stringId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.game_list_item, parent, false)
        return GameViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = games[position]
        holder.bind(game)
    }

    override fun getItemCount(): Int = games.size

    fun updateGames(newGames: List<GameEntity>) {
        games = newGames.toList()
        notifyDataSetChanged()
    }
}

