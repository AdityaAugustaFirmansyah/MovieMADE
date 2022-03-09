package com.aditya.moviemade.ui.movie

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aditya.moviemade.R
import com.aditya.moviemade.databinding.ItemMovieBinding
import com.aditya.core.data.source.remote.network.ApiClient
import com.aditya.core.domain.model.Movie
import com.aditya.moviemade.ui.detail.DetailMovieActivity
import com.bumptech.glide.Glide

class MovieAdapter(private val movies: List<com.aditya.core.domain.model.Movie>) :
    RecyclerView.Adapter<MovieAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(movies[position])
    }

    class Holder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movieEntity: com.aditya.core.domain.model.Movie) {
            binding.apply {
                Glide.with(itemView).load(com.aditya.core.data.source.remote.network.ApiClient.BASE_URL_IMAGE + movieEntity.posterPath)
                    .error(R.drawable.ic_broken_image_black).into(poster)
                ratingMovie.rating = (movieEntity.voteAverage / 2).toFloat()
                tvTitle.text = movieEntity.title
                languageMovie.text = movieEntity.originalLanguage
            }
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailMovieActivity::class.java)
                intent.putExtra(DetailMovieActivity.TAG, movieEntity.id)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int = movies.size
}