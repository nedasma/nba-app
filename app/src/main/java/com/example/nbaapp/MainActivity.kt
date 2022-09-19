package com.example.nbaapp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.nbaapp.databinding.ActivityMainBinding
import com.example.nbaapp.ui.AppViewModel
import com.example.nbaapp.ui.fragments.HomeFragment
import com.example.nbaapp.ui.fragments.PlayerFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val viewModel: AppViewModel by viewModels()

    lateinit var navbar: BottomNavigationView

    // region overridden methods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadFragment(HomeFragment.newInstance())

        navbar = findViewById(R.id.bottomNavigationView)

        navbar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home_tab_fragment -> {
                    loadFragment(HomeFragment.newInstance())
                    return@setOnItemSelectedListener true
                }
                R.id.player_fragment -> {
                    loadFragment(PlayerFragment.newInstance())
                    return@setOnItemSelectedListener true
                }
                else -> {
                    return@setOnItemSelectedListener false
                }
            }
        }
    }

    // endregion

    /**
     * Helper function to create (load) a new [fragment].
     */
    private fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}