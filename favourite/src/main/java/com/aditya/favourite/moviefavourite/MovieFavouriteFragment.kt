package com.aditya.favourite.moviefavourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.aditya.moviemade.R
import com.aditya.moviemade.databinding.FragmentMovieBinding
import org.koin.android.viewmodel.ext.android.viewModel

class MovieFavouriteFragment : Fragment() {

    private lateinit var binding: FragmentMovieBinding
    private val viewModel:MovieFavouriteViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.toolbar3.visibility = View.GONE
        viewModel.getData().observe(viewLifecycleOwner, {
            if (it.isNotEmpty()) {
                binding.progressBar.visibility = View.GONE
                binding.recyclerView.apply {
                    adapter = it?.let { it1 -> MovieFavouriteAdapter(it1) }
                }
                showError(binding,View.GONE,"")
            } else{
                binding.progressBar.visibility = View.GONE
                binding.recyclerView.visibility = View.GONE
                showError(binding, View.VISIBLE, getString(R.string.empty_list))
            }
        })
    }

    companion object {
        fun showError(binding: FragmentMovieBinding, visibility: Int, message: String) {
            binding.emptyMovie.visibility = visibility
            binding.tvError.visibility = visibility
            binding.tvError.text = message
        }
    }
}