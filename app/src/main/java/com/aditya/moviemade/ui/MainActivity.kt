package com.aditya.moviemade.ui

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.aditya.moviemade.R
import com.aditya.moviemade.databinding.ActivityMainBinding
import org.koin.android.ext.android.bind

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBar.toolbar)

        val toggle = ActionBarDrawerToggle(this,
            binding.drawerLayout,
            binding.appBar.toolbar,
            R.string.open_navigation_drawer,
            R.string.close_navigation_drawer)


        navController = findNavController(R.id.nav_host_fragment)
        binding.drawerLayout.addDrawerListener(toggle)
        NavigationUI.setupWithNavController(binding.navigationView,navController)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.destination_favourite,R.id.destination_movie),binding.drawerLayout)
        setupActionBarWithNavController(navController,appBarConfiguration)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_filter,menu)
        return true
    }

}