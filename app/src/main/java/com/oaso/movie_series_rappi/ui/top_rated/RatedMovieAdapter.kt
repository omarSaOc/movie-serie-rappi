package com.oaso.movie_series_rappi.ui.top_rated

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oaso.movie_series_rappi.R
import com.oaso.movie_series_rappi.databinding.ViewMovieBinding
import com.oaso.movie_series_rappi.model.database.popular_movie.PopularMovie
import com.oaso.movie_series_rappi.model.database.rated_movie.RatedMovie
import com.oaso.movie_series_rappi.ui.common.basicDiffUtil
import com.oaso.movie_series_rappi.ui.common.inflate
import com.oaso.movie_series_rappi.ui.common.loadUrl
import com.oaso.movie_series_rappi.ui.popular.PopularMovieAdapter

class RatedMovieAdapter(
    private val listener: (RatedMovie) -> Unit
) : RecyclerView.Adapter<RatedMovieAdapter.RatedMovieViewHolder>() {

    var ratedMovies: List<RatedMovie> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.id == new.id }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatedMovieViewHolder {
        val view = parent.inflate(R.layout.view_movie, false)
        return RatedMovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: RatedMovieViewHolder, position: Int) {
        val movie = ratedMovies[position]
        holder.bind(movie)
        holder.itemView.setOnClickListener { listener(movie) }
    }

    override fun getItemCount() = ratedMovies.size

    class RatedMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ViewMovieBinding.bind(itemView)
        fun bind(ratedMovie: RatedMovie) = with(binding) {
            movieTitle.text = ratedMovie.title
            moviePoster.loadUrl("https://image.tmdb.org/t/p/w185/${ratedMovie.posterPath}")
        }
    }
}