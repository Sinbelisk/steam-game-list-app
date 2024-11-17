package com.example.unplayedgameslist.ui.adapters


import android.content.Context
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

/**
 * The GameAdapter is responsible for displaying a list of games in a RecyclerView.
 * It binds a list of GameEntity objects to the views and handles user interactions with the game items.
 *
 * @param games List of GameEntity objects to display in the RecyclerView.
 * @param context The context used to access resources and perform operations such as string formatting and image loading.
 * @param onGameClickListener A callback function triggered when a user clicks on a game item.
 */
class GameAdapter(
    private var games: List<GameEntity>,
    private val context: Context,
    private val onGameClickListener: (GameEntity) -> Unit // Callback triggered on game item click
) : RecyclerView.Adapter<GameAdapter.GameViewHolder>() {

    /**
     * ViewHolder class to hold references to views in each list item (game item).
     * Responsible for binding the game data to the views.
     */
    inner class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val gameName: TextView = itemView.findViewById(R.id.gameName)
        private val gameTotalHours: TextView = itemView.findViewById(R.id.gamePlayedHours)
        private val gameStatus : TextView = itemView.findViewById(R.id.status)
        private val gameImage: ImageView = itemView.findViewById(R.id.gameImage)

        /**
         * Binds the game data to the corresponding views.
         *
         * @param game The GameEntity object containing the game data to display.
         */
        fun bind(game: GameEntity) {
            // Set the formatted game name and playtime
            gameName.text = getFormattedString(R.string.gameName, game.name)
            gameTotalHours.text = getFormattedString(R.string.playedHours, game.playtime / 60)
            gameStatus.text = game.status?.let { getFormattedString(R.string.gameState, it) }

            // Load the game image using Glide
            loadImage(game.imageUrl)

            // Set the onClickListener to trigger the callback
            itemView.setOnClickListener {
                onGameClickListener(game) // Trigger the click listener callback
            }
        }

        /**
         * Loads the game image from the provided URL using the Glide image loading library.
         *
         * @param imageUrl The URL of the image to load.
         */
        private fun loadImage(imageUrl: String?) {
            Glide.with(itemView.context)
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.NONE) // No disk caching for images
                .into(gameImage)
        }

        /**
         * Formats a string by inserting a value (game name, playtime, etc.) into the string resource.
         *
         * @param stringId The resource ID of the string.
         * @param value The value to insert into the string.
         * @return The formatted string.
         */
        private fun getFormattedString(stringId: Int, value: Any): String {
            return when (value) {
                is String -> context.getString(stringId, value)
                is Int -> context.getString(stringId, value)
                is Double -> context.getString(stringId, value)
                else -> context.getString(stringId) // Default case for unsupported types
            }
        }
    }

    /**
     * Creates a new GameViewHolder for each item in the RecyclerView.
     *
     * @param parent The parent view group that the new view will be added to.
     * @param viewType The view type of the item (used for different types of items, but not used here).
     * @return A new GameViewHolder instance.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.game_list_item, parent, false)
        return GameViewHolder(itemView)
    }

    /**
     * Binds a GameEntity object to the GameViewHolder at the specified position.
     *
     * @param holder The GameViewHolder to bind the data to.
     * @param position The position of the game in the list.
     */
    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = games[position]
        holder.bind(game) // Bind the game data to the views
    }

    /**
     * Returns the total number of items (games) in the RecyclerView.
     *
     * @return The number of games in the list.
     */
    override fun getItemCount(): Int = games.size

    /**
     * Updates the list of games in the adapter and notifies the RecyclerView of the data change.
     *
     * @param newGames The new list of GameEntity objects to display.
     */
    fun updateGames(newGames: List<GameEntity>) {
        games = newGames.toList() // Update the games list
        notifyDataSetChanged() // Notify the RecyclerView to refresh the display
    }
}
