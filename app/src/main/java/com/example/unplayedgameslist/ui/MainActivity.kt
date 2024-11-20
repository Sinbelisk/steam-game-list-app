package com.example.unplayedgameslist.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import com.example.unplayedgameslist.App
import com.example.unplayedgameslist.R
import com.example.unplayedgameslist.databinding.ActivityMainBinding
import com.example.unplayedgameslist.ui.fragments.LoginFragment
import com.example.unplayedgameslist.ui.fragments.NavigationMenuFragment
import kotlinx.coroutines.launch


/**
 * MainActivity is the entry point of the application.
 * It is responsible for managing the main user interface and fragment navigation.
 * The activity also initializes key dependencies such as preferences and the repository.
 */
class MainActivity : AppCompatActivity() {

    // ViewBinding instance to access UI elements in the activity
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enables the edge-to-edge mode for immersive UI experience
        enableEdgeToEdge()

        // Set up the content view for the activity
        setContentView(R.layout.activity_main)

        // Initialize ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // If it's the first time the activity is being created, replace with the Login fragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, LoginFragment())
                .commit()
        }
    }

    /**
     * Changes the currently displayed fragment.
     * It replaces the current fragment with a new one and adds it to the back stack for navigation.
     *
     * @param fragment The new fragment to display.
     */
    fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)  // Allows navigating back to the previous fragment
            .commit()
    }
}
