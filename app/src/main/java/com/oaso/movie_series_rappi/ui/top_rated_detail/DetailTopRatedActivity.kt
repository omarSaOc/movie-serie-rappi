package com.oaso.movie_series_rappi.ui.top_rated_detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.oaso.movie_series_rappi.R
import com.oaso.movie_series_rappi.databinding.ActivityDetailMovieBinding
import com.oaso.movie_series_rappi.databinding.ActivityDetailTopRatedBinding
import com.oaso.movie_series_rappi.model.database.popular_movie.PopularMovie
import com.oaso.movie_series_rappi.model.database.rated_movie.RatedMovie
import com.oaso.movie_series_rappi.ui.common.loadUrl
import com.oaso.movie_series_rappi.ui.popular_detail.DetailPopularMovieActivity
import com.oaso.movie_series_rappi.ui.popular_detail.DetailPopularMovieViewModel

class DetailTopRatedActivity : AppCompatActivity() {

    companion object {
        const val MOVIE = "DetailTopRatedActivity:movie"
    }

    private val viewModel : DetailTopRatedMovieViewModel by viewModels()
    private lateinit var binding : ActivityDetailTopRatedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTopRatedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.movieDetailToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val movie: RatedMovie = intent.getParcelableExtra(MOVIE)
            ?: throw(IllegalAccessException("Movie not found"))
        viewModel.setMovie(movie)
        viewModel.model.observe(this, Observer(::updateUi))
    }

    private fun updateUi(model: DetailTopRatedMovieViewModel.UiModel) = with(binding) {
        val movie = model.movie
        movieDetailToolbar.title = movie.title
        movieImage.loadUrl("https://image.tmdb.org/t/p/w780${movie.backdropPath}")
        movieDetailSummary.text = movie.overview
        movieDetailInfo.setMovie(movie)
    }
}