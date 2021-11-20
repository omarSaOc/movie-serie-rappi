package com.oaso.movie_series_rappi.ui.popular

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oaso.movie_series_rappi.R
import com.oaso.movie_series_rappi.databinding.ViewMovieBinding
import com.oaso.movie_series_rappi.model.database.popular_movie.PopularMovie
import com.oaso.movie_series_rappi.ui.common.basicDiffUtil
import com.oaso.movie_series_rappi.ui.common.inflate
import com.oaso.movie_series_rappi.ui.common.loadUrl

class PopularMovieAdapter(
    private val listener: (PopularMovie) -> Unit
) :
    RecyclerView.Adapter<PopularMovieAdapter.PopularMovieViewHolder>() {

    var popularMovies: List<PopularMovie> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.id == new.id }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMovieViewHolder {
        val view = parent.inflate(R.layout.view_movie, false)
        return PopularMovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: PopularMovieViewHolder, position: Int) {
        val movie = popularMovies[position]
        holder.bind(movie)
        holder.itemView.setOnClickListener { listener(movie) }
    }

    override fun getItemCount() = popularMovies.size

    class PopularMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ViewMovieBinding.bind(itemView)
        fun bind(popularMovie: PopularMovie) = with(binding) {
            movieTitle.text = popularMovie.title
            moviePoster.loadUrl("https://image.tmdb.org/t/p/w185/${popularMovie.posterPath}")
        }
    }
}