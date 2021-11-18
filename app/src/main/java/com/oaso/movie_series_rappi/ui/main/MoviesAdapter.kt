package com.oaso.movie_series_rappi.ui.main

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oaso.movie_series_rappi.R
import com.oaso.movie_series_rappi.databinding.ViewMovieBinding
import com.oaso.movie_series_rappi.model.Movie
import com.oaso.movie_series_rappi.ui.common.basicDiffUtil
import com.oaso.movie_series_rappi.ui.common.inflate
import com.oaso.movie_series_rappi.ui.common.loadUrl

class MoviesAdapter(
//    private val listener: (Movie) -> Unit
) :
    RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    var movies: List<Movie> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.id == new.id }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = parent.inflate(R.layout.view_movie, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
//        holder.itemView.setOnClickListener { listener(movie) }
    }

    override fun getItemCount() = movies.size

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ViewMovieBinding.bind(itemView)
        fun bind(movie: Movie) = with(binding) {
            movieTitle.text = movie.title
            moviePoster.loadUrl("https://image.tmdb.org/t/p/w185/${movie.poster_path}")
        }
    }
}