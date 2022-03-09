package com.aditya.moviemade.ui

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.aditya.moviemade.R
import com.aditya.moviemade.databinding.ActivityMainBinding
import com.aditya.moviemade.ui.movie.MovieFragment
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tabLayoutFavourite.addTab(
            binding.tabLayoutFavourite.newTab().setText(getString(R.string.movie))
        )

        binding.tabLayoutFavourite.addTab(
            binding.tabLayoutFavourite.newTab().setText(getString(R.string.favourite))
        )

        binding.viewPagerFavourite.adapter = MainPagerAdapter(this,
            mutableListOf(MovieFragment(),MovieFavouriteFragment()))

        TabLayoutMediator(
            binding.tabLayoutFavourite, binding.viewPagerFavourite
        ) { tab, position ->
            tab.text = if (position == 0) getString(R.string.movie) else getString(R.string.favourite)
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_filter,menu)
        return true
    }

    override fun onResume() {
        super.onResume()
        binding.viewPagerFavourite.adapter?.notifyDataSetChanged()
    }

    class MainPagerAdapter(requireActivity: FragmentActivity,private val fragments: MutableList<Fragment>) : FragmentStateAdapter(requireActivity) {
        override fun getItemCount(): Int = fragments.size

        override fun createFragment(position: Int): Fragment =fragments[position]

    }
}